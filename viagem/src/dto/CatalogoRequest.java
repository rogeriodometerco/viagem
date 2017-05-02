package dto;

import java.io.Serializable;

public class CatalogoRequest implements Serializable {


	private static final long serialVersionUID = 1L;

	private Filtro filtro;
	private Integer pagina;
	private Integer maxResults;

	public CatalogoRequest () {
		filtro = new Filtro();
		pagina = 1;
		maxResults = 20;
	}
	

}
