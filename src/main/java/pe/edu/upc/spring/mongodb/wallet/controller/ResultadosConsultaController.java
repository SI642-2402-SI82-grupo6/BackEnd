package pe.edu.upc.spring.mongodb.wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.spring.mongodb.wallet.DTO.request.RequestConsultaResultado;
import pe.edu.upc.spring.mongodb.wallet.DTO.response.ResultadosConsultaDTO;
import pe.edu.upc.spring.mongodb.wallet.model.DocumentosCreados;
import pe.edu.upc.spring.mongodb.wallet.service.ResultadosConsultaService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/wallet/resultados/consulta")
public class ResultadosConsultaController {

    @Autowired
    private ResultadosConsultaService resultadosConsultaService;

    @GetMapping("/obtenerResultados")
    public ResponseEntity<List<ResultadosConsultaDTO>> obtenerResultados() {
        List<ResultadosConsultaDTO> resultadosConsultaDTO = resultadosConsultaService.obtenerResultados();
        return ResponseEntity.ok(resultadosConsultaDTO);
    }

    @GetMapping("/obtenerDocumentosCreados")
    public ResponseEntity<List<DocumentosCreados>> obtenerDocumentosCreados() {
        List<DocumentosCreados> resultadosConsultaDTO = resultadosConsultaService.obtenerDocumentosCreados();
        return ResponseEntity.ok(resultadosConsultaDTO);
    }
    @GetMapping("/obtenerDocumentoCreadoPorId/{id}")
    public ResponseEntity<DocumentosCreados> obtenerDocumentoCreadoPorId(@PathVariable String id) {
        DocumentosCreados documentosCreados = resultadosConsultaService.obtenerDocumentoCreadoPorId(id);
        return ResponseEntity.ok(documentosCreados);
    }
}
