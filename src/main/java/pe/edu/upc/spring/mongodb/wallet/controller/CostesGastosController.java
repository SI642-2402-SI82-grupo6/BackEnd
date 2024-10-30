package pe.edu.upc.spring.mongodb.wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.spring.mongodb.security.payload.response.MessageResponse;
import pe.edu.upc.spring.mongodb.wallet.DTO.request.CostesGastosDTORequest;
import pe.edu.upc.spring.mongodb.wallet.DTO.response.CostesGastosDTO;
import pe.edu.upc.spring.mongodb.wallet.model.CostesGastos;
import pe.edu.upc.spring.mongodb.wallet.service.CostesGastosService;

import java.util.List;

@RestController
@RequestMapping("/api/wallet/costes-gastos")
public class CostesGastosController {

    @Autowired
    private CostesGastosService costesGastosService;

    @PostMapping
    public ResponseEntity<MessageResponse> crearCosteGasto(@RequestBody CostesGastosDTORequest costeGastoDTO) {
        MessageResponse costesGastos = costesGastosService.createCostesGastos(costeGastoDTO);
        return ResponseEntity.ok(costesGastos);
    }

    @GetMapping
    public ResponseEntity<List<CostesGastos>> obtenerTodosCostesGastos() {
        List<CostesGastos> costesGastosList = costesGastosService.getAllCostesGastos();
        return ResponseEntity.ok(costesGastosList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CostesGastosDTO> obtenerCosteGastoPorId(@PathVariable String id) {
        return costesGastosService.getCosteGastoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CostesGastos> actualizarCosteGasto(@PathVariable String id, @RequestBody CostesGastosDTO costeGastoDTO) {
        return costesGastosService.updateCostesGastos(id, new CostesGastos(costeGastoDTO))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse>  eliminarCosteGasto(@PathVariable String id) {
        MessageResponse mensaje=costesGastosService.deleteCostesGastos(id);
        return ResponseEntity.ok(mensaje);
    }
    @DeleteMapping
    public ResponseEntity<MessageResponse>  eliminarTodosCostesGastos() {
        MessageResponse mensaje=costesGastosService.deleteAllCostesGastos();
        return ResponseEntity.ok(mensaje);
    }
}
