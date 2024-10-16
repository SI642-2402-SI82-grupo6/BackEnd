package pe.edu.upc.spring.mongodb.wallet.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pe.edu.upc.spring.mongodb.wallet.model.Letra;

import java.time.LocalDate;
import java.util.List;
public interface LetraRepository extends MongoRepository<Letra, String> {
    List<Letra> findByUserId(String userId);
    List<Letra> findByUserIdAndFechaEmision(String userId, LocalDate fechaEmision);
    List<Letra> findByUserIdAndFechaVencimiento(String userId, LocalDate fechaVencimiento);
    List<Letra> findByUserIdAndMonto(String userId, Double monto);
    List<Letra> findByUserIdAndInteres(String userId, Double interes);
    List<Letra> findByUserIdAndFechaEmisionAndFechaVencimiento(String userId, LocalDate fechaEmision, LocalDate fechaVencimiento);
    List<Letra> findByUserIdAndFechaEmisionAndMonto(String userId, LocalDate fechaEmision, Double monto);
    List<Letra> findByUserIdAndFechaEmisionAndInteres(String userId, LocalDate fechaEmision, Double interes);
    List<Letra> findByUserIdAndFechaVencimientoAndMonto(String userId, LocalDate fechaVencimiento, Double monto);
    List<Letra> findByUserIdAndFechaVencimientoAndInteres(String userId, LocalDate fechaVencimiento, Double interes);
    List<Letra> findByUserIdAndMontoAndInteres(String userId, Double monto, Double interes);
    List<Letra> findByUserIdAndFechaEmisionAndFechaVencimientoAndMonto(String userId, LocalDate fechaEmision, LocalDate fechaVencimiento, Double monto);
    List<Letra> findByUserIdAndFechaEmisionAndFechaVencimientoAndInteres(String userId, LocalDate fechaEmision, LocalDate fechaVencimiento, Double interes);
    List<Letra> findByUserIdAndFechaEmisionAndMontoAndInteres(String userId, LocalDate fechaEmision, Double monto, Double interes);
    List<Letra> findByUserIdAndFechaVencimientoAndMontoAndInteres(String userId, LocalDate fechaVencimiento, Double monto, Double interes);
    List<Letra> findByUserIdAndFechaEmisionAndFechaVencimientoAndMontoAndInteres(String userId, LocalDate fechaEmision, LocalDate fechaVencimiento, Double monto, Double interes);
}
