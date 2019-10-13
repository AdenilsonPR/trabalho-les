package br.com.adenilsonpr.view.viewhelper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.com.adenilsonpr.model.application.Resultado;
import br.com.adenilsonpr.model.domain.Cartao;
import br.com.adenilsonpr.model.domain.EntidadeDominio;

public class CartaoViewHelper implements IViewHelper{

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {

		String operacao = request.getParameter("OPERACAO");

		Cartao cartao = new Cartao();

		if (!operacao.equals("VISUALIZAR") && !operacao.equals("EXCLUIR") && !operacao.equals("CONSULTAR")) {

			// Dado do Cartão.

			String nome = request.getParameter("nome");
			String numero = request.getParameter("numero");
			String bandeira = request.getParameter("bandeira");
			String codigo = request.getParameter("codigo");
			String usuario = request.getParameter("usuario");

			cartao.setStatus("Ativo");
			cartao.setDataCadastro(String.valueOf(new Date()).replace(":", "."));

			if (nome != null && !nome.trim().equals("")) {
				cartao.setNome(nome);
			}
			if (numero != null && !numero.trim().equals("")) {
				cartao.setNumero(numero);
			}
			if (bandeira != null && !bandeira.trim().equals("")) {
				cartao.setBandeira(bandeira);
			}
			if (codigo != null && !codigo.trim().equals("")) {
				cartao.setCodigo(codigo);
			}
			if (usuario != null && !usuario.trim().equals("")) {
				cartao.setUsuario(Integer.valueOf(usuario));
			}

			if (operacao.equals("ALTERAR")) {

				String id = request.getParameter("id");

				if (id != null && !id.trim().equals("")) {
					cartao.setId(Integer.valueOf(id));
				}
			}

		} else {
			String usuario = request.getParameter("usuario");
			String id = request.getParameter("id");
			
			if (operacao.equals("EXCLUIR")) {
				cartao.setStatus("Ativo");
			}

			if (usuario != null && !usuario.trim().equals("")) {
				cartao.setUsuario(Integer.valueOf(usuario));
			}
			if (id != null && !id.trim().equals("")) {
				cartao.setId(Integer.valueOf(id));
			} else {
				cartao.setId(-2);
			}

		}

		return cartao;

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