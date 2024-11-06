package pe.edu.upc.spring.mongodb.wallet.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pe.edu.upc.spring.mongodb.wallet.model.Cartera;

public interface CarteraRepository extends MongoRepository<Cartera, String> {

}
