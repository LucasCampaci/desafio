package com.desafio.converter;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import com.desafio.beans.Modelo;
import com.desafio.dao.Dao;

@FacesConverter(value = "modeloConverter")
public class ModeloConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String string) {
		
		try {
			Dao dao = new Dao();
			Modelo modelo = null;

			String sql = "SELECT * FROM modelo WHERE nome LIKE '" + string + "%'";
			ResultSet rs = dao.executarConsulta(sql);
			while (rs.next()) {
				modelo = new Modelo();
				modelo.setCodigo(Integer.parseInt(rs.getString(1).toString()));
				modelo.setModelo(rs.getString(6).toString());
			}
			return modelo;
		} catch (SQLException e) {
		 return "Nenhum";
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object string) {
		if (string != null) {
            return ((Modelo)string).getModelo().toString(); 
        }
		else{
        return null;}
	}

}
