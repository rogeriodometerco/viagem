package enums;

public enum StatusViagem {
	PENDENTE("Pendente"), // TODO pensar num termo melhor
	ACEITA("Aceita"),
	INICIADA("Iniciada"),
	CONCLUIDA("Concluída"),
	ABORTADA("Abortada"),
	RECUSADA("Recusada");
	
	private String descricao;
	
	private StatusViagem(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
