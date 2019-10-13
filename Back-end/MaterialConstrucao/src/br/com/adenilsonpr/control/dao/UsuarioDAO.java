package br.com.adenilsonpr.control.dao;

import java.util.List;

import br.com.adenilsonpr.control.util.Criptografia;
import br.com.adenilsonpr.model.domain.EntidadeDominio;
import br.com.adenilsonpr.model.domain.Login;
import br.com.adenilsonpr.model.domain.Telefone;
import br.com.adenilsonpr.model.domain.Usuario;

public class UsuarioDAO implements IDAO {

	@Override
	public boolean create(EntidadeDominio entidade) {

		Usuario usuario = (Usuario) entidade;
		Login login = usuario.getLogin();
		Telefone telefone = usuario.getTelefone();
		usuario.setLogin(null);
		usuario.setTelefone(null);
		
		EntidadeDominio usuarioPersistido = GenericDAO.create(usuario);
		
		if (usuarioPersistido != null) {
			login.setUsuario(usuarioPersistido.getId());
			login.setSenha(Criptografia.criptografar(login.getSenha()));
			
			if (GenericDAO.create(login) != null) {
				telefone.setUsuario(usuarioPersistido.getId());
				
				if (GenericDAO.create(telefone) != null) {
					return true;
				}	
			}
			return false;
		} else {
			return false;
		}
	}

	@Override
	public boolean update(EntidadeDominio entidade) {
		
		Usuario usuario = (Usuario) entidade;
		Telefone telefone = usuario.getTelefone();
		usuario.setTelefone(null);
		usuario.setLogin(null);

		
		if (GenericDAO.update(usuario)) {
			telefone.setUsuario(usuario.getId());
			if (GenericDAO.update(telefone)) {
				return true;
			}
			return false;
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
		return GenericDAO.read(entidade, "id");
	}

}
