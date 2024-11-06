package pe.edu.upc.spring.mongodb.wallet.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pe.edu.upc.spring.mongodb.wallet.DTO.resource.CarteraResource;
import pe.edu.upc.spring.mongodb.wallet.object.IdGenerator;
import pe.edu.upc.spring.mongodb.wallet.repository.ResultadosConsultaRepository;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
@Document(collection = "cartera")
public class Cartera {
    @Id
    private String id;
    private String userId;
    private String name;
    private String typeMoney;
    private LocalDate creationDate;
    private Double valorTotalRecibir;
    private Double tcea;
    private List<String> documentosCreadosIds;

    public Cartera(){
        this.id = IdGenerator.generateUniqueId();
        this.userId = "";
        this.name = "";
        this.typeMoney = "";
        this.creationDate = LocalDate.now();
        this.valorTotalRecibir = 0.0;
        this.tcea = 0.0;
        this.documentosCreadosIds = null;
    }

    public CarteraResource toResource(){
        CarteraResource carteraResource = new CarteraResource();
        carteraResource.setName(this.name);
        carteraResource.setTypeMoney(this.typeMoney);
        carteraResource.setCreationDate(this.creationDate);
        carteraResource.setDocumentosCreadosIds(this.documentosCreadosIds);
        carteraResource.setValorTotalRecibir(this.valorTotalRecibir);
        carteraResource.setTcea(this.tcea);
        return carteraResource;
    }

    public void CalcularValorTotalRecibir(double valorRecibir){
        this.valorTotalRecibir += valorRecibir;
    }








}
