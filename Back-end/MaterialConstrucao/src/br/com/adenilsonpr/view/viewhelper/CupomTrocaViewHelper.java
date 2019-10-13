package br.com.adenilsonpr.view.viewhelper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.com.adenilsonpr.model.application.Resultado;
import br.com.adenilsonpr.model.domain.CupomTroca;
import br.com.adenilsonpr.model.domain.EntidadeDominio;

public class CupomTrocaViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {

		String operacao = request.getParameter("OPERACAO");

		CupomTroca cupomTroca = new CupomTroca();

		if (!operacao.equals("VISUALIZAR") && !operacao.equals("EXCLUIR") && !operacao.equals("CONSULTAR")) {

			// Dado do Cupom de troca.

			String valor = request.getParameter("valor");
			String usuario = request.getParameter("usuario");

			cupomTroca.setStatus("Ativo");
			cupomTroca.setDataCadastro(String.valueOf(new Date()).replace(":", "."));

			if (valor != null && !valor.trim().equals("")) {
				cupomTroca.setValor(Double.valueOf(valor));
			}
			if (usuario != null && !usuario.trim().equals("")) {
				cupomTroca.setUsuario(Integer.valueOf(usuario));
			}

			if (operacao.equals("ALTERAR")) {

				String id = request.getParameter("id");

				if (id != null && !id.trim().equals("")) {
					cupomTroca.setId(Integer.valueOf(id));
				}
			}

		} else {
			String usuario = request.getParameter("usuario");
			String id = request.getParameter("id");

			if (operacao.equals("EXCLUIR")) {
				cupomTroca.setStatus("Ativo");
			}

			if (usuario != null && !usuario.trim().equals("")) {
				cupomTroca.setUsuario(Integer.valueOf(usuario));
			}
			if (id != null && !id.trim().equals("")) {
				cupomTroca.setId(Integer.valueOf(id));
			} else {
				cupomTroca.setId(-2);
			}

		}

		return cupomTroca;

	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		Gson gson = new Gson();

		PrintWriter out = response.getWriter();

		String employeeJsonString = gson.toJson(resultado);

		response.setContentType("application/json");

		response.setCharacterEncoding("UTF-8");

		out.print(employeeJsonString);

		out.flush();
	}

}
