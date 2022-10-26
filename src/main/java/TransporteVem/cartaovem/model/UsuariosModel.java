package TransporteVem.cartaovem.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;



@Entity
@Table(name= "tb_usuarios")
public class UsuariosModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	
	@NotNull(message = "O atributo nome é obrigatório!") 
	private String nome;
	
	@Schema(example = "email@email.com.br")
	@NotNull(message = "O atributo e-mail é obrigatório!")
	@Email(message = "O atributo usuário deve ser um email válido!")
	private String usuario;
	
	@NotBlank(message = "O atributo senha é obrigatório!")
	@Size(min = 8, message = "A Senha deve ter no mínimo 8 caracteres")
	private String senha;
	
	private String tipo;
	
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("usuario")
	private List<CartaoModel> cartao;
	
	
	public UsuariosModel(Long id, String nome, String usuario, String senha, String tipo) {
		this.id = id;
		this.nome = nome;
		this.usuario = usuario;
		this.senha = senha;
		this.tipo = tipo;
	}
	
	
	public UsuariosModel() { }
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public List<CartaoModel> getCartao() {
		return cartao;
	}


	public void setCartao(List<CartaoModel> cartao) {
		this.cartao = cartao;
	}
	
	

}
