package pe.edu.upc.spring.mongodb.wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.spring.mongodb.wallet.DTO.request.TasaYPlazoDTO;
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

    public Optional<TasaYPlazo> getTasaYPlazoById(String id) {
        return tasaYPlazoRepository.findById(id);
    }

    public TasaYPlazo createTasaYPlazo(TasaYPlazoDTO tasaYPlazoDTO) {
        TasaYPlazo tasaYPlazo = new TasaYPlazo(tasaYPlazoDTO);
        return tasaYPlazoRepository.save(tasaYPlazo);
    }

    public Optional<TasaYPlazo> updateTasaYPlazo(String id, TasaYPlazo tasaYPlazoDetails) {
        return tasaYPlazoRepository.findById(id).map(tasaYPlazo -> {
            tasaYPlazoDetails.setId(id);
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

}