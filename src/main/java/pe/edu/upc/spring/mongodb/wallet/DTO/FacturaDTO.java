package pe.edu.upc.spring.mongodb.wallet.DTO;

import lombok.Data;
import java.time.LocalDate;

@Data
public class FacturaDTO {
    private LocalDate fechaEmision; // Fecha de emisión (FE)
    private LocalDate fechaPago; // Fecha de pago (FP)
    private Double totalFacturado; // Total facturado (TF)
    private Double retencion; // Retención (Rt)
}
