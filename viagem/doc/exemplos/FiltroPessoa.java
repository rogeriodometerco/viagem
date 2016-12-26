package estatistica;

/**
 * Coamo Agroindustrial Cooperativa 
 *
 * @author rdometerco@coamo.com.br 
 * @creation  22/12/2016 10:56:45 
 */
public class FiltroPessoa implements Filtro<Pessoa> {

	private String nome;
	
	public FiltroPessoa(String nome) {
		this.nome = nome;
	}
	
	@Override
	public boolean filtrar(Pessoa objeto) {
		return objeto.getNome().equals(nome);
	}

}
