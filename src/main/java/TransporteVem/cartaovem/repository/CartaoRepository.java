package TransporteVem.cartaovem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import TransporteVem.cartaovem.model.CartaoModel;


@Repository
public interface CartaoRepository extends JpaRepository <CartaoModel, Long> {
	
	public Optional<CartaoModel> findById(Long id);

}
