package estatistica;

import java.util.ArrayList;
import java.util.List;

/**
 * Coamo Agroindustrial Cooperativa 
 *
 * @author rdometerco@coamo.com.br 
 * @creation  22/12/2016 10:50:20 
 */
public interface Filtro<T> {

	
	public boolean filtrar(T objeto);
	
}
