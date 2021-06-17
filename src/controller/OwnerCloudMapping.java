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

import model.UploadPojo;
import DB.DbQueries;

public class OwnerCloudMapping extends HttpServlet {
String email,file,mac,sname,spath;
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession httpSession = request.getSession();

		email = (String) httpSession.getAttribute("email");
		file = (String) httpSession.getAttribute("file");
		mac = (String) httpSession.getAttribute("mac");
		sname = (String) httpSession.getAttribute("sname");
		spath = (String) httpSession.getAttribute("spath");


		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		DbQueries dao = new DbQueries();
		UploadPojo user = new UploadPojo(email, sname, spath, mac,"",0.0);
		
		boolean result = dao.fileupload(user);

		String Id = dao.getId(mac);
		System.out.println("===========" + Id+sname+spath);
	
		UploadPojo user1 = new UploadPojo(email, file, spath, mac,
				Id,0.0);
		boolean result2 = dao.ownerUpload(user1);
		
		String path2 = "webapps/Deduplicationn/bind/"+file;
		File file = new File(path2);
		
		if (result && result2) {
			request.setAttribute("msg", "File mapped with other server");
			RequestDispatcher rd = request.getRequestDispatcher("success.jsp");
			rd.forward(request, response);
				
		}
	}

}
