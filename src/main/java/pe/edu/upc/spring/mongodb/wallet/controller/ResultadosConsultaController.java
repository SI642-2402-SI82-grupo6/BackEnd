package pe.edu.upc.spring.mongodb.wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<ResultadosConsultaDTO> calcularResultadosConsulta(@RequestParam String documentoId, @RequestParam LocalDate fechaDescuento) {
        ResultadosConsultaDTO resultadosConsulta = resultadosConsultaService.calcularResultados(documentoId, fechaDescuento);
        return ResponseEntity.ok(resultadosConsulta);
    }
    @GetMapping("/all")
    public ResponseEntity<List<ResultadosConsultaDTO>> getAllResultadosConsulta() {
        List<ResultadosConsultaDTO> resultadosConsultaList = resultadosConsultaService.getAllResultadosConsulta();
        return ResponseEntity.ok(resultadosConsultaList);
    }
}
