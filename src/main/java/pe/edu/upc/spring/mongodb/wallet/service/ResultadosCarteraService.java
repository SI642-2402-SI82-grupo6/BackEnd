package pe.edu.upc.spring.mongodb.wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pe.edu.upc.spring.mongodb.wallet.model.ResultadosCartera;
import pe.edu.upc.spring.mongodb.wallet.model.ResultadosConsulta;
import pe.edu.upc.spring.mongodb.wallet.DTO.response.ResultadosCarteraDTO;
import pe.edu.upc.spring.mongodb.wallet.repository.ResultadosCarteraRepository;
import pe.edu.upc.spring.mongodb.wallet.repository.ResultadosConsultaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResultadosCarteraService {

    @Autowired
    private ResultadosCarteraRepository resultadosCarteraRepository;

    @Autowired
    private ResultadosConsultaRepository resultadosConsultaRepository;

    private String getUserIdFromPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    public ResultadosCarteraDTO consultarCartera() {
        String userId = getUserIdFromPrincipal();

        // Verificar si la cartera existe y eliminarla si es necesario
        Optional<ResultadosCartera> carteraOpt = resultadosCarteraRepository.findByUserId(userId);
        if (carteraOpt.isPresent()) {
            resultadosCarteraRepository.deleteByUserId(userId);
        }

        // Crear una nueva cartera
        ResultadosCartera resultadosCartera = new ResultadosCartera();
        resultadosCartera.setId();
        resultadosCartera.setUserId(userId);

        // Obtener los resultados de consulta más recientes
        List<ResultadosConsulta> resultadosConsultaList = resultadosConsultaRepository.findByUserId(userId);

        double valorTotalRecibir = resultadosConsultaList.stream()
                .mapToDouble(ResultadosConsulta::getValorRecibir)
                .sum();
        resultadosCartera.setValorTotalRecibir(valorTotalRecibir);

        double totalTceaPonderada = resultadosConsultaList.stream()
                .mapToDouble(consulta -> consulta.getTceaPorcentaje() * consulta.getValorRecibir())
                .sum();
        if (valorTotalRecibir != 0) {
            double tceaPromedio = totalTceaPonderada / valorTotalRecibir;
            resultadosCartera.setTcea(tceaPromedio);
        } else {
            resultadosCartera.setTcea(0.0);
        }

        List<String> resultadosConsultaIds = resultadosConsultaList.stream()
                .map(ResultadosConsulta::getId)
                .collect(Collectors.toList());
        resultadosCartera.setResultadosConsultaIds(resultadosConsultaIds);

        // Guardar la nueva cartera
        resultadosCarteraRepository.save(resultadosCartera);

        // Convertir a DTO
        ResultadosCarteraDTO resultadosCarteraDTO = new ResultadosCarteraDTO();
        resultadosCarteraDTO.setValorTotalRecibir(resultadosCartera.getValorTotalRecibir());
        resultadosCarteraDTO.setTcea(resultadosCartera.getTcea());
        return resultadosCarteraDTO;
    }


}