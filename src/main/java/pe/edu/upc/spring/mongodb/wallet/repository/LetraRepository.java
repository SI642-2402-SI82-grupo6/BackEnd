package pe.edu.upc.spring.mongodb.wallet.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import pe.edu.upc.spring.mongodb.wallet.model.Letra;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LetraRepository extends MongoRepository<Letra, String> {

}
