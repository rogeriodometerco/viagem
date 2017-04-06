package dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import modelo.ComponenteVeiculo;
import modelo.Veiculo;

@Stateless
public class ComponenteVeiculoDao extends GenericDao<Veiculo> {

	public ComponenteVeiculo recuperarPelaPlacaSeExistir(String placa) throws Exception {
		String sql = "SELECT x FROM ComponenteVeiculo x WHERE" +
				" x.placa = :placa";
		ComponenteVeiculo result = null;
		try {
			result = getEntityManager()
					.createQuery(sql, ComponenteVeiculo.class)
					.setParameter("placa", placa)
					.getSingleResult();
		} catch (NoResultException e) {
			// Não tratar como Exceção
		}
		return result;
	}

}
