package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.UploadPojo;

import org.codehaus.jettison.json.JSONObject;

import DB.DbQueries;

import rest.ServerCall;

public class GlobalServer extends HttpServlet {
	String mac, email, file;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession httpSession = request.getSession();

		email = (String) httpSession.getAttribute("email");
		file = (String) httpSession.getAttribute("file");
		mac = (String) httpSession.getAttribute("mac");
		System.out.println("fgfdg" + mac);
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("hash", mac);

			ServerCall serverCall = new ServerCall();
			JSONObject sendHashDetails = serverCall.sendHash(jsonObject);
			String res = (String) sendHashDetails.get("response");
			String res1 = (String) sendHashDetails.get("filepath");

			if (res.equalsIgnoreCase("no")) {
				request
						.setAttribute("msg",
								"No duplicate file found on any server please upload the file");
				request.setAttribute("mac", mac);
				request.setAttribute("check", "gffgf");
				RequestDispatcher requestDispatcher = request
						.getRequestDispatcher("globalserver.jsp");
				requestDispatcher.forward(request, response);
			} else {
				System.out.println("=================" + res);
				System.out.println("=================" + res1);
				
				
				httpSession.setAttribute("spath", res1);
				httpSession.setAttribute("sname", res);
				
				request.setAttribute("msg", "Duplicate file found on other server");
				request.setAttribute("check", "ownermap");
				RequestDispatcher rd = request.getRequestDispatcher("globalserver.jsp");
				rd.forward(request, response);
				
				/*DbQueries dao = new DbQueries();
				UploadPojo user = new UploadPojo(email, res, res1, mac,"",0.0);
				
				boolean result = dao.fileupload(user);

				String Id = dao.getId(mac);
				System.out.println("===========" + Id);
			
				UploadPojo user1 = new UploadPojo(email, file, res1, mac,
						Id,0.0);
				boolean result2 = dao.ownerUpload(user1);
				
				if (result && result2) {
					request.setAttribute("msg", "Your request is rejected");
					RequestDispatcher rd = request.getRequestDispatcher("success.jsp");
					rd.forward(request, response);
						
				}*/

				
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
