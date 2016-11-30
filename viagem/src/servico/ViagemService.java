package servico;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.ViagemDao;
import exception.AppException;
import modelo.Viagem;

@Stateless
public class ViagemService {

	@EJB
	private ViagemDao viagemDao;
	
	public Viagem criar(Viagem viagem) throws AppException {
		Viagem result = null;
		try {
			List<String> erros = validarViagem(viagem);
			if (erros.size() > 0) {
				throw new AppException(erros.toString());
			}
			result = viagemDao.salvar(viagem);
		} catch (Exception e) {
			throw new AppException(e);
		}
		return result;
	}

	private List<String> validarViagem(Viagem viagem) throws Exception {
		List<String> erros = new ArrayList<String>();
		
		// TODO
		
		/*
		if (viagem.getIdentificacao() == null || viagem.getIdentificacao().trim().length() == 0) {
			erros.add("Identifica��o do ve�culo � obrigat�rio");
		}
		if (viagem.getId() != null) {
			Viagem outro = viagemDao.recuperarPelaIdentificacao(viagem.getIdentificacao());
			if (!viagem.getId().equals(outro.getId())) {
				erros.add("J� existe um ve�culo cadastrado com esta identifica��o");
			}
		}
		*/
		return erros;
	}

	public List<Viagem> listar() throws AppException {
		List<Viagem> result = new ArrayList<Viagem>();
		try {
			result = viagemDao.listar();
		} catch(Exception e) {
			throw new AppException("Erro ao listar viagens: " + e.getMessage(), e);
		}
		return result;
	}

	public Viagem recuperar(Long id) throws AppException {
		Viagem result = null;
		try {
			result = viagemDao.recuperar(id);
		} catch(Exception e) {
			throw new AppException("Erro ao recuperar viagem: " + e.getMessage(), e);
		}
		return result;
	}
	
}
