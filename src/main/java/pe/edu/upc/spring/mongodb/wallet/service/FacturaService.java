package pe.edu.upc.spring.mongodb.wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.spring.mongodb.wallet.DTO.request.FacturaDTO;
import pe.edu.upc.spring.mongodb.wallet.model.Factura;
import pe.edu.upc.spring.mongodb.wallet.repository.FacturaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    public List<Factura> getAllFacturas() {
        return facturaRepository.findAll();
    }

    public Optional<Factura> getFacturaById(String id) {
        return facturaRepository.findById(id);
    }

    public Factura createFactura(FacturaDTO facturaDTO) {
        Factura factura = new Factura(facturaDTO);
        return facturaRepository.save(factura);
    }

    public Optional<Factura> updateFactura(String id, Factura facturaDetails) {
        return facturaRepository.findById(id).map(factura -> {
            facturaDetails.setId(id);
            return facturaRepository.save(facturaDetails);
        });
    }

    public boolean deleteFactura(String id) {
        if (facturaRepository.existsById(id)) {
            facturaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}