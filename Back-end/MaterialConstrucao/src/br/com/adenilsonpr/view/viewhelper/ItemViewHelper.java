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
import br.com.adenilsonpr.model.domain.Item;

public class ItemViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		String operacao = request.getParameter("OPERACAO");

		Item item = new Item();

		if (!operacao.equals("VISUALIZAR") && !operacao.equals("EXCLUIR") && !operacao.equals("CONSULTAR")) {

			// Dados da item.
			String descricao = request.getParameter("descricao");
			String quantidade = request.getParameter("quantidade");
			String valorVenda = request.getParameter("valorVenda");
			String valorTotal = request.getParameter("valorTotal");
			String material = request.getParameter("material");
			String usuario = request.getParameter("usuario");
			String venda = request.getParameter("venda");
			String situacao = request.getParameter("situacao");
			String motivo = request.getParameter("motivo");
			String descricaoMotivo = request.getParameter("descricaoMotivo");
			String papel = request.getParameter("papel");

			item.setStatus("Ativo");
			item.setDataCadastro(String.valueOf(new Date()).replace(":", "."));
			

			if (descricao != null && !descricao.trim().equals("")) {
				item.setDescricao(descricao);
			}
			
			if (quantidade != null && !quantidade.trim().equals("")) {
				item.setQuantidade(Integer.valueOf(quantidade));
			}

			if (valorVenda != null && !valorVenda.trim().equals("")) {
				item.setValorVenda(Double.valueOf(valorVenda));
			}
			
			if (valorTotal != null && !valorTotal.trim().equals("")) {
				item.setValorTotal(Double.valueOf(valorTotal));
			}
			
			if (material != null && !material.trim().equals("")) {
				item.setMaterial(Integer.valueOf(material));
			}
			
			if (usuario != null && !usuario.trim().equals("")) {
				item.setUsuario(Integer.valueOf(usuario));
			}
			
			if (venda != null && !venda.trim().equals("")) {
				item.setVenda(Integer.valueOf(venda));
			}
			
			if (situacao != null && !situacao.trim().equals("")) {
				item.setSituacao(situacao);
			}
			
			if (motivo != null && !motivo.trim().equals("")) {
				item.setMotivo(motivo);
			}
			
			if (descricaoMotivo != null && !descricaoMotivo.trim().equals("")) {
				item.setDescricaoMotivo(descricaoMotivo);
			}
			
			if (papel != null && !papel.trim().equals("")) {
				item.setPapel(papel);
			}

			if (operacao.equals("ALTERAR")) {
				int id = Integer.valueOf(request.getParameter("id"));
				item.setId(id);
			}

		} else {

			String quantidade = request.getParameter("quantidade");
			String valorTotal = request.getParameter("valorTotal");
			String material = request.getParameter("material");
			String venda = request.getParameter("venda");
			String id = request.getParameter("id");

			
			if (operacao.equals("EXCLUIR")) {
				item.setStatus("Ativo");
			}

			if (quantidade != null && !quantidade.trim().equals("")) {
				item.setQuantidade(Integer.valueOf(quantidade));
			}

			if (valorTotal != null && !valorTotal.trim().equals("")) {
				item.setValorTotal(Double.valueOf(valorTotal));
			}
			
			if (material != null && !material.trim().equals("")) {
				item.setMaterial(Integer.valueOf(material));
			}
			
			if (venda != null && !venda.trim().equals("")) {
				item.setVenda(Integer.valueOf(venda));
			}

			if (id != null && !id.trim().equals("")) {
				item.setId(Integer.valueOf(id));
			} else {
				item.setId(-1);
			}
		}

		return item;
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
