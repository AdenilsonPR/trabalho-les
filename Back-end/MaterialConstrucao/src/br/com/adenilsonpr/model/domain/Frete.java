package br.com.adenilsonpr.model.domain;

public class Frete extends EntidadeDominio {

	private String cep;
	private double valor;

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

}
