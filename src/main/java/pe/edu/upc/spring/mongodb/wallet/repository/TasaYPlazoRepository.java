package pe.edu.upc.spring.mongodb.wallet.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import pe.edu.upc.spring.mongodb.wallet.model.TasaYPlazo;

import java.util.List;
import java.util.Optional;
@EnableMongoRepositories
public interface TasaYPlazoRepository extends MongoRepository<TasaYPlazo,String> {
    Optional<TasaYPlazo> findByDocumentoId(String documentoId);
}
