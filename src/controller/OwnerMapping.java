package controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DB.DbQueries;

import model.UploadPojo;

public class OwnerMapping extends HttpServlet {
	String email,file,mac;
	String path;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		

		HttpSession httpSession = request.getSession();

		email = (String) httpSession.getAttribute("email");
		file = (String) httpSession.getAttribute("file");
		mac = (String) httpSession.getAttribute("mac");
		
		
		System.out.println("sads"+email+file+mac);
		
		DbQueries dao = new DbQueries();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String Id = dao.getId(mac);
		path = dao.path(mac);
		


		System.out.println("===========" + Id);
		UploadPojo user1 = new UploadPojo(email, file, path, mac, Id, 0.0);
		boolean result2 = dao.ownerUpload(user1);
		
		String path2 = "webapps/Deduplicationn/bind/"+file;
		File file = new File(path2);
        
		
		if(result2 && file.delete()){
			request.setAttribute("msg", "File successfully mapped with duplicate file");
			RequestDispatcher rd = request.getRequestDispatcher("success.jsp");
			rd.forward(request, response);
		}

	}

}
