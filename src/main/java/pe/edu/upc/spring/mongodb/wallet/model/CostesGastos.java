package pe.edu.upc.spring.mongodb.wallet.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import pe.edu.upc.spring.mongodb.security.security.services.UserDetailsImpl;
import pe.edu.upc.spring.mongodb.wallet.DTO.request.CostesGastosDTORequest;
import pe.edu.upc.spring.mongodb.wallet.DTO.response.CostesGastosDTO;
import pe.edu.upc.spring.mongodb.wallet.object.IdGenerator;
import pe.edu.upc.spring.mongodb.wallet.object.MotivoGasto;
import pe.edu.upc.spring.mongodb.wallet.object.TipoGasto;
import pe.edu.upc.spring.mongodb.wallet.object.ValorExpresado;

@Data
@NoArgsConstructor
@Document(collection = "costes_gastos")
@Getter

public class CostesGastos {
    @Id
    private String id;
    private String userId;
    private String documentoId;
    private TipoGasto tipoGasto; // Inicial o Final
    private MotivoGasto motivoGasto; // Motivo del gasto
    private ValorExpresado valorExpresado; // Valor expresado

    public CostesGastos(CostesGastosDTO costesGastos) {;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsImpl) {
            this.userId = ((UserDetailsImpl) principal).getId();
        } else {
            this.userId = principal.toString();
        }

        this.tipoGasto = costesGastos.getTipoGasto();
        this.motivoGasto = costesGastos.getMotivoGasto();
        this.valorExpresado = costesGastos.getValorExpresado();
    }
    public CostesGastos(CostesGastosDTORequest costesGastos) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsImpl) {
            this.userId = ((UserDetailsImpl) principal).getId();
        } else {
            this.userId = principal.toString();
        }

        this.tipoGasto = costesGastos.getTipoGasto();
        this.motivoGasto = costesGastos.getMotivoGasto();
        this.valorExpresado = costesGastos.getValorExpresado();
    }
    public void setId(){
        this.id = IdGenerator.generateUniqueId();
    }
    public CostesGastosDTO toDTO() {
        CostesGastosDTO costesGastosDTO = new CostesGastosDTO();
        costesGastosDTO.setId(this.id);
        costesGastosDTO.setTipoGasto(this.tipoGasto);
        costesGastosDTO.setMotivoGasto(this.motivoGasto);
        costesGastosDTO.setValorExpresado(this.valorExpresado);
        return costesGastosDTO;
    }
}