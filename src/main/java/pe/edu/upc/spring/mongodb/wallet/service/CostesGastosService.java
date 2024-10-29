package pe.edu.upc.spring.mongodb.wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.spring.mongodb.security.payload.response.MessageResponse;
import pe.edu.upc.spring.mongodb.wallet.DTO.request.CostesGastosDTO;
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



    public MessageResponse createCostesGastos(CostesGastosDTO costesGastos) {
        try {
            Optional<DocumentosCreados> lastCreatedDoc = documentosCreadosRepository.findLastCreated();
            if (!lastCreatedDoc.isPresent()) {
                return new MessageResponse("Error creating CostesGastos: No documents created yet");
            }
            String documentoId = lastCreatedDoc.get().getDocumentoId();
            CostesGastos costesGastosEntity = new CostesGastos(costesGastos);
            costesGastosEntity.setDocumentoId(documentoId);

            costesGastosRepository.save(costesGastosEntity);
            return new MessageResponse("CostesGastos created successfully");
        } catch (Exception e) {
            return new MessageResponse("Error creating CostesGastos: " + e.getMessage());
        }
    }

    public Optional<CostesGastos> updateCostesGastos(String id, CostesGastos costesGastosDetails) {
        return costesGastosRepository.findById(id).map(costesGastos -> {
            costesGastosDetails.setId(id);
            return costesGastosRepository.save(costesGastosDetails);
        });
    }

    public MessageResponse deleteCostesGastos(String id) {
        if (costesGastosRepository.existsById(id)) {
            costesGastosRepository.deleteById(id);
            return new MessageResponse("CostesGastos deleted successfully");
        }
        return new MessageResponse("Error deleting CostesGastos: CostesGastos not found");
    }
    public Optional<CostesGastosDTO> getCosteGastoById(String id) {
        Optional<CostesGastos> costesGastosDTO=  costesGastosRepository.findById(id);
        return costesGastosDTO.map(CostesGastos::toDTO);
    }


}