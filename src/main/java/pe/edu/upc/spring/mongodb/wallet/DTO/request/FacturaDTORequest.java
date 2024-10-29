package pe.edu.upc.spring.mongodb.wallet.DTO.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
@Data
public class FacturaDTORequest {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaEmision; // Fecha de emisión (FE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaPago; // Fecha de pago (FP)
    private Double totalFacturado; // Total facturado (TF)
    private Double retencion; // Retención (Rt)
}
