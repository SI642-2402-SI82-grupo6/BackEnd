package pe.edu.upc.spring.mongodb.wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pe.edu.upc.spring.mongodb.security.payload.response.MessageResponse;
import pe.edu.upc.spring.mongodb.security.security.services.UserDetailsImpl;
import pe.edu.upc.spring.mongodb.wallet.DTO.request.FacturaDTORequest;
import pe.edu.upc.spring.mongodb.wallet.DTO.response.FacturaDTO;
import pe.edu.upc.spring.mongodb.wallet.exception.ResourceNotFoundException;
import pe.edu.upc.spring.mongodb.wallet.model.Factura;
import pe.edu.upc.spring.mongodb.wallet.model.DocumentosCreados;
import pe.edu.upc.spring.mongodb.wallet.object.TipoDocumento;
import pe.edu.upc.spring.mongodb.wallet.repository.FacturaRepository;
import pe.edu.upc.spring.mongodb.wallet.repository.DocumentosCreadosRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private DocumentosCreadosRepository documentosCreadosRepository;
    @Autowired
    private ResultadosConsultaService resultadosConsultaService;
    public List<FacturaDTO> getAllFacturas() {
        String userId;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsImpl) {
            userId = ((UserDetailsImpl) principal).getId();
        } else {
            userId = principal.toString();
        }
        List<Factura> facturas = facturaRepository.findByUserId(userId);
        return facturas.stream()
                .map(Factura::toDTO)
                .collect(Collectors.toList());
    }
    public Optional<FacturaDTO> getFacturaById(String id) {
        Optional<Factura> letras = facturaRepository.findById(id);
        return letras.map(Factura::toDTO);
    }

    public MessageResponse createFactura(FacturaDTORequest facturaDTO) {
        try {
            // Verificar si ya existe una factura con el mismo userId
            Factura factura = new Factura(facturaDTO);
            Factura savedFactura = facturaRepository.save(factura);
            // Guardar el ID de la factura en DocumentosCreados
            DocumentosCreados documentoCreado = new DocumentosCreados(
                    savedFactura.getUserId(),
                    savedFactura.getId(),
                    TipoDocumento.FACTURA,
                    LocalDate.now()
            );
            documentosCreadosRepository.save(documentoCreado);

            return new MessageResponse("Factura created successfully");

        } catch (Exception e) {
            throw new ResourceNotFoundException("Error creating Factura: " + e.getMessage());
        }

    }

    public Optional<Factura> updateFactura(String id, Factura facturaDetails) {
        return facturaRepository.findById(id).map(factura -> {
            facturaDetails.setId(id);
            resultadosConsultaService.consultarResultadoPorDocumentoId(id);
            return facturaRepository.save(facturaDetails);
        });
    }

    public MessageResponse deleteFactura(String id) {
        if (facturaRepository.existsById(id)) {
            facturaRepository.deleteById(id);
            documentosCreadosRepository.deleteByDocumentoId(id);
            resultadosConsultaService.deleteResultadosConsultaPorDocumentoId(id);
            return new MessageResponse("Factura deleted successfully");
        }
        throw new ResourceNotFoundException("Error deleting Factura: Factura not found");
    }
}
