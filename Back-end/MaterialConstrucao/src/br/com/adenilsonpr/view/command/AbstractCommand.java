package br.com.adenilsonpr.view.command;

import br.com.adenilsonpr.control.fachada.Fachada;
import br.com.adenilsonpr.control.fachada.IFachada;

public abstract class AbstractCommand  implements ICommand{
    protected IFachada fachada = new Fachada();
}
