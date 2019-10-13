package br.com.adenilsonpr.model.domain;

import java.util.ArrayList;

import br.com.adenilsonpr.model.domain.EntidadeDominio;

public class Pessoa extends EntidadeDominio {

	private String nome;
	private String genero;
	private String nascimento;
	private String cpf;
	private Telefone telefone;
	private ArrayList<Endereco> endereco;
	private Login login;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getNascimento() {
		return nascimento;
	}

	public void setNascimento(String nascimento) {
		this.nascimento = nascimento;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public ArrayList<Endereco> getEndereco() {
		return endereco;
	}

	public void setEndereco(ArrayList<Endereco> endereco) {
		this.endereco = endereco;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

}
