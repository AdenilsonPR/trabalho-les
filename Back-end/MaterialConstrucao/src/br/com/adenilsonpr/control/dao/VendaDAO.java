package br.com.adenilsonpr.control.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.adenilsonpr.model.domain.EntidadeDominio;
import br.com.adenilsonpr.model.domain.Item;
import br.com.adenilsonpr.model.domain.Venda;

public class VendaDAO implements IDAO {

	@Override
	public boolean create(EntidadeDominio entidade) {
		if (GenericDAO.create(entidade) != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean update(EntidadeDominio entidade) {
		if (GenericDAO.update(entidade)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean delete(EntidadeDominio entidade) {
		if (GenericDAO.delete(entidade)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<EntidadeDominio> read(EntidadeDominio entidade) {

		Venda venda = (Venda) entidade;
		ArrayList<EntidadeDominio> entidades;

		if (venda.getUsuario() == 0) {
			entidades = GenericDAO.read(entidade, "id");
		} else {
			entidades = GenericDAO.read(entidade, "usuario");
		}

		ArrayList<Venda> vendas = new ArrayList<Venda>();

		for (EntidadeDominio e : entidades) {
			Venda v = (Venda) e;

			Item itemVenda = new Item();

			itemVenda.setVenda(v.getId());
			itemVenda.setId(-1);

			ArrayList<EntidadeDominio> entidadeItens = GenericDAO.read(itemVenda, "venda");
			ArrayList<Item> itens = new ArrayList<Item>();

			for (EntidadeDominio item : entidadeItens) {
				Item i = (Item) item;
				itens.add(i);
			}

			v.setItens(itens);
			vendas.add(v);
		}

		ArrayList<EntidadeDominio> vendaComItens = new ArrayList<EntidadeDominio>();
		for (Venda vd : vendas) {
			EntidadeDominio ed = (EntidadeDominio) vd;
			vendaComItens.add(ed);
		}

		return vendaComItens;
	}
}
