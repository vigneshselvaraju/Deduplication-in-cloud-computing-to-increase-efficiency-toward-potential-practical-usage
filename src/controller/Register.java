package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.http.servlet.HttpsServlet;

import DB.DbQueries;
import model.RegPojo;

public class Register extends HttpsServlet {


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		try {
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String phone = request.getParameter("phoneno");
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			String country = request.getParameter("country");
			
			
			DbQueries dao = new DbQueries();
			RegPojo user = new RegPojo(name, email, password, phone, city, state, country,"pending");
			System.out.println("username" + name + "email" + email + "-->"
					+ password + "--->" + phone);
			boolean result = dao.userregister(user);
			if (result) {
				request.setAttribute("msg", "Registration successfull");
				RequestDispatcher rd = request
						.getRequestDispatcher("index.jsp");
				rd.forward(request, response);
			} else {
				request.setAttribute("msg", "User Already Exists !");
				RequestDispatcher rd = request
						.getRequestDispatcher("register.jsp");
				rd.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
