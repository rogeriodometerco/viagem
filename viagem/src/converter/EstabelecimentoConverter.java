package converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import dao.EstabelecimentoDao;
import modelo.Estabelecimento;
import util.Ejb;
import util.JsfUtil;

@FacesConverter("estabelecimentoConverter")
public class EstabelecimentoConverter implements Converter {

	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		Estabelecimento result = null;
		if (value != null && value.trim().length() > 0) {
			try {
				EstabelecimentoDao estabelecimentoDao = Ejb.lookup(EstabelecimentoDao.class);
				result = estabelecimentoDao.recuperar(Long.parseLong(value));
			} catch(NumberFormatException e) {
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao converter identificador de estabelecimento", "O literal passado como par�metro � um valor num�rico inv�lido"));
			} catch (Exception e) {
				JsfUtil.addMsgErro("Erro ao recuperar estabelecimento: " + e.getMessage());
			}
		}
		return result;	
	}

	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return String.valueOf(((Estabelecimento)object).getId());
		}
		else {
			return null;
		}
	}   
}     