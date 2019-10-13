package br.com.adenilsonpr.view.viewhelper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.com.adenilsonpr.model.application.Resultado;
import br.com.adenilsonpr.model.domain.EntidadeDominio;
import br.com.adenilsonpr.model.domain.Frete;

public class FreteViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {

		String operacao = request.getParameter("OPERACAO");

		Frete frete = new Frete();

		if (!operacao.equals("VISUALIZAR") && !operacao.equals("EXCLUIR") && !operacao.equals("CONSULTAR")) {

			// Dados do Frete.
			String cep = request.getParameter("cep");
			String valor = request.getParameter("valor");

			frete.setStatus("Ativo");
			frete.setDataCadastro(String.valueOf(new Date()).replace(":", "."));

			if (cep != null && !cep.trim().equals("")) {
				frete.setCep(cep);
				;
			}
			if (valor != null && !valor.trim().equals("")) {
				frete.setValor(Double.valueOf(valor));
			}

			if (operacao.equals("ALTERAR")) {
				String id = request.getParameter("id");
				frete.setId(Integer.valueOf(id));
			}

		}

		else {

			String id = request.getParameter("id");

			if (operacao.equals("EXCLUIR")) {
				frete.setStatus("Ativo");
			}

			if (id != null && !id.trim().equals("")) {
				frete.setId(Integer.valueOf(id));
			} else {
				frete.setId(0);
			}
		}

		return frete;
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
