package pe.edu.upc.spring.mongodb.wallet.DTO;

import lombok.Data;
import pe.edu.upc.spring.mongodb.wallet.object.TipoTasa;

import java.util.Date;

@Data
public class TasaYPlazoDTO {
    private TipoTasa tipoTasa; // Tipo de tasa: efectiva o nominal
    private Double tasaNominal; // %
    private Integer diasPorAnio; // DA
    private String plazoDeTasa; // P
    private String periodoCapital; // Diario, mensual, etc.
    private Date fechaDescuento; // Fecha de descuento para tasa nominal
    private Double tasaEfectiva; // %
    private Integer plazoEfectivo; // En meses o años


}
