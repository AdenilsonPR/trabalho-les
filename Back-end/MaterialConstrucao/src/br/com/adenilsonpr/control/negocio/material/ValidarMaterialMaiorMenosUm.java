package br.com.adenilsonpr.control.negocio.material;

import br.com.adenilsonpr.control.negocio.IStrategy;
import br.com.adenilsonpr.model.domain.EntidadeDominio;
import br.com.adenilsonpr.model.domain.Material;

public class ValidarMaterialMaiorMenosUm implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {

		Material material = (Material) entidade;

		if (material.getQuantidade() < 0) {
			return "Quantidade invalida de materiais.";
		}

		return null;
	}

}
