package estatistica;

import modelo.Estabelecimento;

public class Bkp_FiltroEstabelecimento implements Bkp_Filtro<Estabelecimento> {

	private Estabelecimento estabelecimento;
	
	public Bkp_FiltroEstabelecimento(Estabelecimento e) {
		this.estabelecimento = e;
	}
	
	@Override
	public boolean filtrar(Estabelecimento objeto) {
		return estabelecimento.getId().equals(objeto.getId());
	}

}
