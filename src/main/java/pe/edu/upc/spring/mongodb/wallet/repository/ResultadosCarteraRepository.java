package pe.edu.upc.spring.mongodb.wallet.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import pe.edu.upc.spring.mongodb.wallet.model.ResultadosCartera;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ResultadosCarteraRepository extends MongoRepository<ResultadosCartera, String> {
    Optional<ResultadosCartera> findByUserId(String userId);
    void deleteByUserId(String userId);


}
