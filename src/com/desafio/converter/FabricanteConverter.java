package com.desafio.converter;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.desafio.beans.Fabricante;
import com.desafio.dao.Dao;

@FacesConverter(value="fabricanteConverter")
//@FacesConverter(forClass = Fabricante.class)
public class FabricanteConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String string) {
		
		try {
			Dao dao = new Dao();
			Fabricante fabricante = null;

			String sql = "SELECT * FROM fabricante WHERE nome LIKE '" + string + "%'";
			ResultSet rs = dao.executarConsulta(sql);
			while (rs.next()) {
				fabricante = new Fabricante();
				fabricante.setCodigo(Integer.parseInt(rs.getString(1).toString()));
				fabricante.setNome(rs.getString(2).toString());
				fabricante.setPaisOrigem(rs.getString(3).toString());
			}
			return fabricante;
		} catch (SQLException e) {
		 return "Nenhum";
		}
	}


	@Override
	public String getAsString(FacesContext context, UIComponent component, Object string) {
		if (string != null) {
            return ((Fabricante)string).getNome().toString(); 
        }
		else{
        return null;}
	}
}
