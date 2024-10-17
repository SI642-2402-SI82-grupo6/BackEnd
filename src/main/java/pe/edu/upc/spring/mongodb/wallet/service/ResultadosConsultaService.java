package pe.edu.upc.spring.mongodb.wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.spring.mongodb.wallet.model.ResultadosConsulta;
import pe.edu.upc.spring.mongodb.wallet.model.DocumentosCreados;
import pe.edu.upc.spring.mongodb.wallet.model.Factura;
import pe.edu.upc.spring.mongodb.wallet.model.Letra;
import pe.edu.upc.spring.mongodb.wallet.model.CostesGastos;
import pe.edu.upc.spring.mongodb.wallet.object.TipoDocumento;
import pe.edu.upc.spring.mongodb.wallet.object.TipoGasto;
import pe.edu.upc.spring.mongodb.wallet.object.TipoTasa;
import pe.edu.upc.spring.mongodb.wallet.repository.*;
import pe.edu.upc.spring.mongodb.wallet.DTO.response.ResultadosConsultaDTO;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ResultadosConsultaService {

    @Autowired
    private DocumentosCreadosRepository documentosCreadosRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private LetraRepository letraRepository;

    @Autowired
    private CostesGastosRepository costesGastosRepository;

    @Autowired
    private TasaYPlazoRepository tasaYPlazoRepository;

    @Autowired
    private ResultadosConsultaRepository resultadosConsultaRepository;

    public ResultadosConsultaDTO calcularResultados(String documentoId, LocalDate fechaDescuento) {
        Optional<DocumentosCreados> documentoCreadoOpt = documentosCreadosRepository.findById(documentoId);

        if (!documentoCreadoOpt.isPresent()) {
            throw new RuntimeException("El documento no fue encontrado.");
        }

        DocumentosCreados documentoCreado = documentoCreadoOpt.get();
        ResultadosConsulta resultadosConsulta = new ResultadosConsulta();

        resultadosConsulta.setIdDocumento(documentoCreado.getIdDocumento());
        resultadosConsulta.setUserId(documentoCreado.getUserId());

        // Asignar número de consulta de forma secuencial
        Integer ultimoNumeroConsulta = obtenerUltimoNumeroConsulta();
        resultadosConsulta.setNumeroConsulta(String.valueOf(ultimoNumeroConsulta + 1));

        // Dependiendo del tipo de documento, cargamos la información de factura o letra
        if (documentoCreado.getTipoDocumento().equals(TipoDocumento.FACTURA)) {
            Optional<Factura> facturaOpt = facturaRepository.findById(documentoCreado.getIdDocumento());
            if (!facturaOpt.isPresent()) {
                throw new RuntimeException("Factura no encontrada.");
            }
            Factura factura = facturaOpt.get();
            resultadosConsulta.setFechaGiro(factura.getFechaEmision());
            resultadosConsulta.setValorNomAplicando(factura.getTotalFacturado());
            resultadosConsulta.setFechaVencimiento(factura.getFechaPago());
        } else if (documentoCreado.getTipoDocumento().equals(TipoDocumento.LETRA)) {
            Optional<Letra> letraOpt = letraRepository.findById(documentoCreado.getIdDocumento());
            if (!letraOpt.isPresent()) {
                throw new RuntimeException("Letra no encontrada.");
            }
            Letra letra = letraOpt.get();
            resultadosConsulta.setFechaGiro(letra.getFechaGiro());
            resultadosConsulta.setValorNomAplicando(letra.getValorNominal());
            resultadosConsulta.setFechaVencimiento(letra.getFechaVencimiento());
        }

        // Calcular los días
        resultadosConsulta.CalcularDias(resultadosConsulta.getFechaVencimiento(), fechaDescuento);

        // Obtener tasas y plazos
        var tasaYPlazo = tasaYPlazoRepository.findByDocumentoId(documentoCreado.getIdDocumento())
                .orElseThrow(() -> new RuntimeException("Tasa y Plazo no encontrados."));

        if (tasaYPlazo.getTipoTasa() == TipoTasa.NOMINAL) {
            // Asegúrate de que los valores necesarios no sean nulos
            if (tasaYPlazo.getTasaNominal() == null ) {
                throw new RuntimeException("Valores nulos o incompletos para calcular la tasa nominal.");
            }
            resultadosConsulta.CalcularTEDiasTasaNominal(
                    tasaYPlazo.getTasaNominal(),
                    tasaYPlazo.getPlazoDeTasa(),
                    tasaYPlazo.getPeriodoCapital()
            );
        } else if (tasaYPlazo.getTipoTasa() == TipoTasa.EFECTIVA) {
            // Asegúrate de que los valores necesarios no sean nulos
            if (tasaYPlazo.getTasaEfectiva() == null || tasaYPlazo.getPlazoEfectivo() == null) {
                throw new RuntimeException("Valores nulos o incompletos para calcular la tasa efectiva.");
            }
            resultadosConsulta.CalcularTEdiasTasaEfectiva(
                    tasaYPlazo.getTasaEfectiva(),
                    tasaYPlazo.getPlazoEfectivo()
            );
        } else {
            throw new RuntimeException("Tipo de tasa no soportado: " + tasaYPlazo.getTipoTasa());
        }


        // Calcular el descuento y otros valores
        resultadosConsulta.CalcularDescuento();
        resultadosConsulta.CalcularValorDescuento();

        // Obtener los costes iniciales y finales
        var costesGastos = costesGastosRepository.findByDocumentoId(documentoCreado.getIdDocumento());
        costesGastos.ifPresent(cg -> {
            if (cg.getTipoGasto().equals(TipoGasto.INICIAL)) {
                resultadosConsulta.setCosteInicial(cg.getValorExpresado().getValor());
            } else if (cg.getTipoGasto().equals(TipoGasto.FINAL)) {
                resultadosConsulta.setCosteFinal(cg.getValorExpresado().getValor());
            }
        });


        resultadosConsulta.CaluclarValorNeto();
        resultadosConsulta.CalcularValorRecibir();
        resultadosConsulta.CalcularValorEntregado();
        resultadosConsulta.CalcularTceaPorcentaje();


        return resultadosConsulta.toResultadosConsultaDTO();
    }

     private Integer obtenerUltimoNumeroConsulta() {
        return resultadosConsultaRepository.findAll().stream()
                .map(r -> Integer.parseInt(r.getNumeroConsulta()))
                .max(Integer::compareTo)
                .orElse(0); // Si no existe ningún documento previo, empezamos en 0
    }
}
