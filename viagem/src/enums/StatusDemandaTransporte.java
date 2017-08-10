package enums;

public enum StatusDemandaTransporte {
	PENDENTE("Pendente"),
	FINALIZADA("Finalizado");
	
	private String descricao;
	
	private StatusDemandaTransporte(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
