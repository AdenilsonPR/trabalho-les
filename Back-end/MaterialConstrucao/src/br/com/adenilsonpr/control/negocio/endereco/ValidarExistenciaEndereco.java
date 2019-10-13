package br.com.adenilsonpr.control.negocio.endereco;

import java.util.ArrayList;

import br.com.adenilsonpr.control.dao.GenericDAO;
import br.com.adenilsonpr.control.negocio.IStrategy;
import br.com.adenilsonpr.model.domain.Endereco;
import br.com.adenilsonpr.model.domain.EntidadeDominio;

public class ValidarExistenciaEndereco implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		Endereco endereco = (Endereco) entidade;

		ArrayList<EntidadeDominio> entidades = GenericDAO.read(entidade, "id");

		for (EntidadeDominio e : entidades) {
			Endereco ed = (Endereco) e;
			if (endereco.getCep().equals(ed.getCep()) && endereco.getNumero().equals(ed.getNumero())
					&& endereco.getUsuario() == ed.getUsuario()) {
				return "Endereço já cadastrado";
			}
		}

		return null;
	}

}
