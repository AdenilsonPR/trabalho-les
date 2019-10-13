package br.com.adenilsonpr.view.command;

import br.com.adenilsonpr.model.application.Resultado;
import br.com.adenilsonpr.model.domain.EntidadeDominio;

public interface ICommand {
	
	public Resultado execute(EntidadeDominio entidade);
}
