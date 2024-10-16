package pe.edu.upc.spring.mongodb.wallet.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pe.edu.upc.spring.mongodb.wallet.DTO.response.ResultadosConsultaDTO;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
    private int dias; // Días
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



    public void CalcularDias(LocalDate fechaVencimiento, LocalDate fechaDescuento) {
        this.dias= (int) ChronoUnit.DAYS.between(fechaDescuento, fechaVencimiento);
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
        this.valorNeto= this.valorNeto*(1-descuento);
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
        ResultadosConsultaDTO resultadosConsultaDTO = new ResultadosConsultaDTO();
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
