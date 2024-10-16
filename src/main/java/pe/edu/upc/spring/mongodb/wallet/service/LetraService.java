package pe.edu.upc.spring.mongodb.wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.spring.mongodb.wallet.DTO.request.LetraDTO;
import pe.edu.upc.spring.mongodb.wallet.model.Letra;
import pe.edu.upc.spring.mongodb.wallet.repository.LetraRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LetraService {

    @Autowired
    private LetraRepository letraRepository;

    public List<Letra> getAllLetras() {
        return letraRepository.findAll();
    }

    public Optional<Letra> getLetraById(String id) {
        return letraRepository.findById(id);
    }

    public Letra createLetra(LetraDTO letraDTO) {
        Letra letra = new Letra(letraDTO);
        return letraRepository.save(letra);
    }

    public Optional<Letra> updateLetra(String id, Letra letraDetails) {
        return letraRepository.findById(id).map(letra -> {
            letraDetails.setId(id);
            return letraRepository.save(letraDetails);
        });
    }

    public boolean deleteLetra(String id) {
        if (letraRepository.existsById(id)) {
            letraRepository.deleteById(id);
            return true;
        }
        return false;
    }
}