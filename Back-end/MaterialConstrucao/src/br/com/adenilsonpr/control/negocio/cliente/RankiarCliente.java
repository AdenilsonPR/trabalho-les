package br.com.adenilsonpr.control.negocio.cliente;

import java.util.List;

import br.com.adenilsonpr.control.dao.GenericDAO;
import br.com.adenilsonpr.control.negocio.IStrategy;
import br.com.adenilsonpr.model.domain.EntidadeDominio;
import br.com.adenilsonpr.model.domain.Usuario;
import br.com.adenilsonpr.model.domain.Venda;

public class RankiarCliente implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {


		Venda venda = (Venda) entidade;
		venda.setId(-1);

		double valorTotalCompras = venda.getTotal();
		
		Usuario usuario = new Usuario();
		usuario.setId(venda.getUsuario());

		List<EntidadeDominio> entidades = GenericDAO.read(venda, "usuario");

		EntidadeDominio entidadeDominio = GenericDAO.read(usuario, "id").get(0);

		usuario = (Usuario) entidadeDominio;

		for (EntidadeDominio e : entidades) {
			Venda v = (Venda) e;

			valorTotalCompras += v.getTotal();
		}

		if (valorTotalCompras <= 1000) {
			usuario.setRank(0);
		} else if (valorTotalCompras > 1000 && valorTotalCompras <= 2000) {
			usuario.setRank(1);
		} else if (valorTotalCompras > 2000 && valorTotalCompras <= 4000) {
			usuario.setRank(2);
		} else if (valorTotalCompras > 4000 && valorTotalCompras <= 8000) {
			usuario.setRank(3);
		} else if (valorTotalCompras > 8000 && valorTotalCompras <= 16000) {
			usuario.setRank(4);
		} else if (valorTotalCompras > 16000) {
			usuario.setRank(5);
		}

		GenericDAO.update(usuario);

		return null;
	}

}
