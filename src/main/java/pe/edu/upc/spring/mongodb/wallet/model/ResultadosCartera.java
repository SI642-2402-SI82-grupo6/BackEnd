package pe.edu.upc.spring.mongodb.wallet.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import pe.edu.upc.spring.mongodb.security.security.services.UserDetailsImpl;
import pe.edu.upc.spring.mongodb.wallet.DTO.response.ResultadosCarteraDTO;
import pe.edu.upc.spring.mongodb.wallet.object.IdGenerator;

import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "resultados_cartera")
public class ResultadosCartera {
    @Id
    private String id; // ID del resultado de la cartera
    private String userId; // ID del usuario propietario
    private Double valorTotalRecibir; // VR: Valor Total a Recibir por la cartera
    private Double tcea; // TCEA: Tasa de Coste Efectiva Anual de la cartera

    // Lista de IDs de resultados de consulta
    private List<String> resultadosConsultaIds;
    public void setId(){
        this.id = IdGenerator.generateUniqueId();
    }


    public ResultadosCarteraDTO toResultadosCarteraDTO(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsImpl) {
            this.userId = ((UserDetailsImpl) principal).getId();
        } else {
            this.userId = principal.toString();
        }
        ResultadosCarteraDTO resultadosCarteraDTO = new ResultadosCarteraDTO();
        resultadosCarteraDTO.setId(this.id);
        resultadosCarteraDTO.setValorTotalRecibir(this.valorTotalRecibir);
        resultadosCarteraDTO.setTcea(this.tcea);
        return resultadosCarteraDTO;
    }
}
