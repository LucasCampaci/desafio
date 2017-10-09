package com.desafio.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.desafio.beans.Fabricante;
import com.desafio.entities.CadastroFabricante;

/**
 * Servlet implementation class FabricanteServlet
 */
@WebServlet("/FabricanteServlet")
public class FabricanteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FabricanteServlet() {
		super();
		// TODO Auto-generated constructor stub

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		Fabricante fabricante = new Fabricante();
		CadastroFabricante cadastroFabricante = null;
		String msg = "";// <body onload=\"history.go(-1)\">";

		try {
			cadastroFabricante = new CadastroFabricante();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		fabricante.setCodigo(Integer.parseInt(request.getParameter("codigo")));
		try {
			msg = cadastroFabricante.excluirFabricante(fabricante);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (msg == null) {
			msg = "<script>window.location.assign('index.xhtml')</script>";
		} else {
			msg = "<div class=\"alert alert-danger\" role=\"alert\"> <button type=\"button\" onClick=\"window.location.assign('index.xhtml')\" "
					+ "class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">Voltar</span></button>"
					+ msg + "</div>";
		}

		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Servlet cadastrarDocenteServlet</title>");
		out.println("<link href=\"templates/css/bootstrap.min.css\" rel=\"stylesheet\" />");
		out.println("</head>");
		out.println("<body>");
		out.println(msg);
		out.println("</body>");
		out.println("</html>");
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
