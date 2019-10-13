package br.com.adenilsonpr.control.fachada;

import br.com.adenilsonpr.model.application.Resultado;
import br.com.adenilsonpr.model.domain.EntidadeDominio;

public interface IFachada {

	public Resultado salvar(EntidadeDominio entidade);

    public Resultado alterar(EntidadeDominio entidade);

    public Resultado excluir(EntidadeDominio entidade);

    public Resultado consultar(EntidadeDominio entidade);

}
