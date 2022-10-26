package TransporteVem.cartaovem.service;

import java.nio.charset.Charset;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.apache.commons.codec.binary.Base64;

import TransporteVem.cartaovem.model.UsuarioLogin;
import TransporteVem.cartaovem.model.UsuariosModel;
import TransporteVem.cartaovem.repository.UsuariosRepository;

@Service
public class UsuariosService {
	
	@Autowired
	private UsuariosRepository usuariosRepository;

	public Optional<UsuariosModel> cadastrarUsuario(UsuariosModel usuario) {

		if (usuariosRepository.findByUsuario(usuario.getUsuario()).isPresent())
			return Optional.empty();

		usuario.setSenha(criptografarSenha(usuario.getSenha()));

		return Optional.of(usuariosRepository.save(usuario));

	}
	
	
	
		private String criptografarSenha(String senha) {

			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

			return encoder.encode(senha);

		}

		public Optional<UsuariosModel> atualizarUsuario(UsuariosModel usuario) {

			if (usuariosRepository.findById(usuario.getId()).isPresent()) {

				Optional<UsuariosModel> buscaUsuario = usuariosRepository.findByUsuario(usuario.getUsuario());

				if ((buscaUsuario.isPresent()) && (buscaUsuario.get().getId() != usuario.getId()))
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);

				usuario.setSenha(criptografarSenha(usuario.getSenha()));

				return Optional.ofNullable(usuariosRepository.save(usuario));

			}

			return Optional.empty();

		}

		 public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin) {

		        Optional<UsuariosModel> usuario = usuariosRepository.findByUsuario(usuarioLogin.get().getUsuario());

		        if (usuario.isPresent()) {

		            if (compararSenhas(usuarioLogin.get().getSenha(), usuario.get().getSenha())) {

		                usuarioLogin.get().setId(usuario.get().getId());
		                usuarioLogin.get().setNome(usuario.get().getNome());
		                usuarioLogin.get().setTipo(usuario.get().getTipo());
		                usuarioLogin.get().setToken(gerarBasicToken(usuarioLogin.get().getUsuario(),        usuarioLogin.get().getSenha()));
		                usuarioLogin.get().setSenha(usuario.get().getSenha());

		                return usuarioLogin;

		            }
		        }   

		        return Optional.empty();
		        
		    }
		 
		 private boolean compararSenhas(String senhaDigitada, String senhaBanco) {
		        
		        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		        
		        return encoder.matches(senhaDigitada, senhaBanco);

		    }

		    private String gerarBasicToken(String usuario, String senha) {

		        String token = usuario + ":" + senha;
		        byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
		        return "Basic " + new String(tokenBase64);

		    }


	

}
