package pe.edu.upc.spring.mongodb.wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.spring.mongodb.security.payload.response.MessageResponse;
import pe.edu.upc.spring.mongodb.wallet.DTO.request.LetraDTO;
import pe.edu.upc.spring.mongodb.wallet.model.Letra;
import pe.edu.upc.spring.mongodb.wallet.service.LetraService;

import java.util.List;

@RestController
@RequestMapping("/api/wallet/letras")
public class LetraController {

    @Autowired
    private LetraService letraService;

    @PostMapping
    public ResponseEntity<MessageResponse> crearLetra(@RequestBody LetraDTO letraDTO) {
        MessageResponse letra = letraService.createLetra(letraDTO);
        return ResponseEntity.ok(letra);
    }

    @GetMapping
    public ResponseEntity<List<LetraDTO>> obtenerTodasLetras() {
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
    public ResponseEntity<Letra> actualizarLetra(@PathVariable String id, @RequestBody LetraDTO letraDTO) {
        return letraService.updateLetra(id, new Letra(letraDTO))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> eliminarLetra(@PathVariable String id) {
        MessageResponse letra = letraService.deleteLetra(id);
        return ResponseEntity.ok(letra);
    }
}
