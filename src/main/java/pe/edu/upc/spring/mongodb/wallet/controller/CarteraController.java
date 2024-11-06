package pe.edu.upc.spring.mongodb.wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upc.spring.mongodb.wallet.DTO.requestCartera.CarteraDtoRequest;
import pe.edu.upc.spring.mongodb.wallet.DTO.resource.CarteraResource;
import pe.edu.upc.spring.mongodb.wallet.service.CarteraService;

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
    public ResponseEntity<CarteraResource> agregarDocumento(@RequestBody String carteraId, String doumentoId) {
        CarteraResource carteraResource = carteraService.agregarDocumento(carteraId, doumentoId);
        return ResponseEntity.ok(carteraResource);
    }

}
