package enums;

public enum StatusEntrega {
	PENDENTE("Programado"),
	CARREGADO("Carregado"),
	A_CAMINHO("Trânsito"),
	REALIZADA("Entregue"),
	ABORTADA("Abortado"),
	CANCELADA("Cancelado");
	
	private String descricao;
	
	private StatusEntrega(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}
