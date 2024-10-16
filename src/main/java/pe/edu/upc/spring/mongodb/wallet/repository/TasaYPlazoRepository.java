package pe.edu.upc.spring.mongodb.wallet.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pe.edu.upc.spring.mongodb.wallet.model.TasaYPlazo;

import java.util.List;

public interface TasaYPlazoRepository extends MongoRepository<TasaYPlazo,String> {
    List<TasaYPlazo> findByUserId(String userId);
    List<TasaYPlazo> findByUserIdAndTasa(String userId, Double tasa);
    List<TasaYPlazo> findByUserIdAndPlazo(String userId, Integer plazo);
    List<TasaYPlazo> findByUserIdAndTasaAndPlazo(String userId, Double tasa, Integer plazo);
}
