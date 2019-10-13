package br.com.adenilsonpr.control.negocio.login;

import br.com.adenilsonpr.control.negocio.IStrategy;
import br.com.adenilsonpr.model.domain.EntidadeDominio;
import br.com.adenilsonpr.model.domain.Usuario;

public class ValidarSenhaForte implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		
		Usuario usuario = (Usuario) entidade; 

		String senha = usuario.getLogin().getSenha();
		
		if (senha.length() < 8) {
			return "A senha precisa ter no minimo 8 caracteres.";
			
		}

		boolean achouNumero = false;
		boolean achouMaiuscula = false;
		boolean achouMinuscula = false;
		boolean achouSimbolo = false;
		
		for (char c : senha.toCharArray()) {
			if (c >= '0' && c <= '9') {
				achouNumero = true;
			} else if (c >= 'A' && c <= 'Z') {
				achouMaiuscula = true;
			} else if (c >= 'a' && c <= 'z') {
				achouMinuscula = true;
			} else {
				achouSimbolo = true;
			}
		}
		
		if(!achouNumero) {
			return "A senha precisa ter no minimo um número.";
		}
		
		if(!achouMaiuscula) {
			return "A senha precisa ter no minimo uma letra maiuscula.";
		}
		
		if(!achouMinuscula) {
			return "A senha precisa ter no minimo uma letra minuscula.";
		}
		
		if(!achouSimbolo) {
			return "A senha precisa ter no minimo um caracter especial.";
		}
		
		return null;

	}

}
