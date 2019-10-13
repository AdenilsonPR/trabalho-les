package br.com.adenilsonpr.model.domain;

import br.com.adenilsonpr.model.domain.EntidadeDominio;

public class Login extends EntidadeDominio {

	private String email;
	private String senha;
	private int usuario;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public int getUsuario() {
		return usuario;
	}

	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}
	
	

}
