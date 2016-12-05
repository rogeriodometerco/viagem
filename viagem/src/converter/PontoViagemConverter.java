package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import dao.PontoViagemDao;
import modelo.PontoViagem;
import util.Ejb;
import util.JsfUtil;

@FacesConverter("pontoViagemConverter")
public class PontoViagemConverter implements Converter {

	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		PontoViagem result = null;
		// TODO Descobrir o motivo de value == "null".
		if (value.equals("null")) {
			value = null;
		}
		if (value != null && value.trim().length() > 0) {
			try {
				PontoViagemDao pontoViagemDao = Ejb.lookup(PontoViagemDao.class);
				result = pontoViagemDao.recuperar(Long.parseLong(value));
			} catch(NumberFormatException e) {
				JsfUtil.addMsgErro("Erro ao converter identificador de ponto de viagem: " + e.getMessage());
				//throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao converter identificador", "O literal passado como parâmetro é um valor numérico inválido"));
			} catch (Exception e) {
				JsfUtil.addMsgErro("Erro ao recuperar ponto de viagem: " + e.getMessage());
			}
		}
		return result;	
	}

	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return String.valueOf(((PontoViagem)object).getId());
		}
		else {
			return null;
		}
	}   
}     