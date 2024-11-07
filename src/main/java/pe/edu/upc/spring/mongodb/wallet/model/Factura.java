package pe.edu.upc.spring.mongodb.wallet.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import pe.edu.upc.spring.mongodb.security.security.services.UserDetailsImpl;
import pe.edu.upc.spring.mongodb.wallet.DTO.request.FacturaDTORequest;
import pe.edu.upc.spring.mongodb.wallet.DTO.response.FacturaDTO;
import pe.edu.upc.spring.mongodb.wallet.object.IdGenerator;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Document(collection = "facturas")
public class Factura {
    @Id
    private String id; // ID de la factura
    private String userId; // ID del usuario propietario
    private LocalDate fechaEmision; // Fecha de emisión (FE)
    private LocalDate fechaPago; // Fecha de pago (FP)
    private Double totalFacturado; // Total facturado (TF)
    private Double retencion; // Retención (Rt)

    public Factura(FacturaDTO facturaDTO){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsImpl) {
            this.userId = ((UserDetailsImpl) principal).getId();
        } else {
            this.userId = principal.toString();
        }
        this.fechaEmision = facturaDTO.getFechaEmision();
        this.fechaPago = facturaDTO.getFechaPago();
        this.totalFacturado = facturaDTO.getTotalFacturado();
        this.retencion = facturaDTO.getRetencion();
    }
    public void setId(){
        this.id = IdGenerator.generateUniqueId();
    }

    public static FacturaDTO toDTO(Factura factura){

        FacturaDTO facturaDTO = new FacturaDTO();
        facturaDTO.setId(factura.getId());
        facturaDTO.setFechaEmision(factura.getFechaEmision());
        facturaDTO.setFechaPago(factura.getFechaPago());
        facturaDTO.setTotalFacturado(factura.getTotalFacturado());
        facturaDTO.setRetencion(factura.getRetencion());
        return facturaDTO;
    }

    public Factura(FacturaDTORequest facturaDTORequest){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsImpl) {
            this.userId = ((UserDetailsImpl) principal).getId();
        } else {
            this.userId = principal.toString();
        }
        this.fechaEmision = facturaDTORequest.getFechaEmision();
        this.fechaPago = facturaDTORequest.getFechaPago();
        this.totalFacturado = facturaDTORequest.getTotalFacturado();
        this.retencion = facturaDTORequest.getRetencion();
    }
    public FacturaDTORequest toDTORequest(){

        FacturaDTORequest facturaDTORequest = new FacturaDTORequest();
        facturaDTORequest.setFechaEmision(this.fechaEmision);
        facturaDTORequest.setFechaPago(this.fechaPago);
        facturaDTORequest.setTotalFacturado(this.totalFacturado);
        facturaDTORequest.setRetencion(this.retencion);
        return facturaDTORequest;
    }

}
