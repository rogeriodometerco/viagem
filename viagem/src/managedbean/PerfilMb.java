package managedbean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import enums.Perfil;

@ManagedBean
@RequestScoped
public class PerfilMb {

	public List<Perfil> autocomplete(String query) {
		List<Perfil> perfis = new ArrayList<Perfil>();
		for (Perfil p: Perfil.values()) {
			if (p.getDescricao().toUpperCase().startsWith(query.toUpperCase())) {
				perfis.add(p);
			}
		}
		return perfis;
	}
}
