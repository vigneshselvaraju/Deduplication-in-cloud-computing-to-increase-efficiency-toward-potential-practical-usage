package controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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

import rest.ServerCall;

import DB.DbQueries;
import Sample.MD5;

public class Upload1 extends HttpServlet {
	String hash = "";

	JSONObject jsonObject = new JSONObject();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession hs=request.getSession(false);
		String email = (String) hs.getAttribute("email");
		String image="";
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List<FileItem> items = upload.parseRequest(request);
			for (FileItem locFileItem : items) {
				if (locFileItem.isFormField()) {
					System.out.println("---" + locFileItem.getFieldName());
				}
			}
		} catch (Exception e) {
			
		}
		out.close();
	}

}
