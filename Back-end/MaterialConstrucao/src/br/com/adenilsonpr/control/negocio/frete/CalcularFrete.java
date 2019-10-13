package br.com.adenilsonpr.control.negocio.frete;

import br.com.adenilsonpr.control.negocio.IStrategy;
import br.com.adenilsonpr.model.domain.EntidadeDominio;
import br.com.adenilsonpr.model.domain.Frete;

public class CalcularFrete implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {

		Frete frete = (Frete) entidade;

		int regiao = Integer.valueOf(frete.getCep().substring(0, 1));

		if (regiao == 0) {
			frete.setValor(10);
		} else if (regiao == 1) {
			frete.setValor(11);

		} else if (regiao == 2) {
			frete.setValor(12);

		} else if (regiao == 3) {
			frete.setValor(13);

		} else if (regiao == 4) {
			frete.setValor(14);

		} else if (regiao == 5) {
			frete.setValor(15);

		} else if (regiao == 6) {
			frete.setValor(16);

		} else if (regiao == 7) {
			frete.setValor(17);

		} else if (regiao == 8) {
			frete.setValor(18);

		} else if (regiao == 9) {
			frete.setValor(19);

		}

		return null;
	}

}
