package br.com.adenilsonpr.view.command;

import br.com.adenilsonpr.model.application.Resultado;
import br.com.adenilsonpr.model.domain.EntidadeDominio;

public class ExcluirCommand extends AbstractCommand {

    @Override
    public Resultado execute(EntidadeDominio entidade) {
        return fachada.excluir(entidade);
    }

}

