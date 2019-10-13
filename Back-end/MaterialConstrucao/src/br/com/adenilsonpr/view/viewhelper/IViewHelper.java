package br.com.adenilsonpr.view.viewhelper;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.adenilsonpr.model.application.Resultado;
import br.com.adenilsonpr.model.domain.EntidadeDominio;

public interface IViewHelper {

	public EntidadeDominio getEntidade(HttpServletRequest request);

    public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
}
