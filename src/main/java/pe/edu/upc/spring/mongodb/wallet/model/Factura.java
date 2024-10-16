package pe.edu.upc.spring.mongodb.wallet.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Document(collection = "facturas")
public class Factura {
    @Id
    private String id; // ID de la factura
    private String userId; // ID del usuario propietario
    private LocalDate fechaEmision; // Fecha de emisión (FE)
    private LocalDate fechaPago; // Fecha de pago (FP)
    private Double totalFacturado; // Total facturado (TF)
    private Double retencion; // Retención (Rt)


}
