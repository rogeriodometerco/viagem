package rest;

public class RespostaErro {

	private String mensagem;
	
	public RespostaErro(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getMensagem() {
		return mensagem;
	}
}
