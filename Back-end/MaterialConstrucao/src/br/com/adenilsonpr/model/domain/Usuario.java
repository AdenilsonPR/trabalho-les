package br.com.adenilsonpr.model.domain;

import java.util.ArrayList;

public class Usuario extends Pessoa {

	private ArrayList<Cartao> cartao;
	private ArrayList<Endereco> enderecos;
	private String papel;
	private int rank;

	public ArrayList<Cartao> getCartao() {
		return cartao;
	}

	public void setCartao(ArrayList<Cartao> cartao) {
		this.cartao = cartao;
	}

	public ArrayList<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(ArrayList<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public String getPapel() {
		return papel;
	}

	public void setPapel(String papel) {
		this.papel = papel;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

}
