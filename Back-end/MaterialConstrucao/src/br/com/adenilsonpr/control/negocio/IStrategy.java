package br.com.adenilsonpr.control.negocio;

import br.com.adenilsonpr.model.domain.EntidadeDominio;

public interface IStrategy {
	public String processar(EntidadeDominio entidade);
}
