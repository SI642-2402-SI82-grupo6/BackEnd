package pe.edu.upc.spring.mongodb.wallet.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import pe.edu.upc.spring.mongodb.wallet.model.DocumentosCreados;

import java.util.List;
import java.util.Optional;

@EnableMongoRepositories
public interface DocumentosCreadosRepository extends MongoRepository<DocumentosCreados, String> {

    @Query(value = "{}", sort = "{ 'fechaCreacion' : -1 }")
    List<DocumentosCreados> findLastCreated(Pageable pageable);

    default Optional<DocumentosCreados> findLastCreated() {
        return findLastCreated(PageRequest.of(0, 1)).stream().findFirst();
    }


    Optional<DocumentosCreados> findByDocumentoId(String documentoId);
    void deleteByDocumentoId(String documentoId);
}