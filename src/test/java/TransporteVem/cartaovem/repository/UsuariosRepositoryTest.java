package TransporteVem.cartaovem.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import TransporteVem.cartaovem.model.UsuariosModel;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuariosRepositoryTest {
	
	@Autowired
	private UsuariosRepository usuariosRepository;
	
	@BeforeAll
	void start(){

		usuariosRepository.deleteAll();

		usuariosRepository.save(new UsuariosModel(0L, "Rafael Costa", "rafael@email.com.br", "12345678", "adm"));
	
		usuariosRepository.save(new UsuariosModel(0L, "Natália Costa", "natalia@email.com.br", "12345678", "normal"));
		
		usuariosRepository.save(new UsuariosModel(0L, "Diego Leão", "diego@email.com.br", "12345678", "normal"));

        usuariosRepository.save(new UsuariosModel(0L, "Ana Clara", "aninha@email.com.br", "12345678", "normal"));

	}
	
	@Test
	@DisplayName("Retorna um usuário")
	public void retornaUmUsuario() {

		Optional<UsuariosModel> usuario = usuariosRepository.findByUsuario("rafael@email.com.br");
		
		assertTrue(usuario.get().getUsuario().equals("rafael@email.com.br"));
		
	}
	
	
	@Test
	@DisplayName("Retorna dois usuarios")
	public void retornaDoisUsuarios() {
		
		List<UsuariosModel> listaDeUsuarios = usuariosRepository.findAllByNomeContainingIgnoreCase("Costa");

		assertEquals(2, listaDeUsuarios.size());
		
		assertTrue(listaDeUsuarios.get(0).getNome().equals("Rafael Costa"));
		assertTrue(listaDeUsuarios.get(1).getNome().equals("Natália Costa"));
	}
	
	@AfterAll
	public void end() {
		usuariosRepository.deleteAll();
	}
	
}
