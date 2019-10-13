package br.com.adenilsonpr.control.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.adenilsonpr.control.util.Criptografia;
import br.com.adenilsonpr.model.domain.EntidadeDominio;
import br.com.adenilsonpr.model.domain.Login;

public class LoginDAO implements IDAO {

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

		Login login = (Login) entidade;
		login.setSenha(Criptografia.criptografar(login.getSenha()));

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

		Login login = (Login) entidade;

		ArrayList<EntidadeDominio> entidades = GenericDAO.read(entidade, "id");

		for (EntidadeDominio e : entidades) {
			Login l = (Login) e;

			if (login.getEmail().equals(l.getEmail()) && login.getUsuario() == 0) {
				login.setUsuario(l.getUsuario());
				login.setId(-1);
			}

		}

		if (login.getUsuario() == 0) {
			return GenericDAO.read(entidade, "id");
		} else {
			return GenericDAO.read(entidade, "usuario");
		}
	}

}
