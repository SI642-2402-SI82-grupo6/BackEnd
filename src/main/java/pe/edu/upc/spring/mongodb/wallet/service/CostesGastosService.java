package pe.edu.upc.spring.mongodb.wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public Optional<CostesGastos> getCostesGastosById(String id) {
        return costesGastosRepository.findById(id);
    }

    public CostesGastos createCostesGastos(CostesGastosDTO costesGastos) {
        Optional<DocumentosCreados> lastCreatedDoc = documentosCreadosRepository.findLastCreated();
        if (!lastCreatedDoc.isPresent()) {
            throw new RuntimeException("No DocumentosCreados found.");
        }
        String documentoId = lastCreatedDoc.get().getIdDocumento();
        CostesGastos costesGastosEntity = new CostesGastos(costesGastos);
        costesGastosEntity.setDocumentoId(documentoId);
        return costesGastosRepository.save(costesGastosEntity);
    }

    public Optional<CostesGastos> updateCostesGastos(String id, CostesGastos costesGastosDetails) {
        return costesGastosRepository.findById(id).map(costesGastos -> {
            costesGastosDetails.setId(id);
            return costesGastosRepository.save(costesGastosDetails);
        });
    }

    public boolean deleteCostesGastos(String id) {
        if (costesGastosRepository.existsById(id)) {
            costesGastosRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public Optional<CostesGastos> getCosteGastoById(String id) {
        return costesGastosRepository.findById(id);
    }
}