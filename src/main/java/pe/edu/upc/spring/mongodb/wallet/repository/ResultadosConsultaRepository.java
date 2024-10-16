package pe.edu.upc.spring.mongodb.wallet.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pe.edu.upc.spring.mongodb.wallet.model.ResultadosConsulta;

import java.time.LocalDate;
import java.util.List;

public interface ResultadosConsultaRepository extends MongoRepository<ResultadosConsulta, String> {
    List<ResultadosConsulta> findByUserId(String userId);
    List<ResultadosConsulta> findByUserIdAndFechaEmision(String userId, LocalDate fechaEmision);
    List<ResultadosConsulta> findByUserIdAndFechaVencimiento(String userId, LocalDate fechaVencimiento);
    List<ResultadosConsulta> findByUserIdAndMonto(String userId, Double monto);
    List<ResultadosConsulta> findByUserIdAndInteres(String userId, Double interes);
    List<ResultadosConsulta> findByUserIdAndFechaEmisionAndFechaVencimiento(String userId, LocalDate fechaEmision, LocalDate fechaVencimiento);
    List<ResultadosConsulta> findByUserIdAndFechaEmisionAndMonto(String userId, LocalDate fechaEmision, Double monto);
    List<ResultadosConsulta> findByUserIdAndFechaEmisionAndInteres(String userId, LocalDate fechaEmision, Double interes);
    List<ResultadosConsulta> findByUserIdAndFechaVencimientoAndMonto(String userId, LocalDate fechaVencimiento, Double monto);
    List<ResultadosConsulta> findByUserIdAndFechaVencimientoAndInteres(String userId, LocalDate fechaVencimiento, Double interes);
    List<ResultadosConsulta> findByUserIdAndMontoAndInteres(String userId, Double monto, Double interes);
    List<ResultadosConsulta> findByUserIdAndFechaEmisionAndFechaVencimientoAndMonto(String userId, LocalDate fechaEmision, LocalDate fechaVencimiento, Double monto);
    List<ResultadosConsulta> findByUserIdAndFechaEmisionAndFechaVencimientoAndInteres(String userId, LocalDate fechaEmision, LocalDate fechaVencimiento, Double interes);
    List<ResultadosConsulta> findByUserIdAndFechaEmisionAndMontoAndInteres(String userId, LocalDate fechaEmision, Double monto, Double interes);
    List<ResultadosConsulta> findByUserIdAndFechaVencimientoAndMontoAndInteres(String userId, LocalDate fechaVencimiento, Double monto, Double interes);
    List<ResultadosConsulta> findByUserIdAndFechaEmisionAndFechaVencimientoAndMontoAndInteres(String userId, LocalDate fechaEmision, LocalDate fechaVencimiento, Double monto, Double interes);
}
