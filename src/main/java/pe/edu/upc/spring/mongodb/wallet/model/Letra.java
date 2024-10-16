package pe.edu.upc.spring.mongodb.wallet.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Document(collection = "letras")
public class Letra {
    @Id
    private String id; // ID de la letra
    private String userId; // ID del usuario propietario
    private LocalDate fechaGiro; // Fecha de giro (dia/mes/año)
    private LocalDate fechaVencimiento; // Fecha de vencimiento (dia/mes/año)
    private Double valorNominal; // Valor nominal
    private Double retencion; // Retención

    // Puedes añadir otros atributos o métodos según lo necesites
}
