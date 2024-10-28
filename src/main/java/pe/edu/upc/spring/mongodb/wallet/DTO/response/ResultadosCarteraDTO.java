package pe.edu.upc.spring.mongodb.wallet.DTO.response;

import lombok.Data;

import java.util.List;

@Data
public class ResultadosCarteraDTO {
    private Double valorTotalRecibir; // VR: Valor Total a Recibir por la cartera
    private Double tcea; // TCEA: Tasa de Coste Efectiva Anual de la cartera

}
