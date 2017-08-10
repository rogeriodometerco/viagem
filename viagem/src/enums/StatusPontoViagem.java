package enums;

public enum StatusPontoViagem {
	PENDENTE("Programado"),
	NO_LOCAL("No local"),
	CONCLUIDO("Concluído");

	private String descricao;

	private StatusPontoViagem(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}