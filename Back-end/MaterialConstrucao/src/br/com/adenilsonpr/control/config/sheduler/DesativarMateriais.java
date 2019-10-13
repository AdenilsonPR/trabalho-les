package br.com.adenilsonpr.control.config.sheduler;

import java.util.List;
import java.util.TimerTask;

import br.com.adenilsonpr.control.dao.GenericDAO;
import br.com.adenilsonpr.model.domain.EntidadeDominio;
import br.com.adenilsonpr.model.domain.Material;

public class DesativarMateriais extends TimerTask{

	@Override
	public void run() {
		
		List<EntidadeDominio> entidades = GenericDAO.read(new Material(), "id"); 
		
		for (EntidadeDominio entidade : entidades) {
			
			Material material = (Material) entidade;
			
			if(material.getQuantidade() <= 0 && material.getStatus().equals("Ativo")) {
				
				material.setMotivo("Material esgotado.");
				GenericDAO.update(entidade);
				GenericDAO.delete(entidade);
				System.out.println(material.getDescricao());
			}
			
		}
		
	}

}
