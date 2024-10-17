package pe.edu.upc.spring.mongodb.wallet.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import pe.edu.upc.spring.mongodb.wallet.model.CostesGastos;


import java.util.List;
import java.util.Optional;
@EnableMongoRepositories
public interface CostesGastosRepository extends MongoRepository<CostesGastos, String> {
    Optional<CostesGastos> findByDocumentoId(String documentoId);
}
