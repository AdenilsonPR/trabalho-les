package br.com.adenilsonpr.control.negocio.material;

import java.util.List;

import br.com.adenilsonpr.control.dao.GenericDAO;
import br.com.adenilsonpr.control.negocio.IStrategy;
import br.com.adenilsonpr.model.domain.EntidadeDominio;
import br.com.adenilsonpr.model.domain.GrupoPrecificacao;
import br.com.adenilsonpr.model.domain.Material;

public class CalcularValorVenda implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {

		Material material = (Material) entidade;

		GrupoPrecificacao precificacao = new GrupoPrecificacao();

		precificacao.setId(material.getPrecificacao());

		List<EntidadeDominio> entidades = GenericDAO.read(precificacao, "id");

		precificacao = (GrupoPrecificacao) entidades.get(0);

		material.setValorVenda(material.getCusto() * ((precificacao.getPorcentagem() / 100) + 1));

		if (material.getValorVenda() < material.getCusto()) {
			return "Valor de venda menor que o valor de custo.";
		}

		return null;
	}

}
