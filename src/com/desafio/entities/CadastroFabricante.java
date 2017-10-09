package com.desafio.entities;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import com.desafio.beans.Fabricante;
import com.desafio.dao.Dao;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class CadastroFabricante implements Serializable {

	private static final long serialVersionUID = 1L;
	private Fabricante fabricante;
	Dao dao;
	List<Object> parametros;

	public CadastroFabricante() throws SQLException {
		fabricante = new Fabricante();
		dao = new Dao();
		parametros = new ArrayList<Object>();
	}

	private int autoIncremento() throws NumberFormatException, SQLException {
		int id = 0;
		String sql = "SELECT CASE WHEN MAX(codigo) IS NULL THEN 1 ELSE MAX(codigo)+1 END as id FROM fabricante";
		ResultSet rs = dao.executarConsulta(sql);
		while (rs.next()) {
			System.out.println(rs.getString(1).toString());
			id = Integer.parseInt(rs.getString(1).toString());
		}
		return id;
	}

	public void cadastrar() throws SQLException {
		try {

			String sql = "call `cadastrar_fabricante`(?,?,?)";
			parametros.add(autoIncremento());
			parametros.add(fabricante.getNome());
			parametros.add(fabricante.getPaisOrigem());
			dao.executarManipulacao(sql, parametros);
			// System.out.println(parametros);

			System.out.println("Foi fabricante");

			FacesContext facesContext = FacesContext.getCurrentInstance();
			UIViewRoot uiViewRoot = facesContext.getViewRoot();
			HtmlInputText inputText = null;
			inputText = (HtmlInputText) uiViewRoot.findComponent("frm:nome");
			inputText.setValue("");
			inputText = (HtmlInputText) uiViewRoot.findComponent("frm:paisOrigem");
			inputText.setValue("");

			facesContext.addMessage(null, new FacesMessage("Fabricante cadastrado!"));

		} catch (SQLException e) {
			throw new SQLException();
		} finally {
			parametros.clear();
		}
	}

	public void editar(int codigo) throws SQLException {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		try {
			String sql = "call `cadastrar_fabricante`(?,?,?)";
			parametros.add(codigo);
			parametros.add(fabricante.getNome());
			parametros.add(fabricante.getPaisOrigem());
			dao.executarManipulacao(sql, parametros); //
			// System.out.println(parametros);
			// System.out.println(sql);

			facesContext.addMessage(null, new FacesMessage("Fabricante cadastrado!"));
		} finally {
			parametros.clear();
		}
	}

	public List<Fabricante> completaFabricante(String query) throws SQLException {
		ListarFabricante lf = new ListarFabricante();
		List<Fabricante> fabricantes = lf.getLista();
		List<Fabricante> sugestoes = new ArrayList<Fabricante>();
		for (Fabricante fabricante : fabricantes) {
			if (fabricante.getNome().startsWith(query)) {
				sugestoes.add(fabricante);
			}
		}
		return sugestoes;
	}

	public String excluirFabricante(Fabricante fabricante) throws SQLException{
		try {
			String sql = "call `excluir_fabricante`(?)";
			parametros.add(fabricante.getCodigo());
			dao.executarManipulacao(sql, parametros);
			return null;
		} catch (SQLException e) {
			return "Nao foi possivel excluir esse fabricante, certifique-se de que ele não possua nenhum modelo cadastrado";
		}
	}

	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}
}
