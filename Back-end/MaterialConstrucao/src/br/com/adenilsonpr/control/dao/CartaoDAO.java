package br.com.adenilsonpr.control.dao;

import java.util.List;

import br.com.adenilsonpr.model.domain.Cartao;
import br.com.adenilsonpr.model.domain.EntidadeDominio;



public class CartaoDAO implements IDAO{

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
		
		Cartao cartao = (Cartao) entidade;

		if (cartao.getUsuario() == 0) {
			return GenericDAO.read(entidade, "id");
		} else {
			return GenericDAO.read(entidade, "usuario");
		}
	}

}
