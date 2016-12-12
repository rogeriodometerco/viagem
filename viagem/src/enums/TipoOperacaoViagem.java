package enums;

public enum TipoOperacaoViagem {
	COLETA("Coleta"),
	ENTREGA("Entrega");
	
	private String descricao;
	
	private TipoOperacaoViagem(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
