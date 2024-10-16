package pe.edu.upc.spring.mongodb.wallet.DTO.response;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ResultadosConsultaDTO {
    private String numeroConsulta; // Nº de consulta
    private LocalDate fechaGiro; // Fecha Giro
    private Double valorNomAplicando; // Val. Nom. aplicando
    private LocalDate fechaVencimiento; // Fecha Ven.
    private Integer dias; // Días
    private Double retencion; // Retención
    private Double tasaEfectiva; // TE %
    private Double descuento; // d %
    private Double costeInicial; // Coste Ini.
    private Double costeFinal; // Coste Fin.
    private Double valorNeto; // Val. Neto
    private Double valorRecibir; // Val. Rec.
    private Double valorEntregado; // Val. Ent.
    private Double tceaPorcentaje; // TCEA %
}
