package pe.edu.upc.spring.mongodb.wallet.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pe.edu.upc.spring.mongodb.wallet.object.TipoTasa;

import java.util.Date;

@Data
@NoArgsConstructor
@Document(collection = "tasa_y_plazo")
public class TasaYPlazo {
    @Id
    private String id;
    private String userId;

    private TipoTasa tipoTasa; // Tipo de tasa: efectiva o nominal

    // Para Tasa Nominal
    private Double tasaNominal; // %
    private Integer diasPorAnio; // DA
    private String plazoDeTasa; // P
    private String periodoCapital; // Diario, mensual, etc.
    private Date fechaDescuento; // Fecha de descuento para tasa nominal

    // Para Tasa Efectiva
    private Double tasaEfectiva; // %
    private Integer plazoEfectivo; // En meses o años


}
