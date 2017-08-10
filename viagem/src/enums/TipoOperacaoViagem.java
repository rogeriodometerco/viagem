package enums;

public enum TipoOperacaoViagem {
	COLETA("Carga"),
	ENTREGA("Descarga");
	
	private String descricao;
	
	private TipoOperacaoViagem(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
