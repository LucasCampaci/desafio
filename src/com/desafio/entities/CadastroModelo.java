package com.desafio.entities;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.desafio.beans.Fabricante;
import com.desafio.beans.Modelo;
import com.desafio.dao.Dao;

@ManagedBean
@ViewScoped
public class CadastroModelo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Modelo modelo;
	private Map<String, String> funcs;
	private List<String> valuesFuncs;
	Dao dao;
	Fabricante fabricante = null;
	List<Object> parametros;

	public CadastroModelo() throws SQLException {
		modelo = new Modelo();
		valuesFuncs = new ArrayList<String>();
		dao = new Dao();
		parametros = new ArrayList<Object>();
		fabricante = new Fabricante();
	}

	private int autoIncremento() throws NumberFormatException, SQLException {
		int id = 0;
		String sql = "SELECT CASE WHEN MAX(codigo) IS NULL THEN 1 ELSE MAX(codigo)+1 END as id FROM modelo";
		ResultSet rs = dao.executarConsulta(sql);
		while (rs.next()) {
			id = Integer.parseInt(rs.getString(1).toString());
		}
		return id;
	}

	public void cadastrar() throws SQLException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			String sql = "call `cadastrar_modelo`(?,?,?,?,?,?,?,?)";
			modelo.setCodigo(autoIncremento());
			parametros.add(modelo.getCodigo());
			parametros.add(modelo.getFabricante());
			parametros.add(modelo.getModelo());
			parametros.add(modelo.getFlash());
			parametros.add(modelo.getUrlFoto());
			parametros.add(modelo.getTela());
			parametros.add(modelo.getDataLancamento());
			parametros.add(modelo.getDescricao());
			dao.executarManipulacao(sql, parametros);
			parametros.clear();

			for (String string : valuesFuncs) {
				sql = "call `cadastrar_funcionalidade_modelo`(?,?)";
				parametros.add(modelo.getCodigo());
				parametros.add(string);
				dao.executarManipulacao(sql, parametros);
				parametros.clear();
			}

			facesContext.addMessage(null, new FacesMessage("Modelo Cadastrado!"));

		} catch (SQLException e) {
			facesContext.addMessage(null,
					new FacesMessage("Erro ao cadastrar, verifique se as informações estão corretas!"));
		} finally {
			parametros.clear();
		}
	}
	
	public void editar(int codigo) throws SQLException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		System.out.println("Editando.." + codigo);
		try {
			String sql = "call `cadastrar_modelo`(?,?,?,?,?,?,?,?)";
			modelo.setCodigo(codigo);
			parametros.add(modelo.getCodigo());
			parametros.add(modelo.getFabricante());
			parametros.add(modelo.getModelo());
			parametros.add(modelo.getFlash());
			parametros.add(modelo.getUrlFoto());
			parametros.add(modelo.getTela());
			parametros.add(modelo.getDataLancamento());
			parametros.add(modelo.getDescricao());
			dao.executarManipulacao(sql, parametros);
			
			parametros.clear();

			for (String string : valuesFuncs) {
				sql = "call `cadastrar_funcionalidade_modelo`(?,?)";
				parametros.add(modelo.getCodigo());
				parametros.add(string);
				dao.executarManipulacao(sql, parametros);
				parametros.clear();
			}
			facesContext.addMessage(null, new FacesMessage("Modelo Atualizado!"));

		} catch (SQLException e) { // throw new SQLException();
			facesContext.addMessage(null,
					new FacesMessage("Erro ao atualizar, verifique se as informações estão corretas!"));
		} finally {
			parametros.clear();
		}
	}

	public Map<String, String> getFuncs() throws SQLException {
		funcs = new HashMap<String, String>();
		String sql = "SELECT * FROM funcionalidade";
		ResultSet rs = dao.executarConsulta(sql);

		while (rs.next()) {
			funcs.put(rs.getString(2).toString(), rs.getString(1).toString());
		}
		return funcs;
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

	public void setPais() throws SQLException {
		String sql = "SELECT pais_de_origem FROM fabricante WHERE nome = '" + modelo.getFabricante() + "'";
		ResultSet rs = dao.executarConsulta(sql);

		while (rs.next()) {

			fabricante.setPaisOrigem(rs.getString(1).toString());
			System.out.println(fabricante.getPaisOrigem());
		}
	}

	public Fabricante getFabricante() {
		return fabricante;
	}

	public String excluirModelo(Modelo modelo) {
		try {
			String sql = "call `excluir_modelo`(?)";
			parametros.add(modelo.getCodigo());
			dao.executarManipulacao(sql, parametros);
			return null;
		} catch (SQLException e) {
			return "Não foi possivel excluir esse modelo, se o erro persisteir, contate o administrador";
		}
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public void setFuncs(Map<String, String> funcs) {
		this.funcs = funcs;
	}

	public List<String> getValuesFuncs() {
		return valuesFuncs;
	}

	public void setValuesFuncs(List<String> valuesFuncs) {
		this.valuesFuncs = valuesFuncs;
	}
}
