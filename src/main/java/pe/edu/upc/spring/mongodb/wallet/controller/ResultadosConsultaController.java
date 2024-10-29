package pe.edu.upc.spring.mongodb.wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.spring.mongodb.wallet.DTO.request.RequestConsultaResultado;
import pe.edu.upc.spring.mongodb.wallet.DTO.response.ResultadosConsultaDTO;
import pe.edu.upc.spring.mongodb.wallet.service.ResultadosConsultaService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/wallet/resultados/consulta")
public class ResultadosConsultaController {

    @Autowired
    private ResultadosConsultaService resultadosConsultaService;

    @PostMapping
    public List<ResultadosConsultaDTO> calcularResultados() {
        return resultadosConsultaService.calcularResultados();
    }

}
