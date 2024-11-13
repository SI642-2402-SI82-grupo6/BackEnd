package pe.edu.upc.spring.mongodb.wallet.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pe.edu.upc.spring.mongodb.wallet.model.Cartera;
import java.util.List;
public interface CarteraRepository extends MongoRepository<Cartera, String> {

    List<Cartera> findByUserId(String userId);
}
