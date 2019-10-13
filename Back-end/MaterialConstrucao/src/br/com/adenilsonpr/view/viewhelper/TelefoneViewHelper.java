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
import br.com.adenilsonpr.model.domain.Telefone;

public class TelefoneViewHelper implements IViewHelper{

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {

		String operacao = request.getParameter("OPERACAO");

		Telefone telefone = new Telefone();

		if (!operacao.equals("VISUALIZAR") && !operacao.equals("EXCLUIR") && !operacao.equals("CONSULTAR")) {

			// Dados do Telefone.
			String tipo = request.getParameter("tipo");
			String ddd = request.getParameter("ddd");
			String numero = request.getParameter("numero");
			String usuario = request.getParameter("usuario");

			telefone.setStatus("Ativo");
			telefone.setDataCadastro(String.valueOf(new Date()).replace(":", "."));

			if (tipo != null && !tipo.trim().equals("")) {
				telefone.setTipo(tipo);;
			}
			if (ddd != null && !ddd.trim().equals("")) {
				telefone.setDdd(ddd);;
			}
			if (numero != null && !numero.trim().equals("")) {
				telefone.setNumero(numero);;
			}

			if (usuario != null && !usuario.trim().equals("")) {
				telefone.setUsuario(Integer.valueOf(usuario));
			}

			if (operacao.equals("ALTERAR")) {
				int cliId = Integer.valueOf(request.getParameter("txtAlterarClienteId"));
				telefone.setId(cliId);
			}

		}

		else {

			String usuario = request.getParameter("usuario");
			String id = request.getParameter("id");

			if (usuario != null && !usuario.trim().equals("")) {
				telefone.setUsuario(Integer.valueOf(usuario));
			}

			if (id != null && !id.trim().equals("")) {
				telefone.setId(Integer.valueOf(id));
			} else {
				telefone.setId(-1);
			}
		}

		return telefone;
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