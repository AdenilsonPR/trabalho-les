package br.com.adenilsonpr.control.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.adenilsonpr.model.domain.EntidadeDominio;
import br.com.adenilsonpr.model.domain.Item;
import br.com.adenilsonpr.model.domain.Material;
import br.com.adenilsonpr.model.domain.Venda;

public class MaterialDAO implements IDAO {

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

		ArrayList<EntidadeDominio> entidades;

		entidades = GenericDAO.read(entidade, "id");

		ArrayList<Material> materiais = new ArrayList<Material>();

		for (EntidadeDominio e : entidades) {

			Material m = (Material) e;

			if (m.getQuantidade() > 0 || m.getStatus().equals("Desativo")) {
				materiais.add(m);
			}
		}

		ArrayList<EntidadeDominio> materiaisMaioresZero = new ArrayList<EntidadeDominio>();
		for (Material m : materiais) {
			EntidadeDominio e = (EntidadeDominio) m;
			materiaisMaioresZero.add(e);
		}

		return materiaisMaioresZero;
	}

}
