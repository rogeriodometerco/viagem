package estatistica;

import modelo.Estabelecimento;

public class CrivoEstabelecimentoIgual implements Crivo<Estabelecimento> {

	private Estabelecimento estabelecimento;
	
	public CrivoEstabelecimentoIgual(Estabelecimento e) {
		this.estabelecimento = e;
	}

	@Override
	public boolean passa(Estabelecimento objeto) {
		return estabelecimento.getId().equals(objeto.getId());
	}}
