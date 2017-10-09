package com.desafio.entities;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import com.desafio.beans.Fabricante;
import com.desafio.dao.Dao;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class ListarFabricante implements Serializable {

	private static final long serialVersionUID = 1L;
	private Fabricante fabricante;
	Dao dao;
	List<Object> parametros;

	public ListarFabricante() throws SQLException {
		fabricante = new Fabricante();
		dao = new Dao();
		parametros = new ArrayList<Object>();
	}

	public List<Fabricante> getLista() throws SQLException {
		ArrayList<Fabricante> lista = new ArrayList<Fabricante>();
		String sql = "SELECT * FROM fabricante";
		ResultSet rs = dao.executarConsulta(sql);

		while (rs.next()) {
			fabricante = new Fabricante();
			fabricante.setCodigo(Integer.parseInt(rs.getString(1).toString()));
			fabricante.setNome(rs.getString(2).toString());
			fabricante.setPaisOrigem(rs.getString(3).toString());
			lista.add(fabricante);
		}
		return lista;
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

	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}
}
