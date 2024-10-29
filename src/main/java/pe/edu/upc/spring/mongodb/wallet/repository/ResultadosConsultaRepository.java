package pe.edu.upc.spring.mongodb.wallet.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import pe.edu.upc.spring.mongodb.wallet.model.ResultadosConsulta;

import java.time.LocalDate;
import java.util.List;

public interface ResultadosConsultaRepository extends MongoRepository<ResultadosConsulta, String> {
    List<ResultadosConsulta> findByUserId(String userId);
    List<ResultadosConsulta> findByDocumentoId(String documentoId);
   }
