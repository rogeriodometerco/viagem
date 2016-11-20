package modelo;

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
	
}
