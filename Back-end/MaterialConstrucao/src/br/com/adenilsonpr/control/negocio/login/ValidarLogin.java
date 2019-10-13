package br.com.adenilsonpr.control.negocio.login;

import java.util.ArrayList;

import br.com.adenilsonpr.control.dao.GenericDAO;
import br.com.adenilsonpr.control.negocio.IStrategy;
import br.com.adenilsonpr.control.util.Criptografia;
import br.com.adenilsonpr.model.domain.EntidadeDominio;
import br.com.adenilsonpr.model.domain.Login;

public class ValidarLogin implements IStrategy{

	@Override
	public String processar(EntidadeDominio entidade) {
		
		Login login = (Login) entidade;
		
		ArrayList<EntidadeDominio> entidades = GenericDAO.read(entidade, "id");
		String senhaCriptografada = Criptografia.criptografar(login.getSenha());
		
		for (EntidadeDominio e : entidades) {
			Login l = (Login) e;
			
			if(login.getEmail().equals(l.getEmail())) {
				if(senhaCriptografada.equals(l.getSenha())) {
					return "Valido";
				}
			}
		}
		
		return "Invalido";
	}


}
