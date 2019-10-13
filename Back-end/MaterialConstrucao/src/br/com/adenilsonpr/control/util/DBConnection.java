package br.com.adenilsonpr.control.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	public static Connection getConnection() {

		try {
			String server = "localhost";
			String data = "db_materiais";
			String user = "postgres";
			String password = "Abc123456";

			Class.forName("org.postgresql.Driver");
			String path = "jdbc:postgresql://" + server + "/" + data;

			return DriverManager.getConnection(path, user, password);

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}

	}

	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				System.err.println(e.getMessage());

			}
		}
	}
}
