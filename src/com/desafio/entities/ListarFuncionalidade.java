package com.desafio.entities;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import com.desafio.beans.Funcionalidade;
import com.desafio.dao.Dao;

@ManagedBean
@ViewScoped
public class ListarFuncionalidade implements Serializable {
	private static final long serialVersionUID = 1L;
	private Funcionalidade funcionalidade;
	Dao dao;
	List<Object> parametros;

	public ListarFuncionalidade() throws SQLException {
		funcionalidade = new Funcionalidade();
		dao = new Dao();
		parametros = new ArrayList<Object>();
	}

		
	public List<Funcionalidade> getLista() throws SQLException{
		ArrayList<Funcionalidade> lista = new ArrayList<Funcionalidade>();
		String sql ="SELECT * FROM funcionalidade";
		ResultSet rs = dao.executarConsulta(sql);
		
		while(rs.next()){
			funcionalidade = new Funcionalidade();
			funcionalidade.setCodigo(Integer.parseInt(rs.getString(1).toString()));
			funcionalidade.setNome(rs.getString(2).toString());
			lista.add(funcionalidade);
		}
		return lista;
	}
	
	public Funcionalidade getFuncionalidade() {
		return funcionalidade;
	}

	public void setFuncionalidade(Funcionalidade f) {
		funcionalidade = f;
	}
}
