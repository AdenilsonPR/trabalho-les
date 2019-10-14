package br.com.adenilsonpr.control.negocio.material;

import java.util.List;

import br.com.adenilsonpr.control.dao.MaterialDAO;
import br.com.adenilsonpr.control.negocio.IStrategy;
import br.com.adenilsonpr.model.domain.EntidadeDominio;
import br.com.adenilsonpr.model.domain.Item;
import br.com.adenilsonpr.model.domain.Material;

public class DarBaixaEstoque implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {

		Item item = (Item) entidade;

		Material materialConsulta = new Material();

		materialConsulta.setId(item.getMaterial());

		MaterialDAO dao = new MaterialDAO();

		List<EntidadeDominio> materiais = dao.read(materialConsulta);

		Material materialAlterado = (Material) materiais.get(0);

		materialAlterado.setQuantidade(materialAlterado.getQuantidade() - item.getQuantidade());
		dao.update(materialAlterado);

		return null;
	}

}
