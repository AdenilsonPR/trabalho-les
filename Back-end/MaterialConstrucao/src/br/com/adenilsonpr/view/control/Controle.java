package br.com.adenilsonpr.view.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.adenilsonpr.model.application.Resultado;
import br.com.adenilsonpr.model.domain.EntidadeDominio;
import br.com.adenilsonpr.view.command.AlterarCommand;
import br.com.adenilsonpr.view.command.ConsultarCommand;
import br.com.adenilsonpr.view.command.ExcluirCommand;
import br.com.adenilsonpr.view.command.ICommand;
import br.com.adenilsonpr.view.command.SalvarCommand;
import br.com.adenilsonpr.view.command.VisualizarCommand;
import br.com.adenilsonpr.view.viewhelper.CartaoViewHelper;
import br.com.adenilsonpr.view.viewhelper.CupomPromocionalViewHelper;
import br.com.adenilsonpr.view.viewhelper.CupomTrocaViewHelper;
import br.com.adenilsonpr.view.viewhelper.EnderecoViewHelper;
import br.com.adenilsonpr.view.viewhelper.FornecedorViewHelper;
import br.com.adenilsonpr.view.viewhelper.FreteViewHelper;
import br.com.adenilsonpr.view.viewhelper.GrupoPrecificacaoViewHelper;
import br.com.adenilsonpr.view.viewhelper.IViewHelper;
import br.com.adenilsonpr.view.viewhelper.ItemViewHelper;
import br.com.adenilsonpr.view.viewhelper.LoginViewHelper;
import br.com.adenilsonpr.view.viewhelper.MaterialViewHelper;
import br.com.adenilsonpr.view.viewhelper.TelefoneViewHelper;
import br.com.adenilsonpr.view.viewhelper.UsuarioViewHelper;
import br.com.adenilsonpr.view.viewhelper.VendaViewHelper;

public class Controle extends HttpServlet {

	private static Map<String, ICommand> commands;
	private static Map<String, IViewHelper> vhs;

	public Controle() {

		commands = new HashMap<>();

		commands.put("ALTERAR", new AlterarCommand());
		commands.put("CONSULTAR", new ConsultarCommand());
		commands.put("VISUALIZAR", new VisualizarCommand());
		commands.put("SALVAR", new SalvarCommand());
		commands.put("EXCLUIR", new ExcluirCommand());

		vhs = new HashMap<>();

		vhs.put("/MaterialConstrucao/AlterarUsuario", new UsuarioViewHelper());
		vhs.put("/MaterialConstrucao/AlterarEndereco", new EnderecoViewHelper());
		vhs.put("/MaterialConstrucao/AlterarCartao", new CartaoViewHelper());
		vhs.put("/MaterialConstrucao/AlterarLogin", new LoginViewHelper());
		vhs.put("/MaterialConstrucao/AlterarTelefone", new TelefoneViewHelper());
		vhs.put("/MaterialConstrucao/AlterarMaterial", new MaterialViewHelper());
		vhs.put("/MaterialConstrucao/AlterarVenda", new VendaViewHelper());
		vhs.put("/MaterialConstrucao/AlterarItem", new ItemViewHelper());
		vhs.put("/MaterialConstrucao/AlterarCupomTroca", new CupomTrocaViewHelper());
		vhs.put("/MaterialConstrucao/AlterarCupomPromocional", new CupomPromocionalViewHelper());
		vhs.put("/MaterialConstrucao/AlterarFornecedor", new FornecedorViewHelper());
		vhs.put("/MaterialConstrucao/AlterarGrupoPrecificacao", new GrupoPrecificacaoViewHelper());
		vhs.put("/MaterialConstrucao/AlterarFrete", new FreteViewHelper());

		vhs.put("/MaterialConstrucao/ConsultarUsuario", new UsuarioViewHelper());
		vhs.put("/MaterialConstrucao/ConsultarEndereco", new EnderecoViewHelper());
		vhs.put("/MaterialConstrucao/ConsultarCartao", new CartaoViewHelper());
		vhs.put("/MaterialConstrucao/ConsultarLogin", new LoginViewHelper());
		vhs.put("/MaterialConstrucao/ConsultarTelefone", new TelefoneViewHelper());
		vhs.put("/MaterialConstrucao/ConsultarMaterial", new MaterialViewHelper());
		vhs.put("/MaterialConstrucao/ConsultarVenda", new VendaViewHelper());
		vhs.put("/MaterialConstrucao/ConsultarItem", new ItemViewHelper());
		vhs.put("/MaterialConstrucao/ConsultarCupomTroca", new CupomTrocaViewHelper());
		vhs.put("/MaterialConstrucao/ConsultarCupomPromocional", new CupomPromocionalViewHelper());
		vhs.put("/MaterialConstrucao/ConsultarFornecedor", new FornecedorViewHelper());
		vhs.put("/MaterialConstrucao/ConsultarGrupoPrecificacao", new GrupoPrecificacaoViewHelper());
		vhs.put("/MaterialConstrucao/ConsultarFrete", new FreteViewHelper());

		vhs.put("/MaterialConstrucao/SalvarUsuario", new UsuarioViewHelper());
		vhs.put("/MaterialConstrucao/SalvarEndereco", new EnderecoViewHelper());
		vhs.put("/MaterialConstrucao/SalvarCartao", new CartaoViewHelper());
		vhs.put("/MaterialConstrucao/SalvarLogin", new LoginViewHelper());
		vhs.put("/MaterialConstrucao/SalvarTelefone", new TelefoneViewHelper());
		vhs.put("/MaterialConstrucao/SalvarMaterial", new MaterialViewHelper());
		vhs.put("/MaterialConstrucao/SalvarVenda", new VendaViewHelper());
		vhs.put("/MaterialConstrucao/SalvarItem", new ItemViewHelper());
		vhs.put("/MaterialConstrucao/SalvarCupomTroca", new CupomTrocaViewHelper());
		vhs.put("/MaterialConstrucao/SalvarCupomPromocional", new CupomPromocionalViewHelper());
		vhs.put("/MaterialConstrucao/SalvarFornecedor", new FornecedorViewHelper());
		vhs.put("/MaterialConstrucao/SalvarGrupoPrecificacao", new GrupoPrecificacaoViewHelper());
		vhs.put("/MaterialConstrucao/SalvarFrete", new FreteViewHelper());

		vhs.put("/MaterialConstrucao/ExcluirUsuario", new UsuarioViewHelper());
		vhs.put("/MaterialConstrucao/ExcluirEndereco", new EnderecoViewHelper());
		vhs.put("/MaterialConstrucao/ExcluirCartao", new CartaoViewHelper());
		vhs.put("/MaterialConstrucao/ExcluirLogin", new LoginViewHelper());
		vhs.put("/MaterialConstrucao/ExcluirTelefone", new TelefoneViewHelper());
		vhs.put("/MaterialConstrucao/ExcluirMaterial", new MaterialViewHelper());
		vhs.put("/MaterialConstrucao/ExcluirVenda", new VendaViewHelper());
		vhs.put("/MaterialConstrucao/ExcluirItem", new ItemViewHelper());
		vhs.put("/MaterialConstrucao/ExcluirCupomTroca", new CupomTrocaViewHelper());
		vhs.put("/MaterialConstrucao/ExcluirCupomPromocional", new CupomPromocionalViewHelper());
		vhs.put("/MaterialConstrucao/ExcluirFornecedor", new FornecedorViewHelper());
		vhs.put("/MaterialConstrucao/ExcluirGrupoPrecificacao", new GrupoPrecificacaoViewHelper());
		vhs.put("/MaterialConstrucao/ExcluirFrete", new FreteViewHelper());

		vhs.put("/MaterialConstrucao/VisualizarUsuario", new UsuarioViewHelper());
		vhs.put("/MaterialConstrucao/VisualizarEndereco", new EnderecoViewHelper());
		vhs.put("/MaterialConstrucao/VisualizarCartao", new CartaoViewHelper());
		vhs.put("/MaterialConstrucao/VisualizarLogin", new LoginViewHelper());
		vhs.put("/MaterialConstrucao/VisualizarTelefone", new TelefoneViewHelper());
		vhs.put("/MaterialConstrucao/VisualizarMaterial", new MaterialViewHelper());
		vhs.put("/MaterialConstrucao/VisualizarVenda", new VendaViewHelper());
		vhs.put("/MaterialConstrucao/VisualizarItem", new ItemViewHelper());
		vhs.put("/MaterialConstrucao/VisualizarCupomTroca", new CupomTrocaViewHelper());
		vhs.put("/MaterialConstrucao/VisualizarCupomPromocional", new CupomPromocionalViewHelper());
		vhs.put("/MaterialConstrucao/VisualizarFornecedor", new FornecedorViewHelper());
		vhs.put("/MaterialConstrucao/VisualizarGrupoPrecificacao", new GrupoPrecificacaoViewHelper());
		vhs.put("/MaterialConstrucao/VisualizarFrete", new FreteViewHelper());
	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String uri = request.getRequestURI();

		String operacao = request.getParameter("OPERACAO");

		IViewHelper vh = vhs.get(uri);

		EntidadeDominio entidade = vh.getEntidade(request);

		ICommand command = commands.get(operacao);

		Resultado resultado = command.execute(entidade);

		vh.setView(resultado, request, response);

	}
}