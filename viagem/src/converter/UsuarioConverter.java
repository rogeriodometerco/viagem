package converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import dao.UsuarioDao;
import modelo.Usuario;
import util.Ejb;
import util.JsfUtil;

@FacesConverter("usuarioConverter")
public class UsuarioConverter implements Converter {

	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		Usuario result = null;
		if (value != null && value.trim().length() > 0) {
			try {
				UsuarioDao usuarioDao = Ejb.lookup(UsuarioDao.class);
				result = usuarioDao.recuperar(Long.parseLong(value));
			} catch(NumberFormatException e) {
				JsfUtil.addMsgErro("Erro ao converter identificador de usuário: " + e.getMessage());
				//throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao converter identificador", "O literal passado como parâmetro não é um valor numérico inválido"));
			} catch (Exception e) {
				JsfUtil.addMsgErro("Erro ao recuperar usuário: " + e.getMessage());
			}
		}
		return result;	
	}

	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return String.valueOf(((Usuario)object).getId());
		}
		else {
			return null;
		}
	}   
}     