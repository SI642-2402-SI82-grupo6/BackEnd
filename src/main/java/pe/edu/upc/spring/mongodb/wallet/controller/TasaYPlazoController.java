package pe.edu.upc.spring.mongodb.wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.spring.mongodb.security.payload.response.MessageResponse;
import pe.edu.upc.spring.mongodb.wallet.DTO.request.TasaYPlazoDTO;
import pe.edu.upc.spring.mongodb.wallet.model.TasaYPlazo;
import pe.edu.upc.spring.mongodb.wallet.service.TasaYPlazoService;

import java.util.List;

@RestController
@RequestMapping("/api/wallet/tasa-y-plazo")
public class TasaYPlazoController {

    @Autowired
    private TasaYPlazoService tasaYPlazoService;

    @PostMapping
    public ResponseEntity<MessageResponse> crearTasaYPlazo(@RequestBody TasaYPlazoDTO tasaYPlazoDTO) {

        MessageResponse tasaYPlazo = tasaYPlazoService.createTasaYPlazo(tasaYPlazoDTO);
        return ResponseEntity.ok(tasaYPlazo);
    }

    @GetMapping
    public ResponseEntity<List<TasaYPlazo>> obtenerTodasTasasYPlazos() {
        List<TasaYPlazo> tasasYPlazos = tasaYPlazoService.getAllTasasYPlazos();
        return ResponseEntity.ok(tasasYPlazos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TasaYPlazoDTO> obtenerTasaYPlazoPorId(@PathVariable String id) {
        return tasaYPlazoService.getTasaYPlazoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TasaYPlazo> actualizarTasaYPlazo(@PathVariable String id, @RequestBody TasaYPlazoDTO tasaYPlazoDTO) {
        return tasaYPlazoService.updateTasaYPlazo(id, new TasaYPlazo(tasaYPlazoDTO))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTasaYPlazo(@PathVariable String id) {
        return tasaYPlazoService.deleteTasaYPlazo(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
    @DeleteMapping()
    public ResponseEntity<Void> eliminarTasaYPlazoPorUserId() {
        return tasaYPlazoService.deleteTasaYPlazoByUserId() ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
