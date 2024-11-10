package pe.edu.upc.spring.mongodb.wallet.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import pe.edu.upc.spring.mongodb.security.security.services.UserDetailsImpl;
import pe.edu.upc.spring.mongodb.wallet.DTO.response.ResultadosConsultaDTO;
import pe.edu.upc.spring.mongodb.wallet.object.IdGenerator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
@Document(collection = "resultados_consulta")
public class ResultadosConsulta {
    @Id
    private String id; // ID del resultado de la consulta
    private String userId; // ID del usuario propietario
    private String documentoId; // ID del documento asociado (factura o letra)
    private String type; // Tipo de documento

    private String numeroConsulta; // Nº de consulta
    private LocalDate fechaGiro; // Fecha Giro
    private Double valorNomAplicando; // Val. Nom. aplicando
    private LocalDate fechaVencimiento; // Fecha Ven.
    private int dias; // Días
    private Double retencion= 0.0; // Retención
    private Double tasaEfectiva= 0.0; // TE %
    private Double descuento= 0.0; // d %
    private Double valorDescuento= 0.0;
    private Double costeInicial = 0.0;
    private Double costeFinal = 0.0;
    private Double valorNeto= 0.0; // Val. Neto
    private Double valorRecibir= 0.0; // Val. Rec.
    private Double valorEntregado= 0.0; // Val. Ent.
    private Double tceaPorcentaje= 0.0;// TCEA %

    public void setId(){
        this.id = IdGenerator.generateUniqueId();
    }
    public void sumCosteInicial(Double costeInicial) {
        if (costeInicial != null) {
            this.costeInicial += costeInicial;
        }
    }

    public void sumCosteFinal(Double costeFinal) {
        if (costeFinal != null) {
            this.costeFinal += costeFinal;
        }
    }

    public String getDocumentoId() {
        return this.documentoId;
    }

    public void setDocumentoId(String documentoId) {
        this.documentoId = documentoId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNumeroConsulta() {
        return numeroConsulta;
    }

    public void setNumeroConsulta(String numeroConsulta) {
        this.numeroConsulta = numeroConsulta;
    }
    public void CalcularDias(LocalDate fechaVencimiento, LocalDate fechaDescuento) {
        if (fechaVencimiento != null && fechaDescuento != null) {
            this.dias = (int) ChronoUnit.DAYS.between(fechaDescuento, fechaVencimiento);
        } else {
            throw new IllegalArgumentException("FechaVencimiento y FechaDescuento no deben ser nulas");
        }
    }

    public void CalcularTEDiasTasaNominal(double tasaNominal, int plazoDeTasa, int periodoCapital) {
        double m = (double) plazoDeTasa / periodoCapital;
        double n =(double) dias/periodoCapital;
        this.tasaEfectiva = Math.pow(1 + tasaNominal/m, n) - 1;
    }
    public void CalcularTEdiasTasaEfectiva(Double tasaEfectiva,Integer plazoEfectivo){
        this.tasaEfectiva = Math.pow(1 + tasaEfectiva, (double) dias/plazoEfectivo) - 1;
    }
    public void CalcularDescuento(){
        this.descuento = this.tasaEfectiva/(1+this.tasaEfectiva);
    }
    public void CalcularValorDescuento(){
        this.valorDescuento = this.valorNomAplicando*this.descuento;
    }
    public void CaluclarValorNeto(){
        this.valorNeto= this.valorNomAplicando*(1-descuento);
    }
    public void CalcularValorRecibir(){
        this.valorRecibir = this.valorNeto - this.retencion;
    }
    public void CalcularValorEntregado(){
        this.valorEntregado = this.valorRecibir+this.costeInicial+this.costeFinal+this.valorDescuento;
    }

    public void CalcularTceaPorcentaje(){
        this.tceaPorcentaje = Math.pow(this.valorEntregado/this.valorRecibir, (double)365/dias) - 1;
    }







    public ResultadosConsultaDTO toResultadosConsultaDTO(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsImpl) {
            this.userId = ((UserDetailsImpl) principal).getId();
        } else {
            this.userId = principal.toString();
        }
        ResultadosConsultaDTO resultadosConsultaDTO = new ResultadosConsultaDTO();
        resultadosConsultaDTO.setDocumentoId(this.documentoId);
        resultadosConsultaDTO.setId(this.id);
        resultadosConsultaDTO.setType(this.type);
        resultadosConsultaDTO.setNumeroConsulta(this.numeroConsulta);
        resultadosConsultaDTO.setFechaGiro(this.fechaGiro);
        resultadosConsultaDTO.setValorNomAplicando(this.valorNomAplicando);
        resultadosConsultaDTO.setFechaVencimiento(this.fechaVencimiento);
        resultadosConsultaDTO.setDias(this.dias);
        resultadosConsultaDTO.setRetencion(this.retencion);
        resultadosConsultaDTO.setTasaEfectiva(this.tasaEfectiva);
        resultadosConsultaDTO.setDescuento(this.descuento);
        resultadosConsultaDTO.setValorDescuento(this.valorDescuento);
        resultadosConsultaDTO.setCosteInicial(this.costeInicial);
        resultadosConsultaDTO.setCosteFinal(this.costeFinal);
        resultadosConsultaDTO.setValorNeto(this.valorNeto);
        resultadosConsultaDTO.setValorRecibir(this.valorRecibir);
        resultadosConsultaDTO.setValorEntregado(this.valorEntregado);
        resultadosConsultaDTO.setTceaPorcentaje(this.tceaPorcentaje);
        return resultadosConsultaDTO;
    }
}
