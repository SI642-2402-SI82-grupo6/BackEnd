package pe.edu.upc.spring.mongodb.wallet.DTO;

import lombok.Data;
import java.time.LocalDate;

@Data
public class LetraDTO {
    private LocalDate fechaGiro; // Fecha de giro (dia/mes/año)
    private LocalDate fechaVencimiento; // Fecha de vencimiento (dia/mes/año)
    private Double valorNominal; // Valor nominal
    private Double retencion; // Retención
}
