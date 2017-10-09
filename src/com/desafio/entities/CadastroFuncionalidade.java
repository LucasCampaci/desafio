package com.desafio.entities;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import com.desafio.beans.Funcionalidade;
import com.desafio.dao.Dao;

@ManagedBean
@ViewScoped
public class CadastroFuncionalidade implements Serializable {
	private static final long serialVersionUID = 1L;
	private Funcionalidade funcionalidade;
	Dao dao;
	List<Object> parametros;

	public CadastroFuncionalidade() throws SQLException {
		funcionalidade = new Funcionalidade();
		dao = new Dao();
		parametros = new ArrayList<Object>();
	}

	private int autoIncremento() throws NumberFormatException, SQLException {
		int id = 0;
		String sql = "SELECT CASE WHEN MAX(codigo) < 1 THEN 1 ELSE MAX(codigo)+1 END as id FROM funcionalidade";
		ResultSet rs = dao.executarConsulta(sql);
		while (rs.next()) {
			System.out.println(rs.getString(1).toString());
			id = Integer.parseInt(rs.getString(1).toString());
		}
		return id;
	}

	public void cadastrar() throws SQLException {
		try {
			String sql = "call `cadastrar_funcionalidade`(?,?)";
			parametros.add(autoIncremento());
			parametros.add(funcionalidade.getNome());
			dao.executarManipulacao(sql, parametros);
			
			FacesContext facesContext = FacesContext.getCurrentInstance();
			UIViewRoot uiViewRoot = facesContext.getViewRoot();
			HtmlInputText inputText = null;
			inputText = (HtmlInputText) uiViewRoot.findComponent("frm:nome");
			inputText.setValue("");
			
			facesContext.addMessage(null, new FacesMessage("Funcionalidade cadastrada!"));

		} catch (SQLException e) {
			throw new SQLException();
		} finally {
			parametros.clear();
		}
	}
		
	public void editar(int codigo) {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		try {
			String sql = "call `cadastrar_funcionalidade`(?,?)";
			parametros.add(codigo); 
			parametros.add(funcionalidade.getNome());
			dao.executarManipulacao(sql, parametros); //
			System.out.println(parametros);
			System.out.println(sql);

			facesContext.addMessage(null, new FacesMessage("Funcionalidade salva!"));
		} catch (SQLException e) {

			facesContext.addMessage(null, new FacesMessage(e.getMessage()));

		} finally {
			parametros.clear();
		}
	}
	
	public String excluirFuncionalidade(Funcionalidade funcionalidade) {
		try {
			String sql = "call `excluir_funcionalidade`(?)";
			parametros.add(funcionalidade.getCodigo());
			dao.executarManipulacao(sql, parametros);
			return null;
		} catch (SQLException e) {
			return "Nao foi possivel excluir, certifique-se de que nenhum modelo possua essa funcionalidade";
		}
	}
	
	public Funcionalidade getFuncionalidade() {
		return funcionalidade;
	}

	public void setFuncionalidade(Funcionalidade f) {
		funcionalidade = f;
	}
}
