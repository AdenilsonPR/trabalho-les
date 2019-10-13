package br.com.adenilsonpr.model.domain;

public class Fornecedor extends EntidadeDominio{

	private String nome;
	private String cnpj;
	private String categoriaFornecida;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getCategoriaFornecida() {
		return categoriaFornecida;
	}

	public void setCategoriaFornecida(String categoriaFornecida) {
		this.categoriaFornecida = categoriaFornecida;
	}
}
