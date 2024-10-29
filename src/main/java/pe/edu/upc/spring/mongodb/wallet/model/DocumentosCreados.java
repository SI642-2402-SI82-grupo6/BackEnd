package pe.edu.upc.spring.mongodb.wallet.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pe.edu.upc.spring.mongodb.wallet.object.TipoDocumento;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Document(collection = "documentos_creados")
public class DocumentosCreados {
    @Id
    private String id;
    private String userId;  // ID del usuario
    private String documentoId;  // ID del documento (factura o letra)
    private TipoDocumento tipoDocumento;  // Enum para diferenciar entre Factura y Letra
    private LocalDate fechaCreacion;  // Fecha de creación del documento

    public DocumentosCreados(String userId, String documentoId, TipoDocumento tipoDocumento, LocalDate fechaCreacion) {

        this.userId = userId;
        this.documentoId = documentoId;
        this.tipoDocumento = tipoDocumento;
        this.fechaCreacion = fechaCreacion;
    }
}
