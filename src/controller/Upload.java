package controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.UploadPojo;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.codehaus.jettison.json.JSONObject;

import com.http.servlet.HttpsServlet;

import rest.ServerCall;

import DB.DbQueries;
import Sample.MD5;

public class Upload extends HttpsServlet {
	static String hash = "";
	String file ="";

	JSONObject jsonObject = new JSONObject();

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		HttpSession httpSession = request.getSession();

		String email = (String) httpSession.getAttribute("email");

		System.out.println("=============json============" + jsonObject);
		
		String impo;
		String status = "pending";
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List<FileItem> items = upload.parseRequest(request);
			for (FileItem locFileItem : items) {
				impo = locFileItem.getName();
				httpSession.setAttribute("file", impo);
				
				 double bytes = impo.length();
				System.out.println("--------size of this file is : " +bytes);
				
				
				String path = "webapps/Deduplicationn/Files";
				File fe = new File(path);
				if (!fe.exists()) {
					fe.mkdirs();
				}
				try {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("hash", hash);

					ServerCall serverCall = new ServerCall();
					JSONObject sendHashDetails = serverCall
							.sendHash(jsonObject);
					String res = (String) sendHashDetails.get("response");
					String res1 = (String) sendHashDetails.get("filepath");
					System.out.println("=================" + res);
					System.out.println("=================" + res1);
					 
					if(!res.equalsIgnoreCase("no")){
						DbQueries dao = new DbQueries();
						String Id = dao.getId(hash);
						
						
						if (!Id.equalsIgnoreCase("no")) {

							UploadPojo user1 = new UploadPojo(email, impo, res1, hash,
									Id, bytes);
							boolean result2 = dao.cloudUpload(user1);
							System.out.println("email" + email + "-->" + path + "--->"
							+ hash+"-------->"+res1);
						}
					}else{
					
				DbQueries dao = new DbQueries();
				String Id = dao.getId(hash);
				if (Id.equalsIgnoreCase("no")) {

					UploadPojo user = new UploadPojo(email, impo, path, hash,"",bytes);
					System.out.println("email" + email + "-->" + path + "--->"
							+ hash);
					boolean result = dao.fileupload(user);

					Id = dao.getId(hash);
					System.out.println("===========" + Id);
				
					UploadPojo user1 = new UploadPojo(email, impo, path, hash,
							Id,bytes);
					boolean result2 = dao.ownerUpload(user1);
					if (result && result2) {
						jsonObject.put("error", 0);
						File tosave = new File(path + File.separator + impo);
						locFileItem.write(tosave);
					
					} else {
						jsonObject.put("error", 1);
					}
				
				} else {
					UploadPojo user = new UploadPojo(email, impo, path, hash,
							"",bytes);
					System.out.println("email" + email + "-->" + path + "--->"
							+ hash);

					System.out.println("===========" + Id);
					UploadPojo user1 = new UploadPojo(email, impo, path, hash,
							Id,bytes);
					boolean result2 = dao.ownerUpload(user1);
					if (result2) {
						jsonObject.put("error", 0);
					} else {
						jsonObject.put("error", 1);
					}

				}
				out.print(jsonObject);
					}
				}catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
				
				}
			}		
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		HttpSession httpSession = request.getSession();

		String email = (String) httpSession.getAttribute("email");

		hash = request.getParameter("username");
		file = request.getParameter("filename");
		
		System.out.println("hash" + hash + "email" + file);
		try {
			DbQueries dao = new DbQueries();
			String hashcheck = dao.getCheck(email, hash);
			System.out.println("=====hashcheck======" + hashcheck);

			if (hashcheck.equalsIgnoreCase("no")) {
				request.setAttribute("msg", "File Uploaded Successfuully!!!");
				//out.print("no");
				
			} else {
				request.setAttribute("msg", "File Already Exists!!!");
				out.print("File exists");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
}
