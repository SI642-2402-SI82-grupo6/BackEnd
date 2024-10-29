package pe.edu.upc.spring.mongodb.wallet.DTO.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDate;

@Data
public class LetraDTO {
    private String id; // ID de la letra
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaGiro; // Fecha de giro (dia/mes/año)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaVencimiento; // Fecha de vencimiento (dia/mes/año)
    private Double valorNominal; // Valor nominal
    private Double retencion; // Retención
}
