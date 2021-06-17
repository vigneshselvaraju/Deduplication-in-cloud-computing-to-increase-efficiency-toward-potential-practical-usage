package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.DbQueries;

public class deduplicate extends HttpServlet {
	String hash = "";

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		hash = request.getParameter("mac");
		System.out.println("mac "+hash);
		
		DbQueries dao = new DbQueries();
		String Id = dao.getId(hash);
		System.out.println(Id);
		
		if(Id.equalsIgnoreCase("no")){
			request.setAttribute("msg", "No duplicate file found on this server check with other server");
			request.setAttribute("mac", Id);
			request.setAttribute("check", "check");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("globalserver.jsp");
			requestDispatcher.forward(request, response);
		}
		
	
		else {
			request.setAttribute("msg", "Duplicate file found on this server map with available file");
			request.setAttribute("mac", Id);
			request.setAttribute("check", "map");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("globalserver.jsp");
			requestDispatcher.forward(request, response);
		}
		
		

		
		
		
	}


}
