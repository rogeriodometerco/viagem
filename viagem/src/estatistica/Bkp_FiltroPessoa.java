package estatistica;

/**
 * Coamo Agroindustrial Cooperativa 
 *
 * @author rdometerco@coamo.com.br 
 * @creation  22/12/2016 10:56:45 
 */
public class Bkp_FiltroPessoa implements Bkp_Filtro<Pessoa> {

	private String nome;
	
	public Bkp_FiltroPessoa(String nome) {
		this.nome = nome;
	}
	
	@Override
	public boolean filtrar(Pessoa objeto) {
		return objeto.getNome().equals(nome);
	}

}
