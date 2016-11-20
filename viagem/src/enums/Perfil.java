package enums;

public enum Perfil {
	ADMINISTRADOR_SISTEMA("Administrador do sistema"),
	MOTORISTA("Motorista"), 
	TRANSPORTADOR("Transportador"), 
	TOMADOR("Tomador de serviço"), 
	ESTABELECIMENTO("Estabelecimento");
	
	private String descricao;
	
	private Perfil(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
