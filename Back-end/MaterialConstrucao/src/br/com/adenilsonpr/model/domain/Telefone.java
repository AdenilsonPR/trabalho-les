package br.com.adenilsonpr.model.domain;

import br.com.adenilsonpr.model.domain.EntidadeDominio;

public class Telefone extends EntidadeDominio {

	private String tipo;
	private String ddd;
	private String numero;
	private int usuario;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public int getUsuario() {
		return usuario;
	}

	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}

	
}
