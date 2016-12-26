package estatistica;

import modelo.Conta;

public class CrivoContaIgual implements Crivo<Conta> {

	private Conta conta;
	
	public CrivoContaIgual(Conta e) {
		this.conta = e;
	}

	@Override
	public boolean passa(Conta objeto) {
		return conta.getId().equals(objeto.getId());
	}}
