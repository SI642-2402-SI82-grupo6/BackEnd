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

    private String getUserIdFromPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    @GetMapping("/consultar")
    public ResponseEntity<ResultadosCarteraDTO> consultarCartera() {
        String userId = getUserIdFromPrincipal();
        ResultadosCarteraDTO resultadosCarteraDTO = resultadosCarteraService.consultarCartera();
        return ResponseEntity.ok(resultadosCarteraDTO);
    }
}