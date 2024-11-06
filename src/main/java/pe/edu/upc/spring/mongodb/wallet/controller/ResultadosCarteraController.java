package pe.edu.upc.spring.mongodb.wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upc.spring.mongodb.wallet.DTO.response.ResultadosCarteraDTO;
import pe.edu.upc.spring.mongodb.wallet.service.ResultadosCarteraService;

@RestController
@RequestMapping("/api/wallet/resultados/cartera")
public class ResultadosCarteraController {

    @Autowired
    private ResultadosCarteraService resultadosCarteraService;

    @GetMapping("/consultar")
    public ResponseEntity<ResultadosCarteraDTO> consultarCartera() {

        ResultadosCarteraDTO resultadosCarteraDTO = resultadosCarteraService.consultarCartera();
        return ResponseEntity.ok(resultadosCarteraDTO);
    }
}