package pe.edu.upc.spring.mongodb.wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.spring.mongodb.security.payload.response.MessageResponse;
import pe.edu.upc.spring.mongodb.wallet.DTO.request.FacturaDTO;
import pe.edu.upc.spring.mongodb.wallet.model.Factura;
import pe.edu.upc.spring.mongodb.wallet.service.FacturaService;

import java.util.List;

@RestController
@RequestMapping("/api/wallet/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @PostMapping
    public ResponseEntity<MessageResponse> crearFactura(@RequestBody FacturaDTO facturaDTO) {
        MessageResponse factura = facturaService.createFactura(facturaDTO);
        return ResponseEntity.ok(factura);
    }

    @GetMapping
    public ResponseEntity<List<FacturaDTO>> obtenerTodasFacturas() {
        List<FacturaDTO> facturas = facturaService.getAllFacturas();
        return ResponseEntity.ok(facturas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Factura> obtenerFacturaPorId(@PathVariable String id) {
        return facturaService.getFacturaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Factura> actualizarFactura(@PathVariable String id, @RequestBody FacturaDTO facturaDTO) {
        return facturaService.updateFactura(id, new Factura(facturaDTO))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFactura(@PathVariable String id) {
        return facturaService.deleteFactura(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
