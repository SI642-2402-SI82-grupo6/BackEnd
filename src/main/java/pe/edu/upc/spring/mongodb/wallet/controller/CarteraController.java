package pe.edu.upc.spring.mongodb.wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.spring.mongodb.wallet.DTO.requestCartera.CarteraDtoRequest;
import pe.edu.upc.spring.mongodb.wallet.DTO.resource.CarteraResource;
import pe.edu.upc.spring.mongodb.wallet.model.Cartera;
import pe.edu.upc.spring.mongodb.wallet.service.CarteraService;

import java.util.List;

@RestController
@RequestMapping("/api/wallet/cartera")
public class CarteraController {
    @Autowired
    private CarteraService carteraService;

    @PostMapping("/crearCartera")
    public ResponseEntity<CarteraResource> crearCartera(@RequestBody CarteraDtoRequest carteraDtoRequest) {
        CarteraResource carteraResource = carteraService.createCartera(carteraDtoRequest);
        return ResponseEntity.ok(carteraResource);
    }

    @PostMapping("/agregarDocumento")
    public ResponseEntity<CarteraResource> agregarDocumento( String carteraId, String doumentoId) {
        CarteraResource carteraResource = carteraService.agregarDocumento(carteraId, doumentoId);
        return ResponseEntity.ok(carteraResource);
    }

    @GetMapping("/obtenerCartera")
    public ResponseEntity<Cartera> obtenerCartera(String carteraId) {
        Cartera carteraResource = carteraService.obtenerCartera(carteraId);
        return ResponseEntity.ok(carteraResource);
    }
    @GetMapping("/obtenerCarteras")
    public ResponseEntity<List<Cartera>> obtenerCarteras() {
        List<Cartera> carteraResource = carteraService.obtenerCarteras();
        return ResponseEntity.ok(carteraResource);
    }

}
