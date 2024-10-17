package pe.edu.upc.spring.mongodb.wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.spring.mongodb.wallet.model.ResultadosCartera;
import pe.edu.upc.spring.mongodb.wallet.model.ResultadosConsulta;
import pe.edu.upc.spring.mongodb.wallet.DTO.response.ResultadosCarteraDTO;
import pe.edu.upc.spring.mongodb.wallet.repository.ResultadosCarteraRepository;
import pe.edu.upc.spring.mongodb.wallet.repository.ResultadosConsultaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ResultadosCarteraService {

    @Autowired
    private ResultadosCarteraRepository resultadosCarteraRepository;

    @Autowired
    private ResultadosConsultaRepository resultadosConsultaRepository;


    public ResultadosCarteraDTO consultarCartera(String userId) {
        // Obtener la cartera actual del usuario
        Optional<ResultadosCartera> carteraOpt = resultadosCarteraRepository.findByUserId(userId);
        ResultadosCartera resultadosCartera;

        // Si la cartera no existe, crear una nueva
        if (carteraOpt.isEmpty()) {
            resultadosCartera = new ResultadosCartera();
            resultadosCartera.setUserId(userId);
            resultadosCartera.setId(); // Asignar un nuevo ID a la cartera
        } else {
            resultadosCartera = carteraOpt.get();
        }

        // Obtener los resultados de consulta asociados a esta cartera
        List<ResultadosConsulta> resultadosConsultaList = resultadosConsultaRepository
                .findByUserId(userId); // Suponiendo que todos los resultados de consulta pertenecen al usuario


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
                .toList();
        resultadosCartera.setResultadosConsultaIds(resultadosConsultaIds);

        resultadosCarteraRepository.save(resultadosCartera);


        return resultadosCartera.toResultadosCarteraDTO();
    }
    // Obtener resultados de cartera por userId
    public ResultadosCartera obtenerResultadosCartera(String userId) {
        return resultadosCarteraRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Resultados de cartera no encontrados para el usuario: " + userId));
    }


    public ResultadosCarteraDTO obtenerResultadosCarteraDTO(String userId) {
        ResultadosCartera resultadosCartera = obtenerResultadosCartera(userId);
        ResultadosCarteraDTO dto = new ResultadosCarteraDTO();
        dto.setValorTotalRecibir(resultadosCartera.getValorTotalRecibir());
        dto.setTcea(resultadosCartera.getTcea());
        return dto;
    }


    public ResultadosCartera actualizarResultadosCartera(String userId) {
        // Lógica para actualizar los resultados de la cartera
        ResultadosCartera resultadosCartera = obtenerResultadosCartera(userId);
        // Realiza la actualización necesaria aquí
        return resultadosCartera;
    }
}
