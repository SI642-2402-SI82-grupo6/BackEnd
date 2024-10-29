package pe.edu.upc.spring.mongodb.wallet.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import pe.edu.upc.spring.mongodb.wallet.DTO.request.LetraDTORequest;
import pe.edu.upc.spring.mongodb.wallet.DTO.response.LetraDTO;
import pe.edu.upc.spring.mongodb.wallet.object.IdGenerator;

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

    public Letra(LetraDTO letraDTO){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            this.userId = ((UserDetails) principal).getUsername();
        } else {
            this.userId = principal.toString();
        }
        this.fechaGiro = letraDTO.getFechaGiro();
        this.fechaVencimiento = letraDTO.getFechaVencimiento();
        this.valorNominal = letraDTO.getValorNominal();
        this.retencion = letraDTO.getRetencion();
    }
    public Letra(LetraDTORequest letraDTORequest ){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            this.userId = ((UserDetails) principal).getUsername();
        } else {
            this.userId = principal.toString();
        }
        this.fechaGiro = letraDTORequest.getFechaGiro();
        this.fechaVencimiento = letraDTORequest.getFechaVencimiento();
        this.valorNominal = letraDTORequest.getValorNominal();
        this.retencion = letraDTORequest.getRetencion();
    }
    public void setId(){
        this.id = IdGenerator.generateUniqueId();
    }

    public LetraDTO toDTO(){

        LetraDTO letraDTO = new LetraDTO();
        letraDTO.setId(this.id);
        letraDTO.setFechaGiro(this.fechaGiro);
        letraDTO.setFechaVencimiento(this.fechaVencimiento);
        letraDTO.setValorNominal(this.valorNominal);
        letraDTO.setRetencion(this.retencion);
        return letraDTO;
    }
    public LetraDTORequest toDTORequest(){

        LetraDTORequest letraDTORequest = new LetraDTORequest();
        letraDTORequest.setFechaGiro(this.fechaGiro);
        letraDTORequest.setFechaVencimiento(this.fechaVencimiento);
        letraDTORequest.setValorNominal(this.valorNominal);
        letraDTORequest.setRetencion(this.retencion);
        return letraDTORequest;
    }

}
