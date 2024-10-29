package pe.edu.upc.spring.mongodb.wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.spring.mongodb.security.payload.response.MessageResponse;
import pe.edu.upc.spring.mongodb.wallet.DTO.request.FacturaDTO;
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

    public List<FacturaDTO> getAllFacturas() {
        List<Factura> facturas = facturaRepository.findAll();
        return facturas.stream()
                .map(Factura::toDTO)
                .collect(Collectors.toList());
    }
    public Optional<FacturaDTO> getFacturaById(String id) {
        Optional<Factura> letras = facturaRepository.findById(id);
        return letras.map(Factura::toDTO);
    }

    public MessageResponse createFactura(FacturaDTO facturaDTO) {
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
            return new MessageResponse("Error creating Factura: " + e.getMessage());
        }

    }

    public Optional<Factura> updateFactura(String id, Factura facturaDetails) {
        return facturaRepository.findById(id).map(factura -> {
            facturaDetails.setId(id);
            return facturaRepository.save(facturaDetails);
        });
    }

    public MessageResponse deleteFactura(String id) {
        if (facturaRepository.existsById(id)) {
            facturaRepository.deleteById(id);
            return new MessageResponse("Factura deleted successfully");
        }
        return new MessageResponse("Error deleting Factura: Factura not found");
    }
}
