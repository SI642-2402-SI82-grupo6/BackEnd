package pe.edu.upc.spring.mongodb.wallet.DTO.request;

import lombok.Data;
import pe.edu.upc.spring.mongodb.wallet.object.MotivoGasto;
import pe.edu.upc.spring.mongodb.wallet.object.TipoGasto;
import pe.edu.upc.spring.mongodb.wallet.object.ValorExpresado;

@Data
public class CostesGastosDTORequest {
    private TipoGasto tipoGasto; // Inicial o Final
    private MotivoGasto motivoGasto; // Motivo del gasto
    private ValorExpresado valorExpresado; // Valor expresado
}
