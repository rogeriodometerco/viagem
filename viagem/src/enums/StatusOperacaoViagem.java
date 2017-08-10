package enums;

public enum StatusOperacaoViagem {
	PENDENTE("Programado"), 
	REALIZADA("Realizado"), 
	ABORTADA("Abortado");
	
	private String descricao;
	
	private StatusOperacaoViagem(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
