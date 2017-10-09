package com.desafio.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	private static final String STR_DRIVER = "com.mysql.jdbc.Driver";
	private static final String USER = "root";
	private static final String PASSWORD = "";
	private static final String IP = "localhost";
	private static final String DATABASE = "mydb_desafio";
	private static final String STR_CON = "jdbc:mysql://" + IP + ":3306/" + DATABASE;

	@SuppressWarnings("finally")
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(STR_DRIVER);
			conn = DriverManager.getConnection(STR_CON, USER, PASSWORD);
			System.out.println("Obtendo conexao...");
		} catch (ClassNotFoundException e) {
			String errormsg = "Driver não encontrado...";
			System.out.println(errormsg);
			//throw new SQLException(errormsg, "\n" + e);
		} catch (SQLException e) {
			String errormsg = "Erro ao obter a conexão...";
			System.out.println(errormsg);
			//throw new SQLException(errormsg, "\n" + e);
		} finally {
			return conn;
		}
	}

	public static void closeAll(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
