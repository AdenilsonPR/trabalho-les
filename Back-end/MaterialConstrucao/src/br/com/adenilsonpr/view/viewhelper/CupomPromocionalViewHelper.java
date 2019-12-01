package br.com.adenilsonpr.view.viewhelper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.com.adenilsonpr.model.application.Resultado;
import br.com.adenilsonpr.model.domain.CupomPromocional;
import br.com.adenilsonpr.model.domain.CupomTroca;
import br.com.adenilsonpr.model.domain.EntidadeDominio;

public class CupomPromocionalViewHelper implements IViewHelper{


	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {

		String operacao = request.getParameter("OPERACAO");

		CupomPromocional cupomPromocional = new CupomPromocional();

		if (!operacao.equals("VISUALIZAR") && !operacao.equals("EXCLUIR") && !operacao.equals("CONSULTAR")) {

			// Dado do Cupom promocional.

			String codigo = request.getParameter("codigo");
			String valor = request.getParameter("valor");
			String validade = request.getParameter("validade");

			cupomPromocional.setStatus("Ativo");
			cupomPromocional.setDataCadastro(String.valueOf(new Date()).replace(":", "."));

			if (codigo != null && !codigo.trim().equals("")) {
				cupomPromocional.setCodigo(codigo);
			}
			
			if (valor != null && !valor.trim().equals("")) {
				cupomPromocional.setValor(Double.valueOf(valor));
			}
			
			if (validade!= null && !validade.trim().equals("")) {
				cupomPromocional.setValidade(validade);
			}
			

			if (operacao.equals("ALTERAR")) {

				String id = request.getParameter("id");

				if (id != null && !id.trim().equals("")) {
					cupomPromocional.setId(Integer.valueOf(id));
				}
			}

		} else {
			String id = request.getParameter("id");

			if (operacao.equals("EXCLUIR")) {
				cupomPromocional.setStatus("Ativo");
			}

			
			
			if (id != null && !id.trim().equals("")) {
				cupomPromocional.setId(Integer.valueOf(id));
			} else {
				cupomPromocional.setId(0);
			}

		}

		return cupomPromocional;

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