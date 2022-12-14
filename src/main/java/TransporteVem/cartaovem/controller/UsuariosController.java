package TransporteVem.cartaovem.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


import TransporteVem.cartaovem.model.UsuarioLogin;
import TransporteVem.cartaovem.model.UsuariosModel;
import TransporteVem.cartaovem.repository.UsuariosRepository;
import TransporteVem.cartaovem.service.UsuariosService;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuariosController {
	
	@Autowired
	private UsuariosRepository usuariosRepository;
	
	@Autowired
	private UsuariosService usuariosService;
	
	
	@GetMapping("/all")
	public ResponseEntity <List<UsuariosModel>> getAll(){
		return ResponseEntity.ok(usuariosRepository.findAll());
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<UsuariosModel> getById(@PathVariable Long id) {
		return usuariosRepository.findById(id)
			.map(resposta -> ResponseEntity.ok(resposta))
			.orElse(ResponseEntity.notFound().build());
	}
	
	
	@GetMapping("/name/{nome}")
	public ResponseEntity<List<UsuariosModel>> getByNome(@PathVariable String nome){
		return ResponseEntity.ok(usuariosRepository.findAllByNomeContainingIgnoreCase(nome));
	}
	
	
	@PostMapping("/cadastrar")
	public ResponseEntity<UsuariosModel> postUsuario(@Valid @RequestBody UsuariosModel usuario) {

		return usuariosService.cadastrarUsuario(usuario)
			.map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(resposta))
			.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());

	}
	
	
	@PostMapping("/logar")
	public ResponseEntity<UsuarioLogin> login(@RequestBody Optional<UsuarioLogin> usuarioLogin) {
		return usuariosService.autenticarUsuario(usuarioLogin)
			.map(resposta -> ResponseEntity.ok(resposta))
			.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
	
	
	@PutMapping("/atualizar")
	public ResponseEntity<UsuariosModel> putUsuario(@Valid @RequestBody UsuariosModel usuario) {
		return usuariosService.atualizarUsuario(usuario)
			.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(resposta))
			.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void deleteUsuario(@PathVariable Long id) {
		Optional<UsuariosModel> usuario = usuariosRepository.findById(id);
		
		if(usuario.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		usuariosRepository.deleteById(id);
	}	
	

}
