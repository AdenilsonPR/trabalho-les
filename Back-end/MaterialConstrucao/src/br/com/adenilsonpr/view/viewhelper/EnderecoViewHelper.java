package br.com.adenilsonpr.view.viewhelper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.com.adenilsonpr.model.application.Resultado;
import br.com.adenilsonpr.model.domain.Endereco;
import br.com.adenilsonpr.model.domain.EntidadeDominio;

public class EnderecoViewHelper implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {

		String operacao = request.getParameter("OPERACAO");

		Endereco endereco = new Endereco();

		if (!operacao.equals("VISUALIZAR") && !operacao.equals("EXCLUIR") && !operacao.equals("CONSULTAR")) {

			// Dado do Endereço.
			String cep = request.getParameter("cep");
			String logradouro = request.getParameter("logradouro");
			String bairro = request.getParameter("bairro");
			String cidade = request.getParameter("cidade");
			String estado = request.getParameter("estado");
			String pais = request.getParameter("pais");
			String numero = request.getParameter("numero");
			String tipoResidencia = request.getParameter("tipoResidencia");
			String tipoLogradouro = request.getParameter("tipoLogradouro");
			String cobranca = request.getParameter("cobranca");
			String entrega = request.getParameter("entrega");
			String usuario = request.getParameter("usuario");

			endereco.setStatus("Ativo");
			endereco.setDataCadastro(String.valueOf(new Date()).replace(":", "."));

			if (cep != null && !cep.trim().equals("")) {
				endereco.setCep(cep);
			}
			if (logradouro != null && !logradouro.trim().equals("")) {
				endereco.setLogradouro(logradouro);
			}
			if (bairro != null && !bairro.trim().equals("")) {
				endereco.setBairro(bairro);
			}
			if (cidade != null && !cidade.trim().equals("")) {
				endereco.setCidade(cidade);
			}
			if (estado != null && !estado.trim().equals("")) {
				endereco.setEstado(estado);
			}
			if (pais != null && !pais.trim().equals("")) {
				endereco.setPais(pais);
			}
			if (numero != null && !numero.trim().equals("")) {
				endereco.setNumero(numero);
			}
			if (tipoResidencia != null && !tipoResidencia.trim().equals("")) {
				endereco.setTipoResidencia(tipoResidencia);
			}
			if (tipoLogradouro != null && !tipoLogradouro.trim().equals("")) {
				endereco.setTipoLogradouro(tipoLogradouro);
			}
			if (cobranca != null && !cobranca.trim().equals("")) {
				endereco.setCobranca(cobranca);
			}
			if (entrega != null && !entrega.trim().equals("")) {
				endereco.setEntrega(entrega);
			}
			if (usuario != null && !usuario.trim().equals("")) {
				endereco.setUsuario(Integer.valueOf(usuario));
			}

			if (operacao.equals("ALTERAR")) {

				String id = request.getParameter("id");

				if (id != null && !id.trim().equals("")) {
					endereco.setId(Integer.valueOf(id));
				}

			}

		} else {
			String usuario = request.getParameter("usuario");
			String id = request.getParameter("id");

			if (operacao.equals("EXCLUIR")) {
				endereco.setStatus("Ativo");
			}

			if (usuario != null && !usuario.trim().equals("")) {
				endereco.setUsuario(Integer.valueOf(usuario));
			}
			if (id != null && !id.trim().equals("")) {
				endereco.setId(Integer.valueOf(id));
			} else {
				endereco.setId(-2);
			}

		}

		return endereco;

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