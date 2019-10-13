package br.com.adenilsonpr.control.dao;

import java.util.List;

import br.com.adenilsonpr.model.domain.EntidadeDominio;

public interface IDAO {

	public boolean create(EntidadeDominio entidade);

	public boolean update(EntidadeDominio entidade);

	public boolean delete(EntidadeDominio entidade);

	public List<EntidadeDominio> read(EntidadeDominio entidade);
}
