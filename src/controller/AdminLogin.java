package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.http.servlet.HttpsServlet;

public class AdminLogin extends HttpsServlet {

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String username=request.getParameter("email");
		String password=request.getParameter("password");
		System.out.println("------"+username+"-------"+password);
		if(username.equals("admin@gmail.com")&&password.equals("admin")){
			
			request.setAttribute("msg", "Welcom Admin!!!");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("Adminview.jsp");
			requestDispatcher.forward(request, response);

			
			
			
		}else {
			request.setAttribute("msg", "Username or Password Incorrect");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("UserLogin.jsp");
			requestDispatcher.forward(request, response);
		}
	}

}
