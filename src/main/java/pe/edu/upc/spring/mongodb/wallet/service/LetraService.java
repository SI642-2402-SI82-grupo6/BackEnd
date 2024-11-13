package pe.edu.upc.spring.mongodb.wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pe.edu.upc.spring.mongodb.security.payload.response.MessageResponse;
import pe.edu.upc.spring.mongodb.security.security.services.UserDetailsImpl;
import pe.edu.upc.spring.mongodb.wallet.DTO.request.LetraDTORequest;
import pe.edu.upc.spring.mongodb.wallet.DTO.response.LetraDTO;
import pe.edu.upc.spring.mongodb.wallet.exception.ResourceNotFoundException;
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
    private ResultadosConsultaService resultadosConsultaService;

    @Autowired
    private DocumentosCreadosRepository documentosCreadosRepository;

    public List<LetraDTO> getAllLetras() {
        String userId;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsImpl) {
            userId = ((UserDetailsImpl) principal).getId();
        } else {
            userId = principal.toString();
        }
        List<Letra> letras= letraRepository.findByUserId(userId);
        return letras.stream()
                .map(Letra::toDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    public Optional<LetraDTO> getLetraById(String id) {
        Optional<Letra> letras =letraRepository.findById(id);
        return letras.map(Letra::toDTO);
    }
    public MessageResponse createLetra(LetraDTORequest letraDTO) {
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
            throw new ResourceNotFoundException("Error creating Letra: " + e.getMessage());
        }
    }
    public Optional<Letra> updateLetra(String id, Letra letraDetails) {
        return letraRepository.findById(id).map(letra -> {
            letraDetails.setId(id);
            resultadosConsultaService.consultarResultadoPorDocumentoId(id);
            return letraRepository.save(letraDetails);
        });
    }

    public MessageResponse deleteLetra(String id) {

        if (letraRepository.existsById(id)) {
            letraRepository.deleteById(id);
            documentosCreadosRepository.deleteByDocumentoId(id);
            resultadosConsultaService.deleteResultadosConsultaPorDocumentoId(id);
            return new MessageResponse("Letra deleted successfully");
        }
        throw new ResourceNotFoundException("Error deleting Letra: Letra not found");
    }
}
