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
import br.com.adenilsonpr.model.domain.Login;

public class LoginViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {

		String operacao = request.getParameter("OPERACAO");

		Login login = new Login();

		if (!operacao.equals("VISUALIZAR") && !operacao.equals("EXCLUIR") && !operacao.equals("CONSULTAR")) {

			// Dados do Login.
			String email = request.getParameter("email");
			String senha = request.getParameter("senha");
			String usuario = request.getParameter("usuario");

			login.setStatus("Ativo");
			login.setDataCadastro(String.valueOf(new Date()).replace(":", "."));

			if (email != null && !email.trim().equals("")) {
				login.setEmail(email);
			}
			if (senha != null && !senha.trim().equals("")) {
				login.setSenha(senha);
			}

			if (usuario != null && !usuario.trim().equals("")) {
				login.setUsuario(Integer.valueOf(usuario));
			}

			if (operacao.equals("ALTERAR")) {
				int id = Integer.valueOf(request.getParameter("id"));
				login.setId(id);
			}

		} else {

			String usuario = request.getParameter("usuario");
			String email = request.getParameter("email");
			String senha = request.getParameter("senha");
			String id = request.getParameter("id");

			if (operacao.equals("EXCLUIR")) {
				login.setStatus("Ativo");
			}

			if (email != null && !email.trim().equals("")) {
				login.setEmail(email);
			}

			if (senha != null && !senha.trim().equals("")) {
				login.setSenha(senha);
			}else {
				login.setSenha("1");				
			}

			if (usuario != null && !usuario.trim().equals("")) {
				login.setUsuario(Integer.valueOf(usuario));
			}

			if (id != null && !id.trim().equals("")) {
				login.setId(Integer.valueOf(id));
			} else {
				login.setId(-1);
			}
		}

		return login;
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
