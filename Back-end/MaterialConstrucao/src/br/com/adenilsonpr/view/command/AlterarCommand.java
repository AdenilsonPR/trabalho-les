package br.com.adenilsonpr.view.command;

import br.com.adenilsonpr.model.application.Resultado;
import br.com.adenilsonpr.model.domain.EntidadeDominio;

public class AlterarCommand extends AbstractCommand {

    @Override
    public Resultado execute(EntidadeDominio entidade) {
        return fachada.alterar(entidade);
    }

}
