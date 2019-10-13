package br.com.adenilsonpr.view.viewhelper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.com.adenilsonpr.model.application.Resultado;
import br.com.adenilsonpr.model.domain.EntidadeDominio;
import br.com.adenilsonpr.model.domain.Login;
import br.com.adenilsonpr.model.domain.Telefone;
import br.com.adenilsonpr.model.domain.Usuario;

public class UsuarioViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {

		String operacao = request.getParameter("OPERACAO");

		Usuario usuario = new Usuario();
		Login login = new Login();
		Telefone telefone = new Telefone();

		Random r = new Random();

		if (!operacao.equals("VISUALIZAR") && !operacao.equals("EXCLUIR") && !operacao.equals("CONSULTAR")) {

			// Dado do Login.
			String senha = request.getParameter("senha");
			String email = request.getParameter("email");

			login.setStatus("Ativo");
			login.setDataCadastro(String.valueOf(new Date()).replace(":", "."));

			if (email != null && !email.trim().equals("")) {
				login.setEmail(email);
			}
			if (senha != null && !senha.trim().equals("")) {
				login.setSenha(senha);
			}

			// Dados do Telefone.
			String tipo = request.getParameter("tipo");
			String ddd = request.getParameter("ddd");
			String numero = request.getParameter("numero");

			telefone.setStatus("Ativo");
			telefone.setDataCadastro(String.valueOf(new Date()).replace(":", "."));

			if (tipo != null && !tipo.trim().equals("")) {
				telefone.setTipo(tipo);
			}
			if (ddd != null && !ddd.trim().equals("")) {
				telefone.setDdd(ddd);
			}
			if (numero != null && !numero.trim().equals("")) {
				telefone.setNumero(numero);
			}

			// Dados do usuario.
			String nome = request.getParameter("nome");
			String cpf = request.getParameter("cpf");
			String nascimento = request.getParameter("nascimento");
			String genero = request.getParameter("genero");
			String rank = request.getParameter("rank");

			usuario.setStatus("Ativo");
			usuario.setDataCadastro(String.valueOf(new Date()).replace(":", "."));
			usuario.setPapel("Cliente");

			if (nome != null && !nome.trim().equals("")) {
				usuario.setNome(nome);
			}
			if (cpf != null && !cpf.trim().equals("")) {
				usuario.setCpf(cpf);
			}
			if (nascimento != null && !nascimento.trim().equals("")) {
				usuario.setNascimento(nascimento);
			}
			if (genero != null && !genero.trim().equals("")) {
				usuario.setGenero(genero);
			}
			if (rank != null && !rank.trim().equals("")) {
				usuario.setRank(Integer.valueOf(rank));
			}

			if (operacao.equals("ALTERAR")) {

				String idusuario = request.getParameter("idUsuario");
				String idLogin = request.getParameter("idLogin");
				String idTelefone = request.getParameter("idTelefone");

				if (idusuario != null && !idusuario.trim().equals("")) {
					usuario.setId(Integer.valueOf(idusuario));
				}
				if (idLogin != null && !idLogin.trim().equals("")) {
					login.setId(Integer.valueOf(idLogin));
				}
				if (idTelefone != null && !idTelefone.trim().equals("")) {
					telefone.setId(Integer.valueOf(idTelefone));
				}
			}

			if (login != null) {
				usuario.setLogin(login);
			}
			if (telefone != null) {
				usuario.setTelefone(telefone);
			}

		} else {

			String id = request.getParameter("id");

			if (operacao.equals("EXCLUIR")) {
				usuario.setStatus("Ativo");
			}

			if (id != null && !id.trim().equals("")) {
				usuario.setId(Integer.valueOf(id));
			} else {
				usuario.setId(0);
			}

		}

		return usuario;

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