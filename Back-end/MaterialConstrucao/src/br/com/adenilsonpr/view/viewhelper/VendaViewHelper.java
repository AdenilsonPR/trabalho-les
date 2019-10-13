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
import br.com.adenilsonpr.model.domain.Venda;

public class VendaViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		String operacao = request.getParameter("OPERACAO");

		Venda venda = new Venda();

		if (!operacao.equals("VISUALIZAR") && !operacao.equals("EXCLUIR") && !operacao.equals("CONSULTAR")) {

			// Dados da venda.
			String total = request.getParameter("total");
			String usuario = request.getParameter("usuario");

			venda.setStatus("Ativo");

			if (operacao.equals("SALVAR")) {
				venda.setDataCadastro(String.valueOf(new Date()).replace(":", "."));
				venda.setSituacao("Em processamento");
			}
			
			if (total != null && !total.trim().equals("")) {
				venda.setTotal(Double.valueOf(total));
			}

			if (usuario != null && !usuario.trim().equals("")) {
				venda.setUsuario(Integer.valueOf(usuario));
			}

			if (operacao.equals("ALTERAR")) {
				
				int id = Integer.valueOf(request.getParameter("id"));
				venda.setId(id);

				String situacao = request.getParameter("situacao");
				venda.setSituacao(situacao );
				
			}

		} else {

			String total = request.getParameter("total");
			String usuario = request.getParameter("usuario");
			String id = request.getParameter("id");

			if (operacao.equals("EXCLUIR")) {
				venda.setStatus("Ativo");
			}

			if (total != null && !total.trim().equals("")) {
				venda.setTotal(Double.valueOf(total));
			}

			if (usuario != null && !usuario.trim().equals("")) {
				venda.setUsuario(Integer.valueOf(usuario));
			}

			if (id != null && !id.trim().equals("")) {
				venda.setId(Integer.valueOf(id));
			}
		}

		return venda;
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
