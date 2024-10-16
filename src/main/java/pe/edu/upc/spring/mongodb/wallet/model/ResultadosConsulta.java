package pe.edu.upc.spring.mongodb.wallet.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Document(collection = "resultados_consulta")
public class ResultadosConsulta {
    @Id
    private String id; // ID del resultado de la consulta
    private String userId; // ID del usuario propietario
    private String idDocumento; // ID del documento asociado (factura o letra)
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
