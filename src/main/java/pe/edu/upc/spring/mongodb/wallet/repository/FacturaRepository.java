package pe.edu.upc.spring.mongodb.wallet.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pe.edu.upc.spring.mongodb.wallet.model.Factura;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FacturaRepository extends MongoRepository<Factura, String> {
    Optional<Factura> findTopByUserIdOrderByFechaEmisionDesc(String userId);
    List<Factura> findByUserId(String userId);
    List<Factura> findByUserIdAndFechaEmision(String userId, LocalDate fechaEmision);
    List<Factura> findByUserIdAndFechaPago(String userId, LocalDate fechaPago);
    List<Factura> findByUserIdAndTotalFacturado(String userId, Double totalFacturado);
    List<Factura> findByUserIdAndRetencion(String userId, Double retencion);
    List<Factura> findByUserIdAndFechaEmisionAndFechaPago(String userId, LocalDate fechaEmision, LocalDate fechaPago);
    List<Factura> findByUserIdAndFechaEmisionAndTotalFacturado(String userId, LocalDate fechaEmision, Double totalFacturado);
    List<Factura> findByUserIdAndFechaEmisionAndRetencion(String userId, LocalDate fechaEmision, Double retencion);
    List<Factura> findByUserIdAndFechaPagoAndTotalFacturado(String userId, LocalDate fechaPago, Double totalFacturado);
    List<Factura> findByUserIdAndFechaPagoAndRetencion(String userId, LocalDate fechaPago, Double retencion);
    List<Factura> findByUserIdAndTotalFacturadoAndRetencion(String userId, Double totalFacturado, Double retencion);
    List<Factura> findByUserIdAndFechaEmisionAndFechaPagoAndTotalFacturado(String userId, LocalDate fechaEmision, LocalDate fechaPago, Double totalFacturado);
    List<Factura> findByUserIdAndFechaEmisionAndFechaPagoAndRetencion(String userId, LocalDate fechaEmision, LocalDate fechaPago, Double retencion);
    List<Factura> findByUserIdAndFechaEmisionAndTotalFacturadoAndRetencion(String userId, LocalDate fechaEmision, Double totalFacturado, Double retencion);
    List<Factura> findByUserIdAndFechaPagoAndTotalFacturadoAndRetencion(String userId, LocalDate fechaPago, Double totalFacturado, Double retencion);
    List<Factura> findByUserIdAndFechaEmisionAndFechaPagoAndTotalFacturadoAndRetencion(String userId, LocalDate fechaEmision, LocalDate fechaPago, Double totalFacturado, Double retencion);
}
