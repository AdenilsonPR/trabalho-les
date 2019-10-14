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
import br.com.adenilsonpr.model.domain.GrupoPrecificacao;

public class GrupoPrecificacaoViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {

		String operacao = request.getParameter("OPERACAO");

		GrupoPrecificacao grupoPrecificacao = new GrupoPrecificacao();

		if (!operacao.equals("VISUALIZAR") && !operacao.equals("EXCLUIR") && !operacao.equals("CONSULTAR")) {

			// Dado do preficicação.

			String descricao = request.getParameter("precificacao");
			String precificacao = request.getParameter("porcentagem");

			if (operacao.equals("SALVAR")) {
				grupoPrecificacao.setStatus("Ativo");
				grupoPrecificacao.setDataCadastro(String.valueOf(new Date()).replace(":", "."));
			}

			if (descricao != null && !descricao.trim().equals("")) {
				grupoPrecificacao.setPrecificacao(descricao);
			}

			if (precificacao != null && !precificacao.trim().equals("")) {
				grupoPrecificacao.setPorcentagem(Double.valueOf(precificacao));
			}

			if (operacao.equals("ALTERAR")) {

				String id = request.getParameter("id");
				String status = request.getParameter("status");

				if (id != null && !id.trim().equals("")) {
					grupoPrecificacao.setId(Integer.valueOf(id));
				}

				if (status != null && !status.trim().equals("")) {
					grupoPrecificacao.setStatus(status);
				}
			}

		} else {
			String id = request.getParameter("id");

			if (operacao.equals("EXCLUIR")) {
				grupoPrecificacao.setStatus("Ativo");
			}

			if (id != null && !id.trim().equals("")) {
				grupoPrecificacao.setId(Integer.valueOf(id));
			} else {
				grupoPrecificacao.setId(0);
			}

		}

		return grupoPrecificacao;

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
