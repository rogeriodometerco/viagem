package enums;

public enum StatusOperacaoViagem {
	PENDENTE("Programada"), 
	REALIZADA("Realizada"), 
	ABORTADA("Abortada");
	
	private String descricao;
	
	private StatusOperacaoViagem(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
