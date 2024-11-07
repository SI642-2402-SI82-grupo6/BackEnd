package pe.edu.upc.spring.mongodb.wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.spring.mongodb.wallet.DTO.requestCartera.CarteraDtoRequest;
import pe.edu.upc.spring.mongodb.wallet.DTO.resource.CarteraResource;

import pe.edu.upc.spring.mongodb.wallet.exception.ResourceNotFoundException;
import pe.edu.upc.spring.mongodb.wallet.model.Cartera;
import pe.edu.upc.spring.mongodb.wallet.model.ResultadosConsulta;
import pe.edu.upc.spring.mongodb.wallet.repository.CarteraRepository;
import pe.edu.upc.spring.mongodb.wallet.repository.ResultadosConsultaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CarteraService {
    @Autowired
    private CarteraRepository carteraRepository;
    @Autowired
    private ResultadosConsultaRepository resultadosConsultaRepository;
    public CarteraResource createCartera(CarteraDtoRequest carteraDtoRequest){
        Cartera cartera = new Cartera();
        cartera.setName(carteraDtoRequest.getName());
        cartera.setTypeMoney(carteraDtoRequest.getTypeMoney());
        carteraRepository.save(cartera);
        CarteraResource carteraResource = new CarteraResource();
        carteraResource.setName(cartera.getName());
        carteraResource.setTypeMoney(cartera.getTypeMoney());
        carteraResource.setCreationDate(cartera.getCreationDate());
        carteraResource.setValorTotalRecibir(cartera.getValorTotalRecibir());
        carteraResource.setTcea(cartera.getTcea());
        return carteraResource;
    }
    public CarteraResource agregarDocumento(String carteraId, String documentoId) {
        // Busca la cartera por ID
        Optional<Cartera> optionalCartera = carteraRepository.findById(carteraId);
        if (!optionalCartera.isPresent()) {
            throw new ResourceNotFoundException("Cartera con ID " + carteraId + " no encontrada.");
        }

        Cartera cartera = optionalCartera.get();
        cartera.getDocumentosCreadosIds().add(documentoId);

        // Calcula la TCEA de la cartera actualizada
        calcularTCEA(cartera);

        // Busca el documento en resultadosConsultaRepository
        Optional<ResultadosConsulta> optionalDocumento = resultadosConsultaRepository.findByDocumentoId(documentoId);
        if (optionalDocumento.isPresent()) {
            Double valorRecibir = optionalDocumento.get().getValorRecibir();
            cartera.CalcularValorTotalRecibir(valorRecibir);
        } else {
            throw new ResourceNotFoundException("Documento con ID " + documentoId + " no encontrado.");
        }

        // Guarda la cartera actualizada en el repositorio
        Cartera response = carteraRepository.save(cartera);

        // Retorna la respuesta en forma de CarteraResource
        return response.toResource();
    }

    public void calcularTCEA(Cartera cartera) {
        double sumaTCEA = 0.0;
        double sumaMontos = 0.0;

        for (String documentoId : cartera.getDocumentosCreadosIds()) {
            // Buscar el documento en el repositorio
            Optional<ResultadosConsulta> optionalDocumento = resultadosConsultaRepository.findById(documentoId);
            if (optionalDocumento.isPresent()) {
                ResultadosConsulta documento = optionalDocumento.get();
                sumaTCEA += documento.getTceaPorcentaje() * documento.getValorRecibir();
                sumaMontos += documento.getValorRecibir();
            }
        }

        // Calcula y establece la TCEA en la cartera
        cartera.setTcea(sumaMontos != 0 ? sumaTCEA / sumaMontos : 0);
    }

    public Cartera obtenerCartera(String carteraId) {
        Optional<Cartera> cartera = carteraRepository.findById(carteraId);
        if (cartera.isPresent()) {
            return cartera.get();
        } else {
            throw new ResourceNotFoundException("Cartera con ID " + carteraId + " no encontrada.");
        }
    }
    public List<Cartera> obtenerCarteras() {
        return carteraRepository.findAll();
    }






}
