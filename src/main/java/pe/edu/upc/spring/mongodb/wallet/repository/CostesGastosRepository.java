package pe.edu.upc.spring.mongodb.wallet.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pe.edu.upc.spring.mongodb.wallet.model.CostesGastos;


import java.util.List;
import java.util.Optional;

public interface CostesGastosRepository extends MongoRepository<CostesGastos, String> {
    Optional<CostesGastos> findByDocumentoId(String documentoId);
}
