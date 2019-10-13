package br.com.adenilsonpr.model.domain;

public class GrupoPrecificacao extends EntidadeDominio {

	private String descricao;
	private double porcentagem;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getPorcentagem() {
		return porcentagem;
	}

	public void setPorcentagem(double porcentagem) {
		this.porcentagem = porcentagem;
	}

}
