package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import dao.UfDao;
import modelo.UF;
import util.Ejb;
import util.JsfUtil;

@FacesConverter("ufConverter")
public class UfConverter implements Converter {

	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		UF result = null;
		if (value != null && value.trim().length() > 0) {
			try {
				UfDao ufDao = Ejb.lookup(UfDao.class);
				result = ufDao.recuperar(Long.parseLong(value));
			} catch(NumberFormatException e) {
				JsfUtil.addMsgErro("Erro ao converter identificador de UF: " + e.getMessage());
			} catch (Exception e) {
				JsfUtil.addMsgErro("Erro ao recuperar UF: " + e.getMessage());
			}
		}
		return result;	
	}

	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return String.valueOf(((UF)object).getId());
		}
		else {
			return null;
		}
	}   
}     