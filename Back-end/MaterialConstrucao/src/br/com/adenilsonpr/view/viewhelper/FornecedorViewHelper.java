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
import br.com.adenilsonpr.model.domain.Endereco;
import br.com.adenilsonpr.model.domain.EntidadeDominio;
import br.com.adenilsonpr.model.domain.Fornecedor;

public class FornecedorViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {

		String operacao = request.getParameter("OPERACAO");

		Fornecedor fornecedor = new Fornecedor();

		if (!operacao.equals("VISUALIZAR") && !operacao.equals("EXCLUIR") && !operacao.equals("CONSULTAR")) {

			// Dados do fornecedor.
			String nome = request.getParameter("nome");
			String cnpj = request.getParameter("cnpj");
			String categoriaFornecida = request.getParameter("categoriaFornecida");

			fornecedor.setStatus("Ativo");
			fornecedor.setDataCadastro(String.valueOf(new Date()).replace(":", "."));

			if (nome != null && !nome.trim().equals("")) {
				fornecedor.setNome(nome);
			}
			
			if (cnpj != null && !cnpj.trim().equals("")) {
				fornecedor.setCnpj(cnpj);
			}
			
			if (categoriaFornecida != null && !categoriaFornecida.trim().equals("")) {
				fornecedor.setCategoriaFornecida(categoriaFornecida);
			}

			if (operacao.equals("ALTERAR")) {

				String idFornecedor = request.getParameter("id");

				if (idFornecedor != null && !idFornecedor.trim().equals("")) {
					fornecedor.setId(Integer.valueOf(idFornecedor));
				}				
			}

		} else {

			String id = request.getParameter("id");

			if (operacao.equals("EXCLUIR")) {
				fornecedor.setStatus("Ativo");
			}

			if (id != null && !id.trim().equals("")) {
				fornecedor.setId(Integer.valueOf(id));
			} else {
				fornecedor.setId(0);
			}

		}

		return fornecedor;
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