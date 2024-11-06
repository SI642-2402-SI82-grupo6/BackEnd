package pe.edu.upc.spring.mongodb.wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.spring.mongodb.security.payload.response.MessageResponse;
import pe.edu.upc.spring.mongodb.wallet.DTO.request.FacturaDTORequest;
import pe.edu.upc.spring.mongodb.wallet.DTO.response.FacturaDTO;
import pe.edu.upc.spring.mongodb.wallet.model.Factura;
import pe.edu.upc.spring.mongodb.wallet.service.FacturaService;

import java.util.List;

@RestController
@RequestMapping("/api/wallet/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @PostMapping
    public ResponseEntity<?> crearFactura(@RequestBody FacturaDTORequest facturaDTO) {
        try {
            MessageResponse response = facturaService.createFactura(facturaDTO);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error en los datos de la factura."));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new MessageResponse("Error interno del servidor."));
        }
    }

    @GetMapping
    public ResponseEntity<List<FacturaDTO>> obtenerTodasFacturas() {
        List<FacturaDTO> facturas = facturaService.getAllFacturas();
        return ResponseEntity.ok(facturas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacturaDTO> obtenerFacturaPorId(@PathVariable String id) {
        return facturaService.getFacturaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarFactura(@PathVariable String id, @RequestBody FacturaDTO facturaDTO) {
        try {
            Factura updatedFactura = facturaService.updateFactura(id, new Factura(facturaDTO))
                    .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada"));
            return ResponseEntity.ok(updatedFactura);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(new MessageResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new MessageResponse("Error interno del servidor."));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> eliminarFactura(@PathVariable String id) {
        try {
            MessageResponse response = facturaService.deleteFactura(id);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(new MessageResponse("Factura no encontrada."));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new MessageResponse("Error interno del servidor."));
        }
    }
}

