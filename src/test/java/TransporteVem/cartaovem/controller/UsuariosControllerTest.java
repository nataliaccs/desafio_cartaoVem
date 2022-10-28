package TransporteVem.cartaovem.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import TransporteVem.cartaovem.model.UsuariosModel;
import TransporteVem.cartaovem.repository.UsuariosRepository;
import TransporteVem.cartaovem.service.UsuariosService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuariosControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private UsuariosRepository usuarioRepository;

	@Autowired
	private UsuariosService usuarioService;

	@BeforeAll
	void start() {

		usuarioRepository.deleteAll();

		usuarioService.cadastrarUsuario(new UsuariosModel(0L, "Root", "root@root.com", "rootroot", "adm"));

	}

	@Test
	@DisplayName("Cadastrar usuário")
	public void cadastrarUsuario() {

		HttpEntity<UsuariosModel> corpoRequisicao = new HttpEntity<UsuariosModel>(
				new UsuariosModel(0L, "Rafael Costa", "rafael@email.com.br", "12345678", "adm"));

		ResponseEntity<UsuariosModel> corpoResposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST,
				corpoRequisicao, UsuariosModel.class);

		assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());
		assertEquals(corpoRequisicao.getBody().getNome(), corpoResposta.getBody().getNome());
		assertEquals(corpoRequisicao.getBody().getUsuario(), corpoResposta.getBody().getUsuario());

	}

	@Test
	@DisplayName("Não pode repetir usuário")
	public void semRepetirUsuario() {

		usuarioService
				.cadastrarUsuario(new UsuariosModel(0L, "Natália Costa", "natalia@email.com.br", "12345678", "adm"));

		HttpEntity<UsuariosModel> corpoRequisicao = new HttpEntity<UsuariosModel>(
				new UsuariosModel(0L, "Natália Costa", "natalia@email.com.br", "12345678", "adm"));

		ResponseEntity<UsuariosModel> corpoResposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST,
				corpoRequisicao, UsuariosModel.class);

		assertEquals(HttpStatus.BAD_REQUEST, corpoResposta.getStatusCode());
	}

	@Test
	@DisplayName("Listar todos os usuários")
	public void mostrarTodosUsuarios() {

		usuarioService
				.cadastrarUsuario(new UsuariosModel(0L, "Laura Cardoso", "laura@email.com.br", "sabrina123", "adm"));

		usuarioService
				.cadastrarUsuario(new UsuariosModel(0L, "Tony Ramos", "tony@email.com.br", "ricardo123", "normal"));

		ResponseEntity<String> resposta = testRestTemplate.withBasicAuth("root@root.com", "rootroot")
				.exchange("/usuarios/all", HttpMethod.GET, null, String.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());

	}
	
	@Test
	@DisplayName("Atualizar um usuário")
	public void atualizarUsuario() {

		Optional<UsuariosModel> usuarioCadastrado = usuarioService.cadastrarUsuario(new UsuariosModel(0L, 
			"Juliana Farias", "juliana@email.com.br", "juliana123", "normal"));

		UsuariosModel usuarioUpdate = new UsuariosModel(usuarioCadastrado.get().getId(), 
			"Juliana Farias Ramos", "juliana_ramos@email.com.br", "juliana123" , "normal");
		
		HttpEntity<UsuariosModel> corpoRequisicao = new HttpEntity<UsuariosModel>(usuarioUpdate);

		ResponseEntity<UsuariosModel> corpoResposta = testRestTemplate
			.withBasicAuth("root@root.com", "rootroot")
			.exchange("/usuarios/atualizar", HttpMethod.PUT, corpoRequisicao, UsuariosModel.class);

		assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
		assertEquals(corpoRequisicao.getBody().getNome(), corpoResposta.getBody().getNome());
		assertEquals(corpoRequisicao.getBody().getUsuario(), corpoResposta.getBody().getUsuario());
	}

}
