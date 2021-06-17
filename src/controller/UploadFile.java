package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.codehaus.jettison.json.JSONObject;

import rest.ServerCall;
import DB.DbQueries;
import Sample.MD5Sam;
import Sample.newser;

public class UploadFile extends HttpServlet {
	String hash = "";
	String impo;
	JSONObject jsonObject = new JSONObject();
	DbQueries dao = new DbQueries();
	String path = "webapps/Deduplicationn/Files";
	double bytes;
	String content;
	String value;
	List<String> filen = new ArrayList<String>();
	List<String> listn = new ArrayList<String>();
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		HttpSession httpSession = request.getSession();

		String email = (String) httpSession.getAttribute("email");

		System.out.println("=============json============" + jsonObject);

		String status = "pending";
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> items=null;
		try {
		 items = upload.parseRequest(request);
			for (FileItem locFileItem : items) {
				impo = locFileItem.getName();
				httpSession.setAttribute("file", impo);

				bytes = impo.length();
				System.out.println("--------size of this file is : " + bytes);

				MD5Sam m = new MD5Sam();
				hash = m.getMd5(locFileItem.getName());

				System.out.println("----------hash value-----------" + hash);

				File fe = new File(path);
				if (!fe.exists()) {
					fe.mkdirs();
				}

				// Initializing counters

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("hash", hash);

				ServerCall serverCall = new ServerCall();
				JSONObject sendHashDetails = serverCall.sendHash(jsonObject);
				String res = (String) sendHashDetails.get("response");
				String res1 = (String) sendHashDetails.get("filepath");
				System.out.println("=================" + res);
				System.out.println("=================" + res1);

				if (!res.equalsIgnoreCase("no")) {

					String Id = dao.getId(hash);

					if (!Id.equalsIgnoreCase("no")) {

						UploadPojo user1 = new UploadPojo(email, impo, res1,
								hash, Id, bytes);
						boolean result2 = dao.cloudUpload(user1);
						System.out.println("email" + email + "-->" + path
								+ "--->" + hash + "-------->" + res1);
					}
				} else {

					DbQueries dao = new DbQueries();
					String Id = dao.getId(hash);
					if (Id.equalsIgnoreCase("no")) {

						UploadPojo user = new UploadPojo(email, impo, path,
								hash, "", bytes);
						System.out.println("email" + email + "-->" + path
								+ "--->" + hash);
						boolean result = dao.fileupload(user);

						Id = dao.getId(hash);
						System.out.println("===========" + Id);

						UploadPojo user1 = new UploadPojo(email, impo, path,
								hash, Id, bytes);
						boolean result2 = dao.ownerUpload(user1);
						if (result && result2) {
							jsonObject.put("error", 0);
							File tosave = new File(path + File.separator + impo);
							locFileItem.write(tosave);

						} else {
							jsonObject.put("error", 1);
						}

					} else {
						UploadPojo user = new UploadPojo(email, impo, path,
								hash, "", bytes);
						System.out.println("email" + email + "-->" + path
								+ "--->" + hash);

						System.out.println("===========" + Id);
						UploadPojo user1 = new UploadPojo(email, impo, path,
								hash, Id, bytes);
						boolean result2 = dao.ownerUpload(user1);
						if (result2) {
							jsonObject.put("error", 0);
						} else {
							jsonObject.put("error", 1);
						}

					}

				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		// String email = (String) httpSession.getAttribute("email");

		System.out.println("hash" + hash + "email" + email);

		DbQueries dao = new DbQueries();
		String hashcheck = dao.getCheck(email, hash);
		System.out.println("=====hashcheck======" + hashcheck);
		DbQueries db = new DbQueries();
		List<UploadPojo> list = db.getValueDetails();
		for (UploadPojo pojo : list) {
			String path = pojo.getPath();
			String filename = pojo.getFile();
			System.out.println("----------------" + filename);
			if(filename.contains(impo)){
			if (filename.contains(".txt")) {
				String copyyfile ="";

				for (FileItem locFileItem : items) {
					//filename.add(copyyfile)
					copyyfile = locFileItem.getName();
					filen.add(copyyfile);
					value = locFileItem.getString();
					listn.add(value);

				}
			BufferedReader reader = new BufferedReader(new FileReader(
							"webapps/Deduplicationn/Files"
							+ File.separator + filename));
			StringBuilder stringBuilder = new StringBuilder();
			char[] buffer = new char[10];
			while (reader.read(buffer) != -1) {
				stringBuilder.append(new String(buffer));
				buffer = new char[10];
			}
			reader.close();

			content = stringBuilder.toString();
			System.out.println("cont" + content);

			System.out.println(list.get(0));
			request.setAttribute("uploadedtxt", list.get(0));
			request.setAttribute("foundtxt", content);
			httpSession.setAttribute("foundtxt", content);
			httpSession.setAttribute("upload", list.get(0));
				//System.out.println("-----------------hfskhhshsdhfhshfs**************88"+content);
			}
		}
		}
		if (hashcheck.equalsIgnoreCase("no")) {

		} else {

			// out.print("File exists");

		}

		request.setAttribute("msg", "File Uploaded Successfully..!! ");

		RequestDispatcher rd = request.getRequestDispatcher("Upload1.jsp");
		rd.forward(request, response);
		httpSession.setAttribute("file", impo);
		httpSession.setAttribute("bytes", bytes);
		

	}

}