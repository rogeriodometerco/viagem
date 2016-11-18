package converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import dao.ContaDao;
import modelo.Conta;
import util.Ejb;
import util.JsfUtil;

@FacesConverter("contaConverter")
public class ContaConverter implements Converter {

	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		Conta result = null;
		if (value != null && value.trim().length() > 0) {
			try {
				ContaDao contaDao = Ejb.lookup(ContaDao.class);
				result = contaDao.recuperar(Long.parseLong(value));
			} catch(NumberFormatException e) {
				JsfUtil.addMsgErro("Erro ao converter identificador de conta: " + e.getMessage());
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao converter identificador", "O literal passado como parâmetro não é um valor numérico inválido"));
			} catch (Exception e) {
				JsfUtil.addMsgErro("Erro ao recuperar conta: " + e.getMessage());
			}
		}
		return result;	
	}

	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return String.valueOf(((Conta)object).getId());
		}
		else {
			return null;
		}
	}   
}     