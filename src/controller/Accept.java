package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.DBConnection;

import com.http.servlet.HttpsServlet;

public class Accept extends HttpsServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String email=request.getParameter("email");
		System.out.println("--------"+email);
		String status="accepted";
		DBConnection db=new DBConnection();
		boolean result=db.update(email, status);
		if(result){
			request.setAttribute("msg", "User Approved");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("UserView.jsp");
			requestDispatcher.forward(request, response);
		}
		
	}

}
