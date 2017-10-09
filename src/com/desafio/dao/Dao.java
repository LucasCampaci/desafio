/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.desafio.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.sql.CallableStatement;
import java.sql.ResultSet;

public class Dao {

	Connection connection;
	CallableStatement callableStatement;

	public Dao() throws SQLException {
		this.connection = ConnectionFactory.getConnection();
	}

	public void executarManipulacao(String sql, List<Object> parametros) throws SQLException {
		int cont = 1;
		//System.out.println("Executando manipulação...");
		callableStatement = this.connection.prepareCall(sql);
		for (Object objeto : parametros) {
			callableStatement.setObject(cont, objeto);
			cont++;
		}
		callableStatement.executeQuery();

	}

	public ResultSet executarConsulta(String sql) throws SQLException {
		ResultSet rs = null;
		//System.out.println("Executando consulta...");
		callableStatement = this.connection.prepareCall(sql);
		rs = callableStatement.executeQuery();
		return rs;
	}
}
