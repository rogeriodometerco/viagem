package modelo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Conta {
	
	@Id
	@GeneratedValue
	private Long id;

	private String nome;
	
	private Boolean ativa;
	
	@OneToMany(mappedBy="conta", fetch=FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval=true)
	private Set<PerfilConta> perfis;

	@OneToMany(mappedBy="conta", fetch=FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval=true)
	private Set<AdminConta> administradores;

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

	public Set<PerfilConta> getPerfis() {
		return perfis;
	}

	public void setPerfis(Set<PerfilConta> perfis) {
		this.perfis = perfis;
	}

	public Set<AdminConta> getAdministradores() {
		return administradores;
	}

	public void setAdministradores(Set<AdminConta> administradores) {
		this.administradores = administradores;
	}

	public Boolean getAtiva() {
		return ativa;
	}

	public void setAtiva(Boolean ativa) {
		this.ativa = ativa;
	}

	public void adicionarAdministrador(Usuario usuario) {
		if (this.administradores == null) {
			this.administradores = new HashSet<AdminConta>();
		}
		boolean existe = false;
		for (AdminConta admin: administradores) {
			if (admin.getUsuario().getId().equals(usuario.getId())) {
				existe = true;
				break;
			}
		}
		if (!existe) {
			AdminConta novo = new AdminConta();
			novo.setConta(this);
			novo.setUsuario(usuario);
			this.administradores.add(novo);
		}
	}

	public void removerAdministrador(AdminConta admin) {
		for (AdminConta atual: administradores) {
			if (atual.getId().equals(admin.getId())) {
				atual.setConta(null);
				administradores.remove(atual);
				break;
			}
		}
	}
	
}
