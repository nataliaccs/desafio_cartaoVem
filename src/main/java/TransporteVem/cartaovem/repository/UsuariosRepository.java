package TransporteVem.cartaovem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import TransporteVem.cartaovem.model.UsuariosModel;


@Repository
public interface UsuariosRepository extends JpaRepository<UsuariosModel, Long> {
	
public Optional<UsuariosModel> findByUsuario(String usuario);
	
	public List <UsuariosModel> findAllByNomeContainingIgnoreCase(@Param("nome")String nome);
}
