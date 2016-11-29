package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import dao.MunicipioDao;
import modelo.Municipio;
import util.Ejb;
import util.JsfUtil;

@FacesConverter("municipioConverter")
public class MunicipioConverter implements Converter {

	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		Municipio result = null;
		if (value != null && value.trim().length() > 0) {
			try {
				MunicipioDao municipioDao = Ejb.lookup(MunicipioDao.class);
				result = municipioDao.recuperar(Long.parseLong(value));
			} catch(NumberFormatException e) {
				JsfUtil.addMsgErro("Erro ao converter identificador de município: " + e.getMessage());
			} catch (Exception e) {
				JsfUtil.addMsgErro("Erro ao recuperar município: " + e.getMessage());
			}
		}
		return result;	
	}

	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return String.valueOf(((Municipio)object).getId());
		}
		else {
			return null;
		}
	}   
}     