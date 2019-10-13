package br.com.adenilsonpr.control.dao;

import java.util.List;

import br.com.adenilsonpr.model.domain.EntidadeDominio;
import br.com.adenilsonpr.model.domain.Telefone;

public class TelefoneDAO implements IDAO {

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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<EntidadeDominio> read(EntidadeDominio entidade) {

		Telefone telefone = (Telefone) entidade;

		if (telefone.getUsuario() == 0) {
			return GenericDAO.read(entidade, "id");
		} else {
			return GenericDAO.read(entidade, "usuario");
		}
	}

}
