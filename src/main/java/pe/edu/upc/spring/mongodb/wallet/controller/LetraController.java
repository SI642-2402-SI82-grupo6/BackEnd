package pe.edu.upc.spring.mongodb.wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<Letra> crearLetra(@RequestBody LetraDTO letraDTO) {
        Letra letra = letraService.createLetra(letraDTO);
        return ResponseEntity.ok(letra);
    }

    @GetMapping
    public ResponseEntity<List<Letra>> obtenerTodasLetras() {
        List<Letra> letras = letraService.getAllLetras();
        return ResponseEntity.ok(letras);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Letra> obtenerLetraPorId(@PathVariable String id) {
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
    public ResponseEntity<Void> eliminarLetra(@PathVariable String id) {
        return letraService.deleteLetra(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}