package br.com.adenilsonpr.control.util;

import java.lang.reflect.Method;
import java.util.ArrayList;

import br.com.adenilsonpr.model.domain.EntidadeDominio;

public class ObjectForListMethods {

	public static ArrayList<Method> listGetMethods(EntidadeDominio entidade) {

		Method[] listEntityMethods = entidade.getClass().getMethods();
		ArrayList<Method> listGetMethods = new ArrayList<Method>();

		for (Method method : listEntityMethods) {

			if (method.getName().substring(0, 3).equals("get") && !method.getName().equals("getClass")) {
				listGetMethods.add(method);
			}
		}

		return listGetMethods;
	}

	public static ArrayList<Method> listSetMethods(EntidadeDominio entidade) {

		Method[] listEntityMethods = entidade.getClass().getMethods();
		ArrayList<Method> listGetMethods = new ArrayList<Method>();

		for (Method method : listEntityMethods) {

			if (method.getName().substring(0, 3).equals("set")) {
				listGetMethods.add(method);
			}
		}

		return listGetMethods;
	}
}
