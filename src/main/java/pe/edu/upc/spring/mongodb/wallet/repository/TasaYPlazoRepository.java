package pe.edu.upc.spring.mongodb.wallet.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import pe.edu.upc.spring.mongodb.wallet.model.TasaYPlazo;

import java.util.List;
import java.util.Optional;
@EnableMongoRepositories
public interface TasaYPlazoRepository extends MongoRepository<TasaYPlazo,String> {

    @Query(value = "{}", sort = "{ '_id' : -1 }")
    List<TasaYPlazo> findLastAdded(Pageable pageable);

    Optional<TasaYPlazo> findByUserId(String userId);

    Optional<TasaYPlazo> findByDocumentoId(String documentoId);



    void deleteByUserId(String userId);


}
