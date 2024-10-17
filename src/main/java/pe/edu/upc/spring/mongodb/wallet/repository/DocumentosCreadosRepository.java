package pe.edu.upc.spring.mongodb.wallet.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pe.edu.upc.spring.mongodb.wallet.model.DocumentosCreados;

import java.util.Optional;

public interface DocumentosCreadosRepository extends MongoRepository<DocumentosCreados, String> {
    Optional<DocumentosCreados> findTopByUserIdOrderByFechaCreacionDesc(String userId);
}
