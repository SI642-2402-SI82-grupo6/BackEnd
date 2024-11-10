package pe.edu.upc.spring.mongodb.wallet.DTO.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ResultadosConsultaDTO {
    private String id; // ID
    private String documentoId; // ID del documento asociado (factura o letra)
    private String numeroConsulta; // Nº de consulta
    private String type; // Tipo de documento
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaGiro; // Fecha Giro
    private Double valorNomAplicando; // Val. Nom. aplicando
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaVencimiento; // Fecha Ven.
    private Integer dias; // Días
    private Double retencion; // Retención
    private Double tasaEfectiva; // TE %
    private Double descuento; // d %
    private Double valorDescuento;
    private Double costeInicial; // Coste Ini.
    private Double costeFinal; // Coste Fin.
    private Double valorNeto; // Val. Neto
    private Double valorRecibir; // Val. Rec.
    private Double valorEntregado; // Val. Ent.
    private Double tceaPorcentaje; // TCEA %
}
