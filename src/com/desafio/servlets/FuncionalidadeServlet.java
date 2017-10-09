package com.desafio.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.desafio.beans.Funcionalidade;
import com.desafio.entities.CadastroFuncionalidade;

/**
 * Servlet implementation class FabricanteServlet
 */
@WebServlet("/FuncionalidadeServlet")
public class FuncionalidadeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FuncionalidadeServlet() {
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
		Funcionalidade funcionalidade = new Funcionalidade();
		CadastroFuncionalidade cadastroFuncionalidade = null;
		try {
			cadastroFuncionalidade = new CadastroFuncionalidade();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String msg = "";

		
		funcionalidade.setCodigo(Integer.parseInt(request.getParameter("codigo")));
		msg = cadastroFuncionalidade.excluirFuncionalidade(funcionalidade);
		if (msg == null) {
			msg = "<script>window.location.assign('funcionalidades.xhtml')</script>";
		} else {
			msg = "<div class=\"alert alert-danger\" role=\"alert\"> <button type=\"button\" onClick=\"window.location.assign('funcionalidades.xhtml')\" "
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
