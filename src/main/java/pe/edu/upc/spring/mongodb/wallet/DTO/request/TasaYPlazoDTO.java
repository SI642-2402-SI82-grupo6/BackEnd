package pe.edu.upc.spring.mongodb.wallet.DTO.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import pe.edu.upc.spring.mongodb.wallet.object.TipoTasa;

import java.util.Date;

@Data
public class TasaYPlazoDTO {
    private TipoTasa tipoTasa; // Tipo de tasa: efectiva o nominal
    private Double tasaNominal; // %

    private int plazoDeTasa; // P
    private int periodoCapital; // Diario, mensual, etc.
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fechaDescuento; // Fecha de descuento para tasa nominal
    private Double tasaEfectiva; // %
    private Integer plazoEfectivo; // En meses o años


}
