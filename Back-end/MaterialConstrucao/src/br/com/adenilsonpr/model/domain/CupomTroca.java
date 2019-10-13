package br.com.adenilsonpr.model.domain;

public class CupomTroca extends EntidadeDominio {

	private double valor;
	private int usuario;

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public int getUsuario() {
		return usuario;
	}

	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}
}
