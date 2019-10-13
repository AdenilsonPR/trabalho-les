package br.com.adenilsonpr.view.viewhelper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.internal.compiler.codegen.IntegerCache;

import com.google.gson.Gson;

import br.com.adenilsonpr.model.application.Resultado;
import br.com.adenilsonpr.model.domain.EntidadeDominio;
import br.com.adenilsonpr.model.domain.Material;

public class MaterialViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {

		String operacao = request.getParameter("OPERACAO");

		Material material = new Material();

		if (!operacao.equals("VISUALIZAR") && !operacao.equals("EXCLUIR") && !operacao.equals("CONSULTAR")) {

			// Dados do Material.
			String descricao = request.getParameter("descricao");
			String categoria = request.getParameter("categoria");
			String fornecedor = request.getParameter("fornecedor");
			String precificacao = request.getParameter("precificacao");
			String imagem = request.getParameter("imagem");
			String custo = request.getParameter("custo");
			String valorVenda = request.getParameter("valorvenda");
			String quantidade = request.getParameter("quantidade");
			String motivo = request.getParameter("motivo");

			material.setStatus("Ativo");
			material.setDataCadastro(String.valueOf(new Date()).replace(":", "."));

			if (descricao != null && !descricao.trim().equals("")) {
				material.setDescricao(descricao);
			}
			if (categoria != null && !categoria.trim().equals("")) {
				material.setCategoria(categoria);
			}

			if (fornecedor != null && !fornecedor.trim().equals("")) {
				material.setFornecedor(Integer.valueOf(fornecedor));
			}

			if (precificacao != null && !precificacao.trim().equals("")) {
				material.setPrecificacao(Integer.valueOf(precificacao));
			}

			if (imagem != null && !imagem.trim().equals("")) {
				material.setImagem(imagem);
			}

			if (custo != null && !custo.trim().equals("")) {
				material.setCusto(Double.valueOf(custo));
			}

			if (valorVenda != null && !valorVenda.trim().equals("")) {
				material.setValorVenda(Double.valueOf(valorVenda));
			}

			if (quantidade != null && !quantidade.trim().equals("")) {
				material.setQuantidade(Integer.valueOf(quantidade));
			}

			if (operacao.equals("ALTERAR")) {
				int id = Integer.valueOf(request.getParameter("id"));
				material.setId(id);
			}

			if (motivo != null && !motivo.trim().equals("")) {
				material.setMotivo(motivo);
			}

		} else {

			String id = request.getParameter("id");

			if (operacao.equals("EXCLUIR")) {
				material.setStatus("Ativo");
			}

			if (id != null && !id.trim().equals("")) {
				material.setId(Integer.valueOf(id));
			} else {
				material.setId(0);
			}
		}

		return material;
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
