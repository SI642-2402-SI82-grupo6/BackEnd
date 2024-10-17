package pe.edu.upc.spring.mongodb.wallet.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import pe.edu.upc.spring.mongodb.wallet.model.DocumentosCreados;

import java.util.Optional;
@EnableMongoRepositories
public interface DocumentosCreadosRepository extends MongoRepository<DocumentosCreados, String> {

}
