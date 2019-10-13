package br.com.adenilsonpr.control.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.adenilsonpr.model.domain.EntidadeDominio;

public class ObjectForSql {
	public static String[] jsonForList(String json) {
		json = json.replace("{", "");
		json = json.replace("}", "");
		json = json.replace(",", "#");
		json = json.replace(":", "#");
		json = json.replace("\"", "");
		String data[] = json.split("#");
		return data;
	}

	public static String sqlInsert(EntidadeDominio entidade) {

		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy hh.mm.ss").create();
		String json = gson.toJson(entidade, entidade.getClass());

		String sql = "INSERT INTO " + entidade.getClass().getSimpleName().toLowerCase() + "(";

		String[] data = jsonForList(json);

		for (int i = 0; i < data.length; i++) {

			if (i % 2 == 0) {
				sql += data[i].toLowerCase() + ", ";
			}
		}

		sql = sql.substring(0, sql.length() - 2);
		sql += ") VALUES (";

		for (int i = 0; i < data.length; i++) {

			if (i % 2 == 1) {
				sql += "?, ";
			}
		}

		sql = sql.substring(0, sql.length() - 2);
		sql += ");";

		return sql.replace("\"", "");
	}

	public static String insert(EntidadeDominio entidade, String removeColuna) {

		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy hh.mm.ss").create();
		String json = gson.toJson(entidade);

		String sql = "INSERT INTO " + entidade.getClass().getSimpleName().toLowerCase() + "(";
		int indexRemoveCol = -1;

		String[] data = jsonForList(json);

		for (int i = 0; i < data.length; i++) {

			if (data[i].equals(removeColuna)) {
				indexRemoveCol = i;
			}

			if (i % 2 == 0 && !data[i].equals(removeColuna)) {
				sql += data[i].toLowerCase() + ", ";
			}
		}

		sql = sql.substring(0, sql.length() - 2);
		sql += ") VALUES (";

		for (int i = 0; i < data.length; i++) {

			if (i % 2 == 1 && i != (indexRemoveCol + 1)) {
				sql += "?, ";
			}
		}

		sql = sql.substring(0, sql.length() - 2);
		sql += ");";

		return sql.replace("\"", "");
	}

	public static String update(EntidadeDominio entidade, String condicao) {

		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy hh.mm.ss").create();
		String json = gson.toJson(entidade);

		String sql = "UPDATE " + entidade.getClass().getSimpleName().toLowerCase() + " SET ";
		boolean isCondition = false;

		String[] data = jsonForList(json);

		for (int i = 0; i < data.length; i++) {

			if (!data[i].equals(condicao)) {
				if (i % 2 == 0) {
					sql += data[i].toLowerCase();
					isCondition = false;
				} else if (i % 2 == 1 && !isCondition) {
					sql += "=?, ";
				}
			} else {
				isCondition = true;
			}
		}

		sql = sql.substring(0, sql.length() - 2);
		sql += " WHERE " + condicao + "=?;";

		return sql.replace("\"", "");
	}

	public static String select(EntidadeDominio entidade) {

		String sql = "SELECT * FROM " + entidade.getClass().getSimpleName().toLowerCase() + ";";

		return sql.replace("\"", "");
	}

	public static String select(EntidadeDominio entidade, String condicao) {

		String sql = "SELECT * FROM " + entidade.getClass().getSimpleName().toLowerCase() + " WHERE " + condicao + "=?;";

		return sql.replace("\"", "");
	}

	public static String delete(EntidadeDominio entidade, String condicao, String excluirParametro) {

		String sql = "UPDATE " + entidade.getClass().getSimpleName().toLowerCase() + " SET ";
		sql += excluirParametro + "=?";
		sql += " WHERE " + condicao + "=?;";

		return sql.replace("\"", "");
	}

	public static String delete(EntidadeDominio entidade, String condicao) {

		String sql = "DELETE FROM " + entidade.getClass().getSimpleName().toLowerCase();
		sql += " WHERE " + condicao + "=?;";

		return sql.replace("\"", "");

	}
}
