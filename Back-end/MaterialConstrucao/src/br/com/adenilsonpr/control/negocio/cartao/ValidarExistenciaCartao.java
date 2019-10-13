package br.com.adenilsonpr.control.negocio.cartao;

import java.util.ArrayList;

import br.com.adenilsonpr.control.dao.GenericDAO;
import br.com.adenilsonpr.control.negocio.IStrategy;
import br.com.adenilsonpr.model.domain.Cartao;
import br.com.adenilsonpr.model.domain.EntidadeDominio;

public class ValidarExistenciaCartao implements IStrategy{

	@Override
	public String processar(EntidadeDominio entidade) {
		Cartao  cartao = (Cartao) entidade;
		
		ArrayList <EntidadeDominio> entidades = GenericDAO.read(entidade, "id");
		
		for (EntidadeDominio e : entidades) {
			Cartao c = (Cartao) e;
			if(cartao.getNumero().equals(c.getNumero()) && cartao.getUsuario() == c.getUsuario()) {
				return "Cartão já cadastrado.";
			}
		}
		
		return null;
	}

}
