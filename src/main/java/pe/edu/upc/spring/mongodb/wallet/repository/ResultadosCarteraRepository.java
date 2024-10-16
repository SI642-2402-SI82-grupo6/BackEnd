package pe.edu.upc.spring.mongodb.wallet.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pe.edu.upc.spring.mongodb.wallet.model.ResultadosCartera;

import java.time.LocalDate;
import java.util.List;

public interface ResultadosCarteraRepository extends MongoRepository<ResultadosCartera, String> {
    List<ResultadosCartera> findByUserId(String userId);
    List<ResultadosCartera> findByUserIdAndFechaEmision(String userId, LocalDate fechaEmision);
    List<ResultadosCartera> findByUserIdAndFechaVencimiento(String userId, LocalDate fechaVencimiento);
    List<ResultadosCartera> findByUserIdAndMonto(String userId, Double monto);
    List<ResultadosCartera> findByUserIdAndInteres(String userId, Double interes);
    List<ResultadosCartera> findByUserIdAndFechaEmisionAndFechaVencimiento(String userId, LocalDate fechaEmision, LocalDate fechaVencimiento);
    List<ResultadosCartera> findByUserIdAndFechaEmisionAndMonto(String userId, LocalDate fechaEmision, Double monto);
    List<ResultadosCartera> findByUserIdAndFechaEmisionAndInteres(String userId, LocalDate fechaEmision, Double interes);
    List<ResultadosCartera> findByUserIdAndFechaVencimientoAndMonto(String userId, LocalDate fechaVencimiento, Double monto);
    List<ResultadosCartera> findByUserIdAndFechaVencimientoAndInteres(String userId, LocalDate fechaVencimiento, Double interes);
    List<ResultadosCartera> findByUserIdAndMontoAndInteres(String userId, Double monto, Double interes);
    List<ResultadosCartera> findByUserIdAndFechaEmisionAndFechaVencimientoAndMonto(String userId, LocalDate fechaEmision, LocalDate fechaVencimiento, Double monto);
    List<ResultadosCartera> findByUserIdAndFechaEmisionAndFechaVencimientoAndInteres(String userId, LocalDate fechaEmision, LocalDate fechaVencimiento, Double interes);
    List<ResultadosCartera> findByUserIdAndFechaEmisionAndMontoAndInteres(String userId, LocalDate fechaEmision, Double monto, Double interes);
    List<ResultadosCartera> findByUserIdAndFechaVencimientoAndMontoAndInteres(String userId, LocalDate fechaVencimiento, Double monto, Double interes);
    List<ResultadosCartera> findByUserIdAndFechaEmisionAndFechaVencimientoAndMontoAndInteres(String userId, LocalDate fechaEmision, LocalDate fechaVencimiento, Double monto, Double interes);
}
