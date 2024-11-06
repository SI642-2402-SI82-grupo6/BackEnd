package pe.edu.upc.spring.mongodb.wallet.DTO.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CarteraResource {
    private String name;
    private String typeMoney;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate creationDate;
    private List<String> documentosCreadosIds;
    private Double valorTotalRecibir;
    private Double tcea;
}
