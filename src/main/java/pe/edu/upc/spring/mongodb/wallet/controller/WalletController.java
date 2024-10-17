package pe.edu.upc.spring.mongodb.wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.spring.mongodb.wallet.DTO.request.FacturaDTO;
import pe.edu.upc.spring.mongodb.wallet.DTO.request.LetraDTO;
import pe.edu.upc.spring.mongodb.wallet.DTO.request.CostesGastosDTO;
import pe.edu.upc.spring.mongodb.wallet.DTO.request.TasaYPlazoDTO;
import pe.edu.upc.spring.mongodb.wallet.DTO.response.ResultadosCarteraDTO;
import pe.edu.upc.spring.mongodb.wallet.DTO.response.ResultadosConsultaDTO;
import pe.edu.upc.spring.mongodb.wallet.model.ResultadosCartera;
import pe.edu.upc.spring.mongodb.wallet.model.Factura;
import pe.edu.upc.spring.mongodb.wallet.model.Letra;
import pe.edu.upc.spring.mongodb.wallet.model.CostesGastos;
import pe.edu.upc.spring.mongodb.wallet.model.TasaYPlazo;
import pe.edu.upc.spring.mongodb.wallet.service.FacturaService;
import pe.edu.upc.spring.mongodb.wallet.service.LetraService;
import pe.edu.upc.spring.mongodb.wallet.service.ResultadosConsultaService;
import pe.edu.upc.spring.mongodb.wallet.service.ResultadosCarteraService;
import pe.edu.upc.spring.mongodb.wallet.service.CostesGastosService;
import pe.edu.upc.spring.mongodb.wallet.service.TasaYPlazoService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private FacturaService facturaService;

    @Autowired
    private LetraService letraService;

    @Autowired
    private ResultadosConsultaService resultadosConsultaService;

    @Autowired
    private ResultadosCarteraService resultadosCarteraService;

    @Autowired
    private CostesGastosService costesGastosService;

    @Autowired
    private TasaYPlazoService tasaYPlazoService;

    // CRUD para Factura

    @PostMapping("/facturas")
    public ResponseEntity<Factura> crearFactura(@RequestBody FacturaDTO facturaDTO) {
        Factura factura = facturaService.createFactura(facturaDTO);
        return ResponseEntity.ok(factura);
    }

    @GetMapping("/facturas")
    public ResponseEntity<List<Factura>> obtenerTodasFacturas() {
        List<Factura> facturas = facturaService.getAllFacturas();
        return ResponseEntity.ok(facturas);
    }

    @GetMapping("/facturas/{id}")
    public ResponseEntity<Factura> obtenerFacturaPorId(@PathVariable String id) {
        return facturaService.getFacturaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/facturas/{id}")
    public ResponseEntity<Factura> actualizarFactura(@PathVariable String id, @RequestBody FacturaDTO facturaDTO) {
        return facturaService.updateFactura(id, new Factura(facturaDTO))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/facturas/{id}")
    public ResponseEntity<Void> eliminarFactura(@PathVariable String id) {
        return facturaService.deleteFactura(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    // CRUD para Letra

    @PostMapping("/letras")
    public ResponseEntity<Letra> crearLetra(@RequestBody LetraDTO letraDTO) {
        Letra letra = letraService.createLetra(letraDTO);
        return ResponseEntity.ok(letra);
    }

    @GetMapping("/letras")
    public ResponseEntity<List<Letra>> obtenerTodasLetras() {
        List<Letra> letras = letraService.getAllLetras();
        return ResponseEntity.ok(letras);
    }

    @GetMapping("/letras/{id}")
    public ResponseEntity<Letra> obtenerLetraPorId(@PathVariable String id) {
        return letraService.getLetraById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/letras/{id}")
    public ResponseEntity<Letra> actualizarLetra(@PathVariable String id, @RequestBody LetraDTO letraDTO) {
        return letraService.updateLetra(id, new Letra(letraDTO))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/letras/{id}")
    public ResponseEntity<Void> eliminarLetra(@PathVariable String id) {
        return letraService.deleteLetra(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    // Resultados de Consulta

    @PostMapping("/resultados/consulta")
    public ResponseEntity<ResultadosConsultaDTO> calcularResultadosConsulta(@RequestParam String documentoId, @RequestParam LocalDate fechaDescuento) {
        ResultadosConsultaDTO resultadosConsulta = resultadosConsultaService.calcularResultados(documentoId, fechaDescuento);
        return ResponseEntity.ok(resultadosConsulta);
    }

    // Resultados de Cartera

    @GetMapping("/resultados/cartera/{userId}")
    public ResponseEntity<ResultadosCarteraDTO> obtenerResultadosCartera(@PathVariable String userId) {
        ResultadosCartera resultadosCartera = resultadosCarteraService.obtenerResultadosCartera(userId);
        ResultadosCarteraDTO resultadosCarteraDTO = resultadosCarteraService.obtenerResultadosCarteraDTO(userId);
        return ResponseEntity.ok(resultadosCarteraDTO);
    }

    @PutMapping("/resultados/cartera/{userId}")
    public ResponseEntity<ResultadosCartera> actualizarResultadosCartera(@PathVariable String userId) {
        ResultadosCartera resultadosCartera = resultadosCarteraService.actualizarResultadosCartera(userId);
        return ResponseEntity.ok(resultadosCartera);
    }

    // CRUD para CostesGastos

    @PostMapping("/costes-gastos")
    public ResponseEntity<CostesGastos> crearCosteGasto(@RequestBody CostesGastosDTO costeGastoDTO) {
        CostesGastos costesGastos = costesGastosService.createCostesGastos(costeGastoDTO);
        return ResponseEntity.ok(costesGastos);
    }

    @GetMapping("/costes-gastos")
    public ResponseEntity<List<CostesGastos>> obtenerTodosCostesGastos() {
        List<CostesGastos> costesGastosList = costesGastosService.getAllCostesGastos();
        return ResponseEntity.ok(costesGastosList);
    }

    @GetMapping("/costes-gastos/{id}")
    public ResponseEntity<CostesGastos> obtenerCosteGastoPorId(@PathVariable String id) {
        return costesGastosService.getCosteGastoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/costes-gastos/{id}")
    public ResponseEntity<CostesGastos> actualizarCosteGasto(@PathVariable String id, @RequestBody CostesGastosDTO costeGastoDTO) {
        return costesGastosService.updateCostesGastos(id, new CostesGastos(costeGastoDTO))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/costes-gastos/{id}")
    public ResponseEntity<Void> eliminarCosteGasto(@PathVariable String id) {
        return costesGastosService.deleteCostesGastos(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    // CRUD para TasaYPlazo

    @PostMapping("/tasa-y-plazo")
    public ResponseEntity<TasaYPlazo> crearTasaYPlazo(@RequestBody TasaYPlazoDTO tasaYPlazoDTO) {
        TasaYPlazo tasaYPlazo = tasaYPlazoService.createTasaYPlazo(tasaYPlazoDTO);
        return ResponseEntity.ok(tasaYPlazo);
    }

    @GetMapping("/tasa-y-plazo")
    public ResponseEntity<List<TasaYPlazo>> obtenerTodasTasasYPlazos() {
        List<TasaYPlazo> tasasYPlazos = tasaYPlazoService.getAllTasasYPlazos();
        return ResponseEntity.ok(tasasYPlazos);
    }

    @GetMapping("/tasa-y-plazo/{id}")
    public ResponseEntity<TasaYPlazo> obtenerTasaYPlazoPorId(@PathVariable String id) {
        return tasaYPlazoService.getTasaYPlazoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/tasa-y-plazo/{id}")
    public ResponseEntity<TasaYPlazo> actualizarTasaYPlazo(@PathVariable String id, @RequestBody TasaYPlazoDTO tasaYPlazoDTO) {
        return tasaYPlazoService.updateTasaYPlazo(id, new TasaYPlazo(tasaYPlazoDTO))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/tasa-y-plazo/{id}")
    public ResponseEntity<Void> eliminarTasaYPlazo(@PathVariable String id) {
        return tasaYPlazoService.deleteTasaYPlazo(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
