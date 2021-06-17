package controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.codehaus.jettison.json.JSONObject;

import DB.DbQueries;

public class BindFile extends HttpServlet {
	
    String hash = "";
	String file ="";
	JSONObject jsonObject = new JSONObject();
	String impo;
	String status = "pending";
	String[]ext;
	int maxFileSize = 50000 * 1024;
	int maxMemSize = 50000 * 1024;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		HttpSession httpSession = request.getSession();

		String email = (String) httpSession.getAttribute("email");
		
		httpSession.setAttribute("file",file);
		httpSession.setAttribute("mac",hash);
		
		  DiskFileItemFactory factory = new DiskFileItemFactory();
	      factory.setSizeThreshold(maxMemSize);
	      factory.setRepository(new File("c:\\temp"));
	      ServletFileUpload upload = new ServletFileUpload(factory);
	      upload.setSizeMax( maxFileSize );
	      
		try {
			List<FileItem> items = upload.parseRequest(request);
			
		       
			System.out.println("f"+items);
			httpSession.setAttribute("filepath",items);
			System.out.println("df"+items);
			for (FileItem locFileItem : items) {
				impo = locFileItem.getName();
				httpSession.setAttribute("file", impo);
				
				 double bytes = impo.length();
				System.out.println("--------size of this file is : " +bytes);
				System.out.println("-------- : " +impo);
				ext = impo.split("\\.");
				System.out.println("-------- : " +ext[1]);
				
				String path = "webapps/Deduplicationn/bind";
				File fe = new File(path);
				if (!fe.exists()) {
					fe.mkdirs();
				}
				
				
				
				File tosave = new File(path + File.separator + impo);
				locFileItem.write(tosave);
				
				if(ext[1].equalsIgnoreCase("txt")){
					jsonObject.put("error", 0);
				}
				else if (ext[1].equalsIgnoreCase("pdf")) {
					jsonObject.put("error", 2);
					
				}
				else if (ext[1].equalsIgnoreCase("jpg") || ext[1].equalsIgnoreCase("jpeg") || ext[1].equalsIgnoreCase("png")) {
					jsonObject.put("error", 3);
					
				}
				else if (ext[1].equalsIgnoreCase("mp4") || ext[1].equalsIgnoreCase("mpeg")) {
					jsonObject.put("error", 4);
					
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		out.print(jsonObject);
			

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
				//request.setAttribute("msg", "File Uploaded Successfuully!!!");
				// out.print("no");

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
