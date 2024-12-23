package pe.edu.upc.spring.mongodb.wallet.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pe.edu.upc.spring.mongodb.security.security.services.UserDetailsImpl;
import pe.edu.upc.spring.mongodb.wallet.DTO.response.ResultadosConsultaDTO;
import pe.edu.upc.spring.mongodb.wallet.exception.ResourceNotFoundException;
import pe.edu.upc.spring.mongodb.wallet.model.*;
import pe.edu.upc.spring.mongodb.wallet.object.TipoGasto;
import pe.edu.upc.spring.mongodb.wallet.object.TipoTasa;
import pe.edu.upc.spring.mongodb.wallet.repository.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class ResultadosConsultaService {

    private static final Logger logger = LoggerFactory.getLogger(ResultadosConsultaService.class);

    @Autowired
    private DocumentosCreadosRepository documentosCreadosRepository;

    @Autowired
    private ResultadosConsultaRepository ResultadosConsultaRepository;
    @Autowired
    private LetraRepository letraRepository;
    @Autowired
    private TasaYPlazoRepository tasaYPlazoRepository;
    @Autowired
    private CostesGastosRepository costesGastosRepository;
    @Autowired
    private FacturaRepository facturaRepository;
    LocalDate fechaVencimiento ;

    private static final AtomicLong counter = new AtomicLong(1);
    public void consultarResultadoPorDocumentoId(String documentoId) {

        Optional<DocumentosCreados> documentoOpt = documentosCreadosRepository.findByDocumentoId(documentoId);
        if (documentoOpt.isEmpty()) {
            throw new RuntimeException("Documento no encontrado.");
        }
        if (documentoId == null) {
            throw new IllegalArgumentException("The given id must not be null");
        }

        ResultadosConsultaRepository.findByDocumentoId(documentoId)
                .ifPresent(ResultadosConsultaRepository::delete);
        ResultadosConsulta resultadosConsulta = new ResultadosConsulta();
        resultadosConsulta.setDocumentoId(documentoId);
        resultadosConsulta.setUserId(documentoOpt.get().getUserId());
        resultadosConsulta.setNumeroConsulta(String.valueOf(counter.getAndIncrement()));
        // Handle multiple results by selecting the first one
        Optional<DocumentosCreados> documentos = documentosCreadosRepository.findByDocumentoId(documentoId);
        if (documentos.isEmpty()) {
            throw new RuntimeException("Documento no encontrado.");
        }
        // Search and assign data from Factura or Letra
        Optional<Factura> facturaOpt = facturaRepository.findById(documentoId);
        if (facturaOpt.isPresent()) {
            Factura factura = facturaOpt.get();
            resultadosConsulta.setFechaGiro(factura.getFechaEmision());
            resultadosConsulta.setValorNomAplicando(factura.getTotalFacturado());
            resultadosConsulta.setFechaVencimiento(factura.getFechaPago());
            resultadosConsulta.setRetencion(factura.getRetencion());
            resultadosConsulta.setType("FACTURA");
            fechaVencimiento = factura.getFechaPago();
        } else {
            Optional<Letra> letraOpt = letraRepository.findById(documentoId);
            if (letraOpt.isPresent()) {
                Letra letra = letraOpt.get();
                resultadosConsulta.setFechaGiro(letra.getFechaGiro());
                resultadosConsulta.setValorNomAplicando(letra.getValorNominal());
                resultadosConsulta.setFechaVencimiento(letra.getFechaVencimiento());
                resultadosConsulta.setRetencion(letra.getRetencion());
                resultadosConsulta.setType("LETRA");
                fechaVencimiento = letra.getFechaVencimiento();
            } else {
                throw new ResourceNotFoundException("Factura o Letra no encontrada.");
            }
        }
        List<CostesGastos> costesGastosList = costesGastosRepository.findAll();
        for (CostesGastos costeGasto : costesGastosList) {
            if (costeGasto.getDocumentoId().equals(documentoId)) {
                double valor = costeGasto.getValorExpresado().isEsPorcentaje() ?
                        costeGasto.getValorExpresado().getValor() * resultadosConsulta.getValorNomAplicando() / 100 :
                        costeGasto.getValorExpresado().getValor();

                if (costeGasto.getTipoGasto() == TipoGasto.INICIAL) {
                    resultadosConsulta.sumCosteInicial(valor);
                } else if (costeGasto.getTipoGasto() == TipoGasto.FINAL) {
                    resultadosConsulta.sumCosteFinal(valor);
                }
            }
        }
        Optional<TasaYPlazo> tasaYPlazoOpt = tasaYPlazoRepository.findByDocumentoId(documentoId);
        logger.info("Fetched TasaYPlazo: {}", tasaYPlazoOpt);
        if (tasaYPlazoOpt.isPresent()) {
            TasaYPlazo tasaYPlazo = tasaYPlazoOpt.get();
            logger.info("Fecha de vencimiento (Factura): {}", fechaVencimiento);
            resultadosConsulta.CalcularDias(fechaVencimiento, tasaYPlazo.getFechaDescuento());

            if (tasaYPlazo.getTipoTasa() != null) {
                if (tasaYPlazo.getTipoTasa().equals(TipoTasa.EFECTIVA)) {
                    resultadosConsulta.CalcularTEdiasTasaEfectiva(tasaYPlazo.getTasaEfectiva(), tasaYPlazo.getPlazoEfectivo());
                } else if (tasaYPlazo.getTipoTasa().equals(TipoTasa.NOMINAL)) {
                    resultadosConsulta.CalcularTEDiasTasaNominal(tasaYPlazo.getTasaNominal(), tasaYPlazo.getPlazoDeTasa(), tasaYPlazo.getPeriodoCapital());
                } else {
                    logger.warn("Tipo de tasa desconocido: {}", tasaYPlazo.getTipoTasa());
                }
            } else {
                logger.error("El tipo de tasa es nulo");
            }
        }

        // Llamar a los métodos adicionales
        resultadosConsulta.CalcularDescuento();
        resultadosConsulta.CalcularValorDescuento();
        resultadosConsulta.CaluclarValorNeto();
        resultadosConsulta.CalcularValorRecibir();
        resultadosConsulta.CalcularValorEntregado();
        resultadosConsulta.CalcularTceaPorcentaje();

        if(ResultadosConsultaRepository.findByDocumentoId(documentoId).isPresent()){
            ResultadosConsultaRepository.deleteByDocumentoId(documentoId);
        }
        ResultadosConsultaRepository.save(resultadosConsulta);
        resultadosConsulta.toResultadosConsultaDTO();
    }
    public void deleteResultadosConsultaPorDocumentoId(String id) {
        ResultadosConsultaRepository.deleteByDocumentoId(id);
    }

        public List<ResultadosConsultaDTO> obtenerResultados() {
            List<ResultadosConsulta> resultadosConsulta = ResultadosConsultaRepository.findAll();
            return resultadosConsulta.stream()
                    .map(ResultadosConsulta::toResultadosConsultaDTO)
                    .collect(Collectors.toList());
        }

    public List<DocumentosCreados> obtenerDocumentosCreados() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId;
        if (principal instanceof UserDetailsImpl) {
            userId = ((UserDetailsImpl) principal).getId();
        } else {
            userId = principal.toString();
        }
        Optional<List<DocumentosCreados>> AllDocument=documentosCreadosRepository.findAllByUserId(userId);
        if(AllDocument.isEmpty()){
            throw new ResourceNotFoundException("No se encontraron documentos creados");
        }
        return AllDocument.get();
    }
    public DocumentosCreados obtenerDocumentoCreadoPorId(String id) {
        Optional<DocumentosCreados> documentosCreados = documentosCreadosRepository.findById(id);
        if (documentosCreados.isPresent()) {
            return documentosCreados.get();
        } else {
            throw new ResourceNotFoundException("Documento con ID " + id + " no encontrado.");
        }
    }

}