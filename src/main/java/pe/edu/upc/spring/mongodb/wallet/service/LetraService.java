package pe.edu.upc.spring.mongodb.wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.spring.mongodb.security.payload.response.MessageResponse;
import pe.edu.upc.spring.mongodb.wallet.DTO.request.LetraDTO;
import pe.edu.upc.spring.mongodb.wallet.model.Letra;
import pe.edu.upc.spring.mongodb.wallet.model.DocumentosCreados;
import pe.edu.upc.spring.mongodb.wallet.object.TipoDocumento;
import pe.edu.upc.spring.mongodb.wallet.repository.LetraRepository;
import pe.edu.upc.spring.mongodb.wallet.repository.DocumentosCreadosRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LetraService {

    @Autowired
    private LetraRepository letraRepository;

    @Autowired
    private DocumentosCreadosRepository documentosCreadosRepository;

    public List<LetraDTO> getAllLetras() {
        List<Letra> letras= letraRepository.findAll();
        return letras.stream()
                .map(Letra::toDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    public Optional<Letra> getLetraById(String id) {
        return letraRepository.findById(id);
    }
    public MessageResponse createLetra(LetraDTO letraDTO) {
        try {

            Letra letra = new Letra(letraDTO);
            Letra savedLetra = letraRepository.save(letra);


            DocumentosCreados documentoCreado = new DocumentosCreados(
                    savedLetra.getUserId(),
                    savedLetra.getId(),
                    TipoDocumento.LETRA,
                    LocalDate.now()
            );
            documentosCreadosRepository.save(documentoCreado);

            return new MessageResponse("Letra created successfully");
        } catch (Exception e) {
            return new MessageResponse("Error creating Letra: " + e.getMessage());
        }
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
