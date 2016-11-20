package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import enums.Perfil;

@FacesConverter("perfilConverter")
public class PerfilConverter implements Converter {

	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		Perfil result = null;
		if (value != null && value.trim().length() > 0) {
			result = Perfil.valueOf(value);
		}
		return result;	
	}

	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return String.valueOf(((Perfil)object).name());
		}
		else {
			return null;
		}
	}   
}     