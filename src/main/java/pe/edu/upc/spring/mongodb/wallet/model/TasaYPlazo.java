package pe.edu.upc.spring.mongodb.wallet.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import pe.edu.upc.spring.mongodb.wallet.DTO.request.TasaYPlazoDTO;
import pe.edu.upc.spring.mongodb.wallet.object.IdGenerator;
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
    private int plazoDeTasa; // P
    private int periodoCapital; // Diario, mensual, etc.
    private Date fechaDescuento; // Fecha de descuento para tasa nominal

    // Para Tasa Efectiva
    private Double tasaEfectiva; // %
    private Integer plazoEfectivo; // En meses o años

    public TasaYPlazo(TasaYPlazoDTO tasaYPlazoDTO){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            this.userId = ((UserDetails) principal).getUsername();
        } else {
            this.userId = principal.toString();
        }
        this.tipoTasa = tasaYPlazoDTO.getTipoTasa();
        if(this.tipoTasa == TipoTasa.NOMINAL){
            this.tasaNominal = tasaYPlazoDTO.getTasaNominal();

            this.plazoDeTasa = tasaYPlazoDTO.getPlazoDeTasa();
            this.periodoCapital = tasaYPlazoDTO.getPeriodoCapital();
            this.fechaDescuento = tasaYPlazoDTO.getFechaDescuento();
        } else {
            this.tasaEfectiva = tasaYPlazoDTO.getTasaEfectiva();
            this.plazoEfectivo = tasaYPlazoDTO.getPlazoEfectivo();
        }
    }
    public void setId(){
        this.id = IdGenerator.generateUniqueId();
    }

}
