package dao;

import java.util.List;

import javax.ejb.Stateless;

import modelo.Conta;

@Stateless
public class ContaDao extends GenericDao<Conta> {

	public List<Conta> listarPorNome(String chave, int rows) throws Exception {
		
		List<Conta> result = null;
		
		// Recupera registros iniciando pela string pesquisada.
		String sql = "SELECT x FROM Conta x WHERE" +
				" upper(x.nome) like :iniciandoPor" +
				" order by x.nome";
		System.out.println(sql);
		result = getEntityManager()
				.createQuery(sql, Conta.class)
				.setParameter("iniciandoPor", chave.toUpperCase().concat("%"))
				.getResultList();
		
		// Recupera registros contendo string pesquisada até completar a quantidade de linhas a retornar.
		if (result.size() < rows) {
			List<Conta> result2 = null;
			String sql2 = "SELECT x FROM Conta x WHERE" +
					" upper(x.nome) like :contendo" +
					" and upper(x.nome) not like :iniciandoPor" +
					" order by x.nome";
			result2 = getEntityManager()
					.createQuery(sql2, Conta.class)
					.setParameter("contendo", "%".concat(chave.toUpperCase()).concat("%"))
					.setParameter("iniciandoPor", chave.toUpperCase().concat("%"))
					.getResultList();
			int i = 0;
			while (result.size() + i < rows && result2.size() < i) {
				result.add(result2.get(i));
				i++;
			}
		}
		return result;
	}
}
