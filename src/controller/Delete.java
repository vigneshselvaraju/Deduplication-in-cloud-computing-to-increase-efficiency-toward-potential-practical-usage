package controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.DbQueries;

public class Delete extends HttpServlet {

	boolean status,status2;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String hash = request.getParameter("hash");
		String file=request.getParameter("file");
		String data=request.getParameter("ofile");
		String email = request.getParameter("email");
		int cou=0;
		String path = "webapps/Deduplicationn/Files/"+data;
		
		DbQueries dbQueries = new DbQueries();
		
		String count = dbQueries.count(hash);
		cou=Integer.parseInt(count);
		System.out.println("path"+data);
		
		if(cou == 1){
			status = dbQueries.Delete(hash, email);
			status2 = dbQueries.ODelete(hash, email);
			
			if(status && status2){
				 File files = new File(path);
				 files.delete();
				 
				 request.setAttribute("msg", "File Deleted Successfully");
				 RequestDispatcher rd = request.getRequestDispatcher("delete.jsp");
				 rd.forward(request, response);
				
			}
			
		}
		else {
			status = dbQueries.Delete(hash, email);
			request.setAttribute("msg", "File Deleted Successfully");
			RequestDispatcher rd = request.getRequestDispatcher("delete.jsp");
			rd.forward(request, response);
			
		}
		
	
	}

}
