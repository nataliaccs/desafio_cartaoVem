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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import TransporteVem.cartaovem.model.CartaoModel;
import TransporteVem.cartaovem.repository.CartaoRepository;

@RestController
@RequestMapping("/cartao")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CartaoController {
	
	@Autowired
	private CartaoRepository cartaoRepository;
	
	@GetMapping
	public ResponseEntity<List<CartaoModel>> GetAll() {
		return ResponseEntity.ok(cartaoRepository.findAll());
	}
	
	@PostMapping
	public ResponseEntity<CartaoModel> postCartao (@Valid @RequestBody CartaoModel cartaoModel){
		return ResponseEntity.status(HttpStatus.CREATED).body(cartaoRepository.save(cartaoModel));
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void deleteCartao(@PathVariable Long id) {
		Optional<CartaoModel> cartaOptional = cartaoRepository.findById(id);
		
		if(cartaOptional.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		cartaoRepository.deleteById(id);
	}	
	
	

}
