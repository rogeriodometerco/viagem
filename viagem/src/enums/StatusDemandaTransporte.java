package enums;

public enum StatusDemandaTransporte {
	PENDENTE("Pendente"),
	FINALIZADA("Finalizada");
	
	private String descricao;
	
	private StatusDemandaTransporte(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
