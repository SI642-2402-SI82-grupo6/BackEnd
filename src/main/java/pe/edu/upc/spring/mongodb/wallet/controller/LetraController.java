package pe.edu.upc.spring.mongodb.wallet.controller;



import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.spring.mongodb.security.payload.response.MessageResponse;
import pe.edu.upc.spring.mongodb.wallet.DTO.request.LetraDTORequest;
import pe.edu.upc.spring.mongodb.wallet.DTO.response.LetraDTO;
import pe.edu.upc.spring.mongodb.wallet.model.Letra;
import pe.edu.upc.spring.mongodb.wallet.service.LetraService;

import java.util.List;

@RestController
@RequestMapping("/api/wallet/letras")
public class LetraController {

    @Autowired
    private LetraService letraService;


    @PostMapping

    public ResponseEntity<MessageResponse> crearLetra(@RequestBody LetraDTORequest letraDTO) {
        MessageResponse response = letraService.createLetra(letraDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> obtenerTodasLetras() {
        List<LetraDTO> letras = letraService.getAllLetras();
        return ResponseEntity.ok(letras);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LetraDTO> obtenerLetraPorId(@PathVariable String id) {
        return letraService.getLetraById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarLetra(@PathVariable String id, @RequestBody LetraDTO letraDTO) {
        try {
            Letra updatedLetra = letraService.updateLetra(id, new Letra(letraDTO))
                    .orElseThrow(() -> new IllegalArgumentException("Letra no encontrada."));
            return ResponseEntity.ok(updatedLetra);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(new MessageResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new MessageResponse("Error interno del servidor."));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarLetra(@PathVariable String id) {
        try {
            MessageResponse response = letraService.deleteLetra(id);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(new MessageResponse("Letra con ID " + id + " no encontrada."));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new MessageResponse("Error interno del servidor."));
        }
    }
}
