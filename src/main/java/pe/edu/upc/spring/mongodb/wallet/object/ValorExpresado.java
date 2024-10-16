package pe.edu.upc.spring.mongodb.wallet.object;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ValorExpresado {
    private boolean esPorcentaje; // Indica si es un porcentaje
    private Double valor; // Valor del gasto (puede ser monto o porcentaje)
}