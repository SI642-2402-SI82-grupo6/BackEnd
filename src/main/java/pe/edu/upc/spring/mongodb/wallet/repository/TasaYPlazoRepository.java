package pe.edu.upc.spring.mongodb.wallet.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pe.edu.upc.spring.mongodb.wallet.model.TasaYPlazo;

import java.util.List;
import java.util.Optional;

public interface TasaYPlazoRepository extends MongoRepository<TasaYPlazo,String> {
    Optional<TasaYPlazo> findByDocumentoId(String documentoId);
}
