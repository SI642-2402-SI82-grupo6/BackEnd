package pe.edu.upc.spring.mongodb.wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pe.edu.upc.spring.mongodb.security.payload.response.MessageResponse;
import pe.edu.upc.spring.mongodb.security.security.services.UserDetailsImpl;
import pe.edu.upc.spring.mongodb.wallet.DTO.request.TasaYPlazoDTORequest;
import pe.edu.upc.spring.mongodb.wallet.DTO.response.TasaYPlazoDTO;
import pe.edu.upc.spring.mongodb.wallet.exception.ResourceAlreadyExistsException;
import pe.edu.upc.spring.mongodb.wallet.exception.ResourceNotFoundException;
import pe.edu.upc.spring.mongodb.wallet.model.TasaYPlazo;
import pe.edu.upc.spring.mongodb.wallet.repository.DocumentosCreadosRepository;
import pe.edu.upc.spring.mongodb.wallet.repository.TasaYPlazoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TasaYPlazoService {

    @Autowired
    private TasaYPlazoRepository tasaYPlazoRepository;
    @Autowired
    private DocumentosCreadosRepository documentosCreadosRepository;

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
    public MessageResponse createTasaYPlazo(TasaYPlazoDTORequest tasaYPlazoDTO) {

        try {

            TasaYPlazo tasaYPlazo = new TasaYPlazo(tasaYPlazoDTO);
            documentosCreadosRepository.findLastCreated().ifPresent(documentosCreados -> {
                tasaYPlazo.setDocumentoId(documentosCreados.getDocumentoId());
            });

            tasaYPlazoRepository.save(tasaYPlazo);

            return new MessageResponse("TasaYPlazo created successfully");
        } catch (ResourceAlreadyExistsException e) {
            throw new ResourceNotFoundException("Error creating TasaYPlazo: " + e.getMessage());
        }

    }

    public Optional<TasaYPlazo> updateTasaYPlazo(String id, TasaYPlazo tasaYPlazoDetails) {
        return tasaYPlazoRepository.findById(id).map(tasaYPlazo -> {
            tasaYPlazoDetails.setId(id);
            tasaYPlazoRepository.save(tasaYPlazoDetails);
            return tasaYPlazoRepository.save(tasaYPlazoDetails);
        });
    }

    public MessageResponse deleteTasaYPlazo(String id) {

        if (tasaYPlazoRepository.existsById(id)) {
            tasaYPlazoRepository.deleteById(id);
            return new MessageResponse("TasaYPlazo deleted successfully");
        }
        throw new ResourceNotFoundException("Error deleting TasaYPlazo: TasaYPlazo not found");
    }
    public List<TasaYPlazo> getAllTasasYPlazos() {
        return tasaYPlazoRepository.findAll();
    }
    public boolean deleteTasaYPlazoByUserId() {
        String userId;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsImpl) {
            userId = ((UserDetailsImpl) principal).getId();
        } else {
            userId = principal.toString();
        }
        Optional<TasaYPlazo> tasaYPlazo = tasaYPlazoRepository.findByUserId(userId);
        if (tasaYPlazo.isPresent()) {
            tasaYPlazoRepository.deleteByUserId(tasaYPlazo.get().getUserId());
            return true;
        }
        return false;
    }

}