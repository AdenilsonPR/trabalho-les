package br.com.adenilsonpr.control.fachada;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.adenilsonpr.control.dao.CartaoDAO;
import br.com.adenilsonpr.control.dao.CupomPromocionalDAO;
import br.com.adenilsonpr.control.dao.CupomTrocaDAO;
import br.com.adenilsonpr.control.dao.EnderecoDAO;
import br.com.adenilsonpr.control.dao.FornecedorDAO;
import br.com.adenilsonpr.control.dao.FreteDAO;
import br.com.adenilsonpr.control.dao.GrupoPrecificacaoDAO;
import br.com.adenilsonpr.control.dao.IDAO;
import br.com.adenilsonpr.control.dao.ItemDAO;
import br.com.adenilsonpr.control.dao.LoginDAO;
import br.com.adenilsonpr.control.dao.MaterialDAO;
import br.com.adenilsonpr.control.dao.TelefoneDAO;
import br.com.adenilsonpr.control.dao.UsuarioDAO;
import br.com.adenilsonpr.control.dao.VendaDAO;
import br.com.adenilsonpr.control.negocio.IStrategy;
import br.com.adenilsonpr.control.negocio.cartao.ValidarExistenciaCartao;
import br.com.adenilsonpr.control.negocio.cliente.RankiarCliente;
import br.com.adenilsonpr.control.negocio.endereco.ValidarExistenciaEndereco;
import br.com.adenilsonpr.control.negocio.frete.CalcularFrete;
import br.com.adenilsonpr.control.negocio.login.ValidarLogin;
import br.com.adenilsonpr.control.negocio.login.ValidarSenhaForte;
import br.com.adenilsonpr.control.negocio.material.CalcularValorVenda;
import br.com.adenilsonpr.control.negocio.material.DarBaixaEstoque;
import br.com.adenilsonpr.control.negocio.material.ReentradaEstoque;
import br.com.adenilsonpr.control.negocio.material.ValidarMaterialMaiorMenosUm;
import br.com.adenilsonpr.control.negocio.material.ValidarMaterialMaiorZero;
import br.com.adenilsonpr.model.application.Resultado;
import br.com.adenilsonpr.model.domain.Cartao;
import br.com.adenilsonpr.model.domain.CupomPromocional;
import br.com.adenilsonpr.model.domain.CupomTroca;
import br.com.adenilsonpr.model.domain.Endereco;
import br.com.adenilsonpr.model.domain.EntidadeDominio;
import br.com.adenilsonpr.model.domain.Fornecedor;
import br.com.adenilsonpr.model.domain.Frete;
import br.com.adenilsonpr.model.domain.GrupoPrecificacao;
import br.com.adenilsonpr.model.domain.Item;
import br.com.adenilsonpr.model.domain.Login;
import br.com.adenilsonpr.model.domain.Material;
import br.com.adenilsonpr.model.domain.Telefone;
import br.com.adenilsonpr.model.domain.Usuario;
import br.com.adenilsonpr.model.domain.Venda;

public class Fachada implements IFachada {

	private Map<String, Map<String, List<IStrategy>>> rns;
	private Map<String, IDAO> daos;
	private Resultado resultado;

	public Fachada() {
		rns = new HashMap<String, Map<String, List<IStrategy>>>();
		daos = new HashMap<String, IDAO>();

		daos.put(Usuario.class.getName(), new UsuarioDAO());
		daos.put(Endereco.class.getName(), new EnderecoDAO());
		daos.put(Cartao.class.getName(), new CartaoDAO());
		daos.put(Login.class.getName(), new LoginDAO());
		daos.put(Telefone.class.getName(), new TelefoneDAO());
		daos.put(Venda.class.getName(), new VendaDAO());
		daos.put(Material.class.getName(), new MaterialDAO());
		daos.put(Item.class.getName(), new ItemDAO());
		daos.put(CupomTroca.class.getName(), new CupomTrocaDAO());
		daos.put(CupomPromocional.class.getName(), new CupomPromocionalDAO());
		daos.put(Fornecedor.class.getName(), new FornecedorDAO());
		daos.put(GrupoPrecificacao.class.getName(), new GrupoPrecificacaoDAO());
		daos.put(Frete.class.getName(), new FreteDAO());

		// Regras de negocio de usuario
		List<IStrategy> rnsSalvarUsuario = new ArrayList<IStrategy>();
		rnsSalvarUsuario.add(new ValidarSenhaForte());
		List<IStrategy> rnsAlterarUsuario = new ArrayList<IStrategy>();
		List<IStrategy> rnsExcluirUsuario = new ArrayList<IStrategy>();
		List<IStrategy> rnsConsultarUsuario = new ArrayList<IStrategy>();

		Map<String, List<IStrategy>> rnsUsuario = new HashMap<String, List<IStrategy>>();
		rnsUsuario.put("SALVAR", rnsSalvarUsuario);
		rnsUsuario.put("ALTERAR", rnsAlterarUsuario);
		rnsUsuario.put("EXCLUIR", rnsExcluirUsuario);
		rnsUsuario.put("CONSULTAR", rnsConsultarUsuario);

		// Regras de negocio de endereco
		List<IStrategy> rnsSalvarEndereco = new ArrayList<IStrategy>();
		List<IStrategy> rnsAlterarEndereco = new ArrayList<IStrategy>();
		List<IStrategy> rnsExcluirEndereco = new ArrayList<IStrategy>();
		List<IStrategy> rnsConsultarEndereco = new ArrayList<IStrategy>();
		rnsSalvarEndereco.add(new ValidarExistenciaEndereco());

		Map<String, List<IStrategy>> rnsEndereco = new HashMap<String, List<IStrategy>>();
		rnsEndereco.put("SALVAR", rnsSalvarEndereco);
		rnsEndereco.put("ALTERAR", rnsAlterarEndereco);
		rnsEndereco.put("EXCLUIR", rnsExcluirEndereco);
		rnsEndereco.put("CONSULTAR", rnsConsultarEndereco);

		// Regras de negocio de cartão
		List<IStrategy> rnsSalvarCartao = new ArrayList<IStrategy>();
		List<IStrategy> rnsAlterarCartao = new ArrayList<IStrategy>();
		List<IStrategy> rnsExcluirCartao = new ArrayList<IStrategy>();
		List<IStrategy> rnsConsultarCartao = new ArrayList<IStrategy>();
		rnsSalvarCartao.add(new ValidarExistenciaCartao());

		Map<String, List<IStrategy>> rnsCartao = new HashMap<String, List<IStrategy>>();
		rnsCartao.put("SALVAR", rnsSalvarCartao);
		rnsCartao.put("ALTERAR", rnsAlterarCartao);
		rnsCartao.put("EXCLUIR", rnsExcluirCartao);
		rnsCartao.put("CONSULTAR", rnsConsultarCartao);

		// Regras de negocio de login
		List<IStrategy> rnsSalvarLogin = new ArrayList<IStrategy>();
		List<IStrategy> rnsAlterarLogin = new ArrayList<IStrategy>();
		List<IStrategy> rnsExcluirLogin = new ArrayList<IStrategy>();
		List<IStrategy> rnsConsultarLogin = new ArrayList<IStrategy>();
		rnsConsultarLogin.add(new ValidarLogin());

		Map<String, List<IStrategy>> rnsLogin = new HashMap<String, List<IStrategy>>();
		rnsLogin.put("SALVAR", rnsSalvarLogin);
		rnsLogin.put("ALTERAR", rnsAlterarLogin);
		rnsLogin.put("EXCLUIR", rnsExcluirLogin);
		rnsLogin.put("CONSULTAR", rnsConsultarLogin);

		// Regras de negocio de telefone
		List<IStrategy> rnsSalvarTelefone = new ArrayList<IStrategy>();
		List<IStrategy> rnsAlterarTelefone = new ArrayList<IStrategy>();
		List<IStrategy> rnsExcluirTelefone = new ArrayList<IStrategy>();
		List<IStrategy> rnsConsultarTelefone = new ArrayList<IStrategy>();

		Map<String, List<IStrategy>> rnsTelefone = new HashMap<String, List<IStrategy>>();
		rnsTelefone.put("SALVAR", rnsSalvarTelefone);
		rnsTelefone.put("ALTERAR", rnsAlterarTelefone);
		rnsTelefone.put("EXCLUIR", rnsExcluirTelefone);
		rnsTelefone.put("CONSULTAR", rnsConsultarTelefone);

		// Regras de negocio do material
		List<IStrategy> rnsSalvarMaterial = new ArrayList<IStrategy>();
		rnsSalvarMaterial.add(new CalcularValorVenda());
		rnsSalvarMaterial.add(new ValidarMaterialMaiorZero());

		List<IStrategy> rnsAlterarMaterial = new ArrayList<IStrategy>();
		rnsAlterarMaterial.add(new CalcularValorVenda());
		rnsAlterarMaterial.add(new ValidarMaterialMaiorMenosUm());

		List<IStrategy> rnsExcluirMaterial = new ArrayList<IStrategy>();
		List<IStrategy> rnsConsultarMaterial = new ArrayList<IStrategy>();

		Map<String, List<IStrategy>> rnsMaterial = new HashMap<String, List<IStrategy>>();
		rnsMaterial.put("SALVAR", rnsSalvarMaterial);
		rnsMaterial.put("ALTERAR", rnsAlterarMaterial);
		rnsMaterial.put("EXCLUIR", rnsExcluirMaterial);
		rnsMaterial.put("CONSULTAR", rnsConsultarMaterial);

		// Regras de negocio do venda
		List<IStrategy> rnsSalvarVenda = new ArrayList<IStrategy>();
		rnsSalvarVenda.add(new RankiarCliente());

		List<IStrategy> rnsAlterarVenda = new ArrayList<IStrategy>();
		List<IStrategy> rnsExcluirVenda = new ArrayList<IStrategy>();
		List<IStrategy> rnsConsultarVenda = new ArrayList<IStrategy>();

		Map<String, List<IStrategy>> rnsVenda = new HashMap<String, List<IStrategy>>();
		rnsVenda.put("SALVAR", rnsSalvarVenda);
		rnsVenda.put("ALTERAR", rnsAlterarVenda);
		rnsVenda.put("EXCLUIR", rnsExcluirVenda);
		rnsVenda.put("CONSULTAR", rnsConsultarVenda);

		// Regras de negocio do item
		List<IStrategy> rnsSalvarItem = new ArrayList<IStrategy>();
		rnsSalvarItem.add(new DarBaixaEstoque());

		List<IStrategy> rnsAlterarItem = new ArrayList<IStrategy>();
		rnsAlterarItem.add(new ReentradaEstoque());

		List<IStrategy> rnsExcluirItem = new ArrayList<IStrategy>();
		List<IStrategy> rnsConsultarItem = new ArrayList<IStrategy>();

		Map<String, List<IStrategy>> rnsItem = new HashMap<String, List<IStrategy>>();
		rnsItem.put("SALVAR", rnsSalvarItem);
		rnsItem.put("ALTERAR", rnsAlterarItem);
		rnsItem.put("EXCLUIR", rnsExcluirItem);
		rnsItem.put("CONSULTAR", rnsConsultarItem);

		// Regras de negocio do cupom de troca
		List<IStrategy> rnsSalvarCupomTroca = new ArrayList<IStrategy>();
		List<IStrategy> rnsAlterarCupomTroca = new ArrayList<IStrategy>();
		List<IStrategy> rnsExcluirCupomTroca = new ArrayList<IStrategy>();
		List<IStrategy> rnsConsultarCupomTroca = new ArrayList<IStrategy>();

		Map<String, List<IStrategy>> rnsCupomTroca = new HashMap<String, List<IStrategy>>();
		rnsCupomTroca.put("SALVAR", rnsSalvarCupomTroca);
		rnsCupomTroca.put("ALTERAR", rnsAlterarCupomTroca);
		rnsCupomTroca.put("EXCLUIR", rnsExcluirCupomTroca);
		rnsCupomTroca.put("CONSULTAR", rnsConsultarCupomTroca);

		// Regras de negocio do cupom promocional
		List<IStrategy> rnsSalvarCupomPromocional = new ArrayList<IStrategy>();
		List<IStrategy> rnsAlterarCupomPromocional = new ArrayList<IStrategy>();
		List<IStrategy> rnsExcluirCupomPromocional = new ArrayList<IStrategy>();
		List<IStrategy> rnsConsultarCupomPromocional = new ArrayList<IStrategy>();

		Map<String, List<IStrategy>> rnsCupomPromocional = new HashMap<String, List<IStrategy>>();
		rnsCupomPromocional.put("SALVAR", rnsSalvarCupomPromocional);
		rnsCupomPromocional.put("ALTERAR", rnsAlterarCupomPromocional);
		rnsCupomPromocional.put("EXCLUIR", rnsExcluirCupomPromocional);
		rnsCupomPromocional.put("CONSULTAR", rnsConsultarCupomPromocional);

		// Regras de negocio do fornecedor
		List<IStrategy> rnsSalvarFornecedor = new ArrayList<IStrategy>();
		List<IStrategy> rnsAlterarFornecedor = new ArrayList<IStrategy>();
		List<IStrategy> rnsExcluirFornecedor = new ArrayList<IStrategy>();
		List<IStrategy> rnsConsultarFornecedor = new ArrayList<IStrategy>();

		Map<String, List<IStrategy>> rnsFornecedor = new HashMap<String, List<IStrategy>>();
		rnsFornecedor.put("SALVAR", rnsSalvarFornecedor);
		rnsFornecedor.put("ALTERAR", rnsAlterarFornecedor);
		rnsFornecedor.put("EXCLUIR", rnsExcluirFornecedor);
		rnsFornecedor.put("CONSULTAR", rnsConsultarFornecedor);

		// Regras do grupo precificacao
		List<IStrategy> rnsSalvarGrupoPrecificacao = new ArrayList<IStrategy>();
		List<IStrategy> rnsAlterarGrupoPrecificacao = new ArrayList<IStrategy>();
		List<IStrategy> rnsExcluirGrupoPrecificacao = new ArrayList<IStrategy>();
		List<IStrategy> rnsConsultarGrupoPrecificacao = new ArrayList<IStrategy>();

		Map<String, List<IStrategy>> rnsGrupoPrecificacao = new HashMap<String, List<IStrategy>>();
		rnsGrupoPrecificacao.put("SALVAR", rnsSalvarGrupoPrecificacao);
		rnsGrupoPrecificacao.put("ALTERAR", rnsAlterarGrupoPrecificacao);
		rnsGrupoPrecificacao.put("EXCLUIR", rnsExcluirGrupoPrecificacao);
		rnsGrupoPrecificacao.put("CONSULTAR", rnsConsultarGrupoPrecificacao);

		// Regras do frete
		List<IStrategy> rnsSalvarFrete = new ArrayList<IStrategy>();
		rnsSalvarFrete.add(new CalcularFrete());

		List<IStrategy> rnsAlterarFrete = new ArrayList<IStrategy>();
		rnsAlterarFrete.add(new CalcularFrete());

		List<IStrategy> rnsExcluirFrete = new ArrayList<IStrategy>();
		List<IStrategy> rnsConsultarFrete = new ArrayList<IStrategy>();

		Map<String, List<IStrategy>> rnsFrete = new HashMap<String, List<IStrategy>>();
		rnsFrete.put("SALVAR", rnsSalvarFrete);
		rnsFrete.put("ALTERAR", rnsAlterarFrete);
		rnsFrete.put("EXCLUIR", rnsExcluirFrete);
		rnsFrete.put("CONSULTAR", rnsConsultarFrete);

		rns.put(Usuario.class.getName(), rnsUsuario);
		rns.put(Endereco.class.getName(), rnsEndereco);
		rns.put(Telefone.class.getName(), rnsTelefone);
		rns.put(Cartao.class.getName(), rnsCartao);
		rns.put(Login.class.getName(), rnsLogin);
		rns.put(Material.class.getName(), rnsMaterial);
		rns.put(Venda.class.getName(), rnsVenda);
		rns.put(Item.class.getName(), rnsItem);
		rns.put(CupomTroca.class.getName(), rnsCupomTroca);
		rns.put(CupomPromocional.class.getName(), rnsCupomPromocional);
		rns.put(Fornecedor.class.getName(), rnsFornecedor);
		rns.put(GrupoPrecificacao.class.getName(), rnsGrupoPrecificacao);
		rns.put(Frete.class.getName(), rnsFrete);

	}

	@Override
	public Resultado salvar(EntidadeDominio entidade) {
		resultado = new Resultado();
		String nomeClasse = entidade.getClass().getName();
		List<EntidadeDominio> entidades = new ArrayList<EntidadeDominio>();
		String msg = executarRegras(entidade, "SALVAR");

		if (msg == null) {
			IDAO dao = daos.get(nomeClasse);
			if (dao.create(entidade)) {
				entidades.add(entidade);
				resultado.setEntidades(entidades);
				resultado.setMensagem("Salvo com sucesso.");

			} else {
				resultado.setMensagem("Falha ao registrar!");
				entidades.add(entidade);
				resultado.setEntidades(entidades);
			}
		} else {
			entidades.add(entidade);
			resultado.setEntidades(entidades);
			resultado.setMensagem(msg);
		}

		return resultado;
	}

	@Override
	public Resultado alterar(EntidadeDominio entidade) {
		resultado = new Resultado();
		String nomeClasse = entidade.getClass().getName();

		List<EntidadeDominio> entidades = new ArrayList<EntidadeDominio>();
		String msg = executarRegras(entidade, "ALTERAR");

		if (msg == null) {
			IDAO dao = daos.get(nomeClasse);
			try {
				if (dao.update(entidade)) {
					entidades.add(entidade);
					resultado.setEntidades(entidades);
					resultado.setMensagem("Alterado com sucesso.");

				} else {
					resultado.setMensagem("Falha ao alterar!");
					entidades.add(entidade);
					resultado.setEntidades(entidades);
				}

			} catch (Exception e) {
				e.printStackTrace();
				resultado.setMensagem("Falha ao alterar!");

			}
		} else {
			resultado.setMensagem(msg);
			entidades.add(entidade);
			resultado.setEntidades(entidades);

		}

		return resultado;
	}

	@Override
	public Resultado excluir(EntidadeDominio entidade) {
		resultado = new Resultado();
		String nomeClasse = entidade.getClass().getName();

		List<EntidadeDominio> entidades = new ArrayList<EntidadeDominio>();
		String msg = executarRegras(entidade, "EXCLUIR");

		if (msg == null) {
			IDAO dao = daos.get(nomeClasse);
			try {
				if (dao.delete(entidade)) {
					entidades.add(entidade);
					resultado.setEntidades(entidades);
					resultado.setMensagem("Excluido com sucesso!");

				} else {
					resultado.setMensagem("Falha ao excluir!");
					entidades.add(entidade);
					resultado.setEntidades(entidades);
				}

			} catch (Exception e) {
				e.printStackTrace();
				resultado.setMensagem("Falha ao excluir!");

			}
		} else {
			resultado.setMensagem(msg);

		}

		return resultado;
	}

	@Override
	public Resultado consultar(EntidadeDominio entidade) {
		resultado = new Resultado();

		String nomeClasse = entidade.getClass().getName();
		String msg = executarRegras(entidade, "CONSULTAR");

		if (msg == null || msg.equals("Valido") || msg.equals("Invalido")) {
			IDAO dao = daos.get(nomeClasse);
			try {
				resultado.setEntidades(dao.read(entidade));
				resultado.setMensagem(msg);

			} catch (Exception e) {
				resultado.setMensagem("Falha ao consultar!");
			}
		} else {

			List<EntidadeDominio> entidades = new ArrayList<EntidadeDominio>();
			entidades.add(entidade);
			resultado.setEntidades(entidades);
			resultado.setMensagem(msg);
		}
		return resultado;
	}

	private String executarRegras(EntidadeDominio entidade, String operacao) {
		String nomeClasse = entidade.getClass().getName();
		StringBuilder msg = new StringBuilder();
		Map<String, List<IStrategy>> regrasOperacao = rns.get(nomeClasse);
		if (regrasOperacao != null) {
			List<IStrategy> regras = regrasOperacao.get(operacao);
			if (regras != null) {
				for (IStrategy s : regras) {
					String m = s.processar(entidade);
					if (m != null) {
						msg.append(m);
					}
				}
			}
		}
		if (msg.length() > 0) {
			return msg.toString();
		} else {
			return null;
		}
	}
}
