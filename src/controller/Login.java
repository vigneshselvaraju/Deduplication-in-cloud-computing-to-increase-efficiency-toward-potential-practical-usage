package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.http.servlet.HttpsServlet;

import DB.DbQueries;

public class Login extends HttpsServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		DbQueries dao = new DbQueries();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try {
			String email = request.getParameter("email");
			session.setAttribute("email", email);
			String password = request.getParameter("password");
			String result = dao.userlogin(email, password);
			if (!result.equalsIgnoreCase("no")) {
				System.out.println("--------------"+result);
				String temp[] = result.split(" ");
				if (temp[1].equalsIgnoreCase("pending")) {
					request.setAttribute("msg", "Your request is in pending...");
					RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
					rd.forward(request, response);
				}else if(temp[1].equalsIgnoreCase("declined")){
					request.setAttribute("msg", "Your request is rejected");
					RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
					rd.forward(request, response);
				} 
				request.setAttribute("msg", "Login successfull");
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("logview.jsp");
				requestDispatcher.forward(request, response);

			} else {

				request.setAttribute("msg", "Invalid Email Or Password");
				RequestDispatcher requestDispatcher = request
						.getRequestDispatcher("login.jsp");
				requestDispatcher.forward(request, response);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

