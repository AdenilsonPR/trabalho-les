package br.com.adenilsonpr.control.dao;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.adenilsonpr.control.util.Criptografia;
import br.com.adenilsonpr.control.util.DBConnection;
import br.com.adenilsonpr.control.util.ObjectForListMethods;
import br.com.adenilsonpr.control.util.ObjectForSql;
import br.com.adenilsonpr.model.domain.EntidadeDominio;


public class GenericDAO {

	public static EntidadeDominio create(EntidadeDominio entidade){
		if (entidade == null) {
			return null;
		} else {
			Connection conn = null;
			try {

				conn = DBConnection.getConnection();
				conn.setAutoCommit(false);

				Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy hh.mm.ss").create();
				String json = gson.toJson(entidade);
				String[] data = ObjectForSql.jsonForList(json);
				String[] jsonAttributes = new String[data.length / 2];
				ArrayList<Method> listGetMethods = ObjectForListMethods.listGetMethods(entidade);

				for (int i = 0, j = 0; i < data.length; i += 2, j++) {
					jsonAttributes[j] = data[i];
				}

				PreparedStatement pstm = conn.prepareStatement(ObjectForSql.insert(entidade, "id"), Statement.RETURN_GENERATED_KEYS);

				int indexValue = 1;
				for (int i = 0; i < jsonAttributes.length; i++) {
					for (Method method : listGetMethods) {
						if (jsonAttributes[i].toLowerCase().equals(method.getName().toLowerCase().substring(3, method.getName().length()))) {
							if (!jsonAttributes[i].equals("id") && !jsonAttributes[i].equals("status")) {
								pstm.setObject(indexValue++, method.invoke(entidade));
							} else if (jsonAttributes[i].equals("status")) {
								pstm.setObject(indexValue++, "Ativo");
							}
							
						}
					}
				}

				pstm.execute();
				
				ResultSet rs = pstm.getGeneratedKeys();
	            
				int id = 0;
	            if (rs.next()) {
	                id = rs.getInt(1);
	            }
	            entidade.setId(id);
				
				conn.commit();
				return entidade;
			} catch (Exception e) {
				try {
					conn.rollback();
				} catch (SQLException e1) {	
					e1.printStackTrace();
				}
				e.printStackTrace();
				return null;
			} finally {
				DBConnection.close(conn);
			}

		}
	}

	public static ArrayList<EntidadeDominio> read(EntidadeDominio entidade, String condicao) {
		Connection conn = null;
		try {

			conn = DBConnection.getConnection();
			Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy hh.mm.ss").create();
			String json = gson.toJson(entidade);
			String[] data = ObjectForSql.jsonForList(json);
			String[] jsonAttributes = new String[data.length / 2];
			ArrayList<Method> listGetMethods = ObjectForListMethods.listGetMethods(entidade);
			ArrayList<Method> listSetMethods = ObjectForListMethods.listSetMethods(entidade);

			for (int i = 0, j = 0; i < data.length; i += 2, j++) {
				jsonAttributes[j] = data[i];
			}

			PreparedStatement pstm;
			
			if (entidade.getId() == 0) {
				pstm = conn.prepareStatement(ObjectForSql.select(entidade));
			} else {
				pstm = conn.prepareStatement(ObjectForSql.select(entidade, condicao));

				for (int i = 0; i < jsonAttributes.length; i++) {
					for (Method method : listGetMethods) {
						if (jsonAttributes[i].toLowerCase()
								.equals(method.getName().toLowerCase().substring(3, method.getName().length()))) {
							if (jsonAttributes[i].equals(condicao)) {
								pstm.setObject(1, method.invoke(entidade));
							}
						}
					}
				}
			}

			ResultSet rs = pstm.executeQuery();
			ArrayList<EntidadeDominio> entitiesList = new ArrayList<>();

			while (rs.next()) {
				EntidadeDominio entidadeDominio = entidade.getClass().newInstance();

				for (Method setMethod : listSetMethods) {
					Method getMethod = null;

					for (Method method : listGetMethods) {
						if (method.getName().toLowerCase().substring(3, method.getName().length())
								.equals(setMethod.getName().toLowerCase().substring(3, setMethod.getName().length()))) {
							getMethod = method;
						}
					}

					if (getMethod.getReturnType().getName().toLowerCase().equals("java.lang.string")) {
						setMethod.invoke(entidadeDominio, rs.getString(setMethod.getName().toLowerCase().substring(3, setMethod.getName().length())));
					} else if (getMethod.getReturnType().getName().toLowerCase().equals("int")) {
						setMethod.invoke(entidadeDominio, rs.getInt(setMethod.getName().toLowerCase().substring(3, setMethod.getName().length())));
					} else if (getMethod.getReturnType().getName().toLowerCase().equals("double")) {
						setMethod.invoke(entidadeDominio, rs.getDouble(setMethod.getName().toLowerCase().substring(3, setMethod.getName().length())));
					} else if (getMethod.getReturnType().getName().toLowerCase().equals("date")) {
						setMethod.invoke(entidadeDominio, rs.getDate(setMethod.getName().toLowerCase().substring(3, setMethod.getName().length())));
					}else if (getMethod.getReturnType().getName().toLowerCase().equals("float")) {
						setMethod.invoke(entidadeDominio, rs.getFloat(setMethod.getName().toLowerCase().substring(3, setMethod.getName().length())));
					}else if (getMethod.getReturnType().getName().toLowerCase().equals("boolean")) {
						setMethod.invoke(entidadeDominio, rs.getBoolean(setMethod.getName().toLowerCase().substring(3, setMethod.getName().length())));
					}

				}
				entitiesList.add(entidadeDominio);
			}

			return entitiesList;

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		} finally {
			DBConnection.close(conn);
		}

	}

	public static boolean update(EntidadeDominio entidade) {
		if (entidade == null) {
			return false;
		} else {
			Connection conn = null;
			try {

				conn = DBConnection.getConnection();
				conn.setAutoCommit(false);
				
				Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy hh.mm.ss").create();
				String json = gson.toJson(entidade);
				String[] data = ObjectForSql.jsonForList(json);
				String[] jsonAttributes = new String[data.length / 2];
				ArrayList<Method> listGetMethods = ObjectForListMethods.listGetMethods(entidade);

				for (int i = 0, j = 0; i < data.length; i += 2, j++) {
					jsonAttributes[j] = data[i];
				}

				PreparedStatement pstm = conn.prepareStatement(ObjectForSql.update(entidade, "id"));

				int indexValue = 1;
				for (int i = 0; i < jsonAttributes.length; i++) {
					for (Method method : listGetMethods) {
						if (jsonAttributes[i].toLowerCase()
								.equals(method.getName().toLowerCase().substring(3, method.getName().length()))) {
							if (!jsonAttributes[i].equals("id") && !jsonAttributes[i].equals("status")) {
								pstm.setObject(indexValue++, method.invoke(entidade));
							} else if (jsonAttributes[i].equals("id")) {
								pstm.setObject(jsonAttributes.length, method.invoke(entidade));
							} else if (jsonAttributes[i].equals("status")) {
								pstm.setObject(indexValue++, "Ativo");
							}
						}
					}
				}

				pstm.execute();
				conn.commit();
				return true;
			} catch (Exception e) {
				try {
					conn.rollback();
				} catch (SQLException e1) {	
					e1.printStackTrace();
				}
				e.printStackTrace();
				return false;
			} finally {
				DBConnection.close(conn);
			}

		}
	}

	public static boolean delete(EntidadeDominio entidade) {
		if (entidade == null) {
			return false;
		} else {
			Connection conn = null;
			try {

				conn = DBConnection.getConnection();
				conn.setAutoCommit(false);

				Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy hh.mm.ss").create();
				String json = gson.toJson(entidade);
				String[] data = ObjectForSql.jsonForList(json);
				String[] jsonAttributes = new String[data.length / 2];

				ArrayList<Method> listGetMethods = ObjectForListMethods.listGetMethods(entidade);

				for (int i = 0, j = 0; i < data.length; i += 2, j++) {
					jsonAttributes[j] = data[i];
				}

				PreparedStatement pstm = conn.prepareStatement(ObjectForSql.delete(entidade, "id", "status"));

				int indexValue = 1;
				for (int i = 0; i < jsonAttributes.length; i++) {
					for (Method method : listGetMethods) {
						if (jsonAttributes[i].toLowerCase()
								.equals(method.getName().toLowerCase().substring(3, method.getName().length()))) {
							if (jsonAttributes[i].equals("status")) {
								pstm.setObject(indexValue++, "Desativo");
							} else if (jsonAttributes[i].equals("id")) {
								pstm.setObject(2, method.invoke(entidade));
							}
						}
					}
				}

				pstm.execute();
				conn.commit();
				return true;
			} catch (Exception e) {
				try {
					conn.rollback();
				} catch (SQLException e1) {	
					e1.printStackTrace();
				}
				e.printStackTrace();
				return false;
			} finally {
				DBConnection.close(conn);
			}

		}
	}

}
