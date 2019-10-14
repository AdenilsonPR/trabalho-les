package br.com.adenilsonpr.model.domain;

public class GrupoPrecificacao extends EntidadeDominio {

	private String precificacao;
	private double porcentagem;

	public String getPrecificacao() {
		return precificacao;
	}

	public void setPrecificacao(String precificacao) {
		this.precificacao = precificacao;
	}

	public double getPorcentagem() {
		return porcentagem;
	}

	public void setPorcentagem(double porcentagem) {
		this.porcentagem = porcentagem;
	}

}
