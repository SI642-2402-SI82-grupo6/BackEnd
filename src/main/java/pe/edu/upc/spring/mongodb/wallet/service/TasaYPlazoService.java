package pe.edu.upc.spring.mongodb.wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.spring.mongodb.wallet.DTO.request.TasaYPlazoDTO;
import pe.edu.upc.spring.mongodb.wallet.exception.ResourceAlreadyExistsException;
import pe.edu.upc.spring.mongodb.wallet.model.TasaYPlazo;
import pe.edu.upc.spring.mongodb.wallet.repository.TasaYPlazoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TasaYPlazoService {

    @Autowired
    private TasaYPlazoRepository tasaYPlazoRepository;

    public List<TasaYPlazo> getAllTasaYPlazo() {
        return tasaYPlazoRepository.findAll();
    }

    public Optional<TasaYPlazoDTO> getTasaYPlazoById(String id) {
        Optional<TasaYPlazo> tasaYPlazoOpt = tasaYPlazoRepository.findById(id);
        if (tasaYPlazoOpt.isPresent()) {
            TasaYPlazo tasaYPlazo = tasaYPlazoOpt.get();
            TasaYPlazoDTO tasaYPlazoDTO = tasaYPlazo.toDTO(tasaYPlazo);
            return Optional.of(tasaYPlazoDTO);
        } else {
            return Optional.empty();
        }
    }
    public TasaYPlazoDTO createTasaYPlazo(TasaYPlazoDTO tasaYPlazoDTO) {
        TasaYPlazo tasaYPlazo = new TasaYPlazo(tasaYPlazoDTO);

        // Check if a TasaYPlazo with the same userId already exists
        Optional<TasaYPlazo> existingTasaYPlazo = tasaYPlazoRepository.findByUserId(tasaYPlazo.getUserId());
        if (existingTasaYPlazo.isPresent()) {
            throw new ResourceAlreadyExistsException("TasaYPlazo with userId " + tasaYPlazo.getUserId() + " already exists.");
        }

        // Save the new TasaYPlazo
        tasaYPlazoRepository.save(tasaYPlazo);
        return tasaYPlazo.toDTO(tasaYPlazo);
    }

    public Optional<TasaYPlazo> updateTasaYPlazo(String id, TasaYPlazo tasaYPlazoDetails) {
        return tasaYPlazoRepository.findById(id).map(tasaYPlazo -> {
            tasaYPlazoDetails.setId(id);
            tasaYPlazoRepository.save(tasaYPlazoDetails);
            return tasaYPlazoRepository.save(tasaYPlazoDetails);
        });
    }

    public boolean deleteTasaYPlazo(String id) {
        if (tasaYPlazoRepository.existsById(id)) {
            tasaYPlazoRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public List<TasaYPlazo> getAllTasasYPlazos() {
        return tasaYPlazoRepository.findAll();
    }
    public boolean deleteTasaYPlazoByUserId(String userId) {
        Optional<TasaYPlazo> tasaYPlazo = tasaYPlazoRepository.findByUserId(userId);
        if (tasaYPlazo.isPresent()) {
            tasaYPlazoRepository.deleteByUserId(tasaYPlazo.get().getUserId());
            return true;
        }
        return false;
    }

}