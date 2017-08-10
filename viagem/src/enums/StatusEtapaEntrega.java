package enums;

public enum StatusEtapaEntrega {
	PENDENTE("Pendente"),
	CARREGADO("Carregado"),
	TRANSITO("Trânsito"),
	CONCLUIDA("Concluída"),
	ABORTADA("Abortada");
	
	private String descricao;
	
	private StatusEtapaEntrega(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}
