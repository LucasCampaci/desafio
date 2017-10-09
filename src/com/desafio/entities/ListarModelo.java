package com.desafio.entities;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import com.desafio.beans.Modelo;
import com.desafio.dao.Dao;

@ManagedBean
@ViewScoped
public class ListarModelo implements Serializable {
	private static final long serialVersionUID = 1L;
	Modelo modelo;
	Dao dao;
	List<Object> parametros;
	private List<String> valuesModelos;
	private List<Modelo> listaModelos;

	public ListarModelo() throws SQLException {
		modelo = new Modelo();
		dao = new Dao();
		listaModelos = new ArrayList<Modelo>();
	}

	public List<Modelo> getLista() throws SQLException, ParseException {
		ArrayList<Modelo> lista = new ArrayList<Modelo>();
		String sql = "SELECT m.codigo, f.nome as fabricante, m.nome as modelo, "
				+ "CASE WHEN m.flash < 1 THEN 'Não' ELSE 'Sim' END as flash, m.tela, "
				+ "COUNT(modelo_codigo) as funcionalidades, m.imagem, m.datalancamento, m.descricao "
				+ "FROM modelo m INNER JOIN fabricante f ON f.codigo = m.fabricante_codigo "
				+ "LEFT JOIN modelo_has_funcionalidade mhf ON mhf.modelo_codigo = m.codigo "
				+ "GROUP BY m.codigo, f.nome, m.nome, m.flash, m.tela, m.imagem, m.datalancamento, m.descricao ORDER BY m.nome, f.nome ASC";
		// os atributos foram agrupados por um alerta do mysql que nao consegui
		// tratar no jdbc
		ResultSet rs = dao.executarConsulta(sql);

		while (rs.next()) {
			modelo = new Modelo();
			modelo.setCodigo(Integer.parseInt(rs.getString(1).toString()));
			modelo.setFabricante(rs.getString(2).toString());
			modelo.setModelo(rs.getString(3).toString());
			modelo.setFlash(rs.getString(4).toString());
			modelo.setTela(Float.parseFloat(rs.getString(5).toString()));
			modelo.setFuncionalidades(buscarFuncionalidades(String.valueOf(modelo.getCodigo())));
			modelo.setUrlFoto(rs.getString(7).toString());
			modelo.setDataLancamento(rs.getDate(8));
			modelo.setDescricao(rs.getString(9).toString());
			
			lista.add(modelo);
		}
		return lista;
	}

	public void buscarComparacao() throws NumberFormatException, SQLException {
		String sql = "SELECT m.codigo, f.nome as fabricante, m.nome as modelo, "
				+ "CASE WHEN m.flash < 1 THEN 'Não' ELSE 'Sim' END as flash, m.tela, "
				+ "COUNT(modelo_codigo) as funcionalidades, m.imagem "
				+ "FROM modelo m INNER JOIN fabricante f ON f.codigo = m.fabricante_codigo "
				+ "LEFT JOIN modelo_has_funcionalidade mhf ON mhf.modelo_codigo = m.codigo " + "WHERE m.nome = '"
				+ modelo.getModelo() + "' "
				+ "GROUP BY m.codigo, f.nome, m.nome, m.flash, m.tela, m.imagem ORDER BY m.nome, f.nome ASC";
		// os atributos foram agrupados por um alerta do mysql que nao consegui
		// tratar no jdbc
		ResultSet rs = dao.executarConsulta(sql);
		//System.out.println(modelo.getModelo());

		while (rs.next()) {
			modelo = new Modelo();
			modelo.setCodigo(Integer.parseInt(rs.getString(1).toString()));
			modelo.setFabricante(rs.getString(2).toString());
			modelo.setModelo(rs.getString(3).toString());
			modelo.setFlash(rs.getString(4).toString());
			modelo.setTela(Float.parseFloat(rs.getString(5).toString()));
			modelo.setFuncionalidades(buscarFuncionalidades(String.valueOf(modelo.getCodigo())));
			modelo.setUrlFoto(rs.getString(7).toString());
			listaModelos.add(modelo);
		}
		//System.out.println(listaModelos);
	}
	
	public List<Modelo> completaModelo(String query) throws SQLException, ParseException {
		List<Modelo> modelos = getLista();
		List<Modelo> sugestoes = new ArrayList<Modelo>();
		for (Modelo modelo : modelos) {
			if (modelo.getModelo().startsWith(query)) {
				sugestoes.add(modelo);
			}
		}
		return sugestoes;
	}

	private String buscarFuncionalidades(String codigo) throws SQLException {
		String sql = "SELECT CASE WHEN f.nome IS NULL THEN '-' ELSE f.nome END FROM funcionalidade f INNER JOIN modelo_has_funcionalidade mhf "
				+ "ON mhf.funcionalidade_codigo = f.codigo WHERE mhf.modelo_codigo = " + codigo;
		ResultSet rs = dao.executarConsulta(sql);
		String string = "";
		StringBuilder strBuilder = new StringBuilder(string);

		while (rs.next()) {
			strBuilder.append(rs.getString(1).toString());
			strBuilder.append("  ");
		}

		if (strBuilder.length() < 1) {
			strBuilder.append(" -- ");
		}
		return strBuilder.toString();
		// System.out.println(codigo);
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public List<String> getValuesModelos() {
		return valuesModelos;
	}

	public void setValuesModelos(List<String> valuesModelos) {
		this.valuesModelos = valuesModelos;
	}
}
