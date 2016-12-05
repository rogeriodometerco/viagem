package converter;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import dao.VeiculoDao;
import modelo.Veiculo;
import util.Ejb;
import util.JsfUtil;

@FacesConverter("veiculoConverter")
public class VeiculoConverter implements Converter {

	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		Veiculo result = null;
		if (value != null && value.trim().length() > 0) {
			try {
				VeiculoDao veiculoDao = Ejb.lookup(VeiculoDao.class);
				result = veiculoDao.recuperar(Long.parseLong(value));
			} catch(NumberFormatException e) {
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao converter identificador de veículo", "O literal passado como parâmetro é um valor numérico inválido"));
			} catch (Exception e) {
				JsfUtil.addMsgErro("Erro ao recuperar veículo: " + e.getMessage());
			}
		}
		return result;	
	}

	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return String.valueOf(((Veiculo)object).getId());
		}
		else {
			return null;
		}
	}   
}     