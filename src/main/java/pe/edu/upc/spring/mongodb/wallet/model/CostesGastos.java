package pe.edu.upc.spring.mongodb.wallet.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pe.edu.upc.spring.mongodb.wallet.object.MotivoGasto;
import pe.edu.upc.spring.mongodb.wallet.object.TipoGasto;
import pe.edu.upc.spring.mongodb.wallet.object.ValorExpresado;

@Data
@NoArgsConstructor
@Document(collection = "costes_gastos")
public class CostesGastos {
    @Id
    private String id;
    private String userId;
    private TipoGasto tipoGasto; // Inicial o Final
    private MotivoGasto motivoGasto; // Motivo del gasto
    private ValorExpresado valorExpresado; // Valor expresado
}