package pe.edu.upc.spring.mongodb.wallet.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pe.edu.upc.spring.mongodb.wallet.model.CostesGastos;
import pe.edu.upc.spring.mongodb.wallet.object.MotivoGasto;
import pe.edu.upc.spring.mongodb.wallet.object.TipoGasto;
import pe.edu.upc.spring.mongodb.wallet.object.ValorExpresado;

import java.util.List;

public interface CostesGastosRepository extends MongoRepository<CostesGastos, String> {
    List<CostesGastos> findByUserId(String userId);
    List<CostesGastos> findByUserIdAndTipoGasto(String userId, TipoGasto tipoGasto);
    List<CostesGastos> findByUserIdAndMotivoGasto(String userId, MotivoGasto motivoGasto);
    List<CostesGastos> findByUserIdAndTipoGastoAndMotivoGasto(String userId, TipoGasto tipoGasto, MotivoGasto motivoGasto);
    List<CostesGastos> findByUserIdAndValorExpresado(String userId, ValorExpresado valorExpresado);
    List<CostesGastos> findByUserIdAndTipoGastoAndValorExpresado(String userId, TipoGasto tipoGasto, ValorExpresado valorExpresado);
    List<CostesGastos> findByUserIdAndMotivoGastoAndValorExpresado(String userId, MotivoGasto motivoGasto, ValorExpresado valorExpresado);
    List<CostesGastos> findByUserIdAndTipoGastoAndMotivoGastoAndValorExpresado(String userId, TipoGasto tipoGasto, MotivoGasto motivoGasto, ValorExpresado valorExpresado);
}
