package TransporteVem.cartaovem.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_cartao")
public class CartaoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "O atributo nome é obrigatório!")
	private String nome;

	@NotNull(message = "O atributo numeroCartao é obrigatório!")
	private String numeroCartao;

	private boolean status;

	@NotNull(message = "O atributo tipoCartao é obrigatório!")
	private String tipoCartao;

	@ManyToOne
	@JsonIgnoreProperties("cartao")
	private UsuariosModel usuario;

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

	public String getNumeroCartao() {
		return numeroCartao;
	}

	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getTipoCartao() {
		return tipoCartao;
	}

	public void setTipoCartao(String tipoCartao) {
		this.tipoCartao = tipoCartao;
	}

	public UsuariosModel getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuariosModel usuario) {
		this.usuario = usuario;
	}
	
	

}
