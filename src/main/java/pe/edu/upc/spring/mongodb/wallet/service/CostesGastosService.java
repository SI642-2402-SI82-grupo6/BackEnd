package pe.edu.upc.spring.mongodb.wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.spring.mongodb.security.payload.response.MessageResponse;
import pe.edu.upc.spring.mongodb.wallet.DTO.request.CostesGastosDTORequest;
import pe.edu.upc.spring.mongodb.wallet.DTO.response.CostesGastosDTO;
import pe.edu.upc.spring.mongodb.wallet.exception.ResourceNotFoundException;
import pe.edu.upc.spring.mongodb.wallet.exception.ResourceAlreadyExistsException;
import pe.edu.upc.spring.mongodb.wallet.model.CostesGastos;
import pe.edu.upc.spring.mongodb.wallet.model.DocumentosCreados;
import pe.edu.upc.spring.mongodb.wallet.repository.CostesGastosRepository;
import pe.edu.upc.spring.mongodb.wallet.repository.DocumentosCreadosRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CostesGastosService {

    @Autowired
    private CostesGastosRepository costesGastosRepository;

    @Autowired
    private DocumentosCreadosRepository documentosCreadosRepository;

    public List<CostesGastos> getAllCostesGastos() {
        return costesGastosRepository.findAll();
    }

    public MessageResponse createCostesGastos(CostesGastosDTORequest costesGastos) {
        Optional<DocumentosCreados> lastCreatedDoc = documentosCreadosRepository.findLastCreated();
        if (lastCreatedDoc.isEmpty()) {
            throw new ResourceNotFoundException("No documents found");
        }
        String documentoId = lastCreatedDoc.get().getDocumentoId();
        CostesGastos costesGastosEntity = new CostesGastos(costesGastos);
        costesGastosEntity.setDocumentoId(documentoId);

        if (costesGastosRepository.findByDocumentoId(documentoId).isPresent()) {
            costesGastosRepository.deleteByDocumentoId(documentoId);
        }
        costesGastosRepository.save(costesGastosEntity);
        return new MessageResponse("CostesGastos created successfully");
    }

    public Optional<CostesGastos> updateCostesGastos(String id, CostesGastos costesGastosDetails) {
        return Optional.ofNullable(costesGastosRepository.findById(id).map(costesGastos -> {
            costesGastosDetails.setId(id);
            return costesGastosRepository.save(costesGastosDetails);
        }).orElseThrow(() -> new ResourceNotFoundException("CostesGastos not found with id " + id)));
    }

    public MessageResponse deleteCostesGastos(String id) {
        if (costesGastosRepository.existsById(id)) {
            costesGastosRepository.deleteById(id);
            return new MessageResponse("CostesGastos deleted successfully");
        }
        throw new ResourceNotFoundException("CostesGastos not found with id " + id);
    }

    public Optional<CostesGastosDTO> getCosteGastoById(String id) {
        Optional<CostesGastos> costesGastosDTO = costesGastosRepository.findById(id);
        return Optional.ofNullable(costesGastosDTO.map(CostesGastos::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("CostesGastos not found with id " + id)));
    }

    public MessageResponse deleteAllCostesGastos() {
        costesGastosRepository.deleteAll();
        return new MessageResponse("All CostesGastos deleted successfully");
    }
}