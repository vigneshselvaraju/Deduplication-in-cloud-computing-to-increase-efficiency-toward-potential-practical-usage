package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.UploadPojo;

import org.apache.commons.fileupload.FileItem;

import DB.DbQueries;

public class Compare extends HttpServlet {

	String content1;
	String value;
	List<String> filen = new ArrayList<String>();
	List<String> listn = new ArrayList<String>();
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session=request.getSession(true);
		String file=(String)session.getAttribute("file");
		System.out.println("----------Uploaded File name-------"+file);
		Double bytes=(Double)session.getAttribute("bytes");
		System.out.println("------------------size-------------"+bytes);
		String content=(String)session.getAttribute("content");
		DbQueries db=new DbQueries();
		List<UploadPojo> list=db.getValueDetails();

		for(UploadPojo pojo:list){
			String file1=pojo.getFile();
			Double bytes1=pojo.getBytes();
			System.out.println("--------------file    ------------"+file1);
			System.out.println("--------------bytes1    ------------"+bytes1);
			if(file.contains(".txt")){
				
				
				

					
						
				
					BufferedReader reader = new BufferedReader(new FileReader(
							"webapps/Deduplicationn/Files"
							+ File.separator + file));
			StringBuilder stringBuilder = new StringBuilder();
			char[] buffer = new char[10];
			while (reader.read(buffer) != -1) {
				stringBuilder.append(new String(buffer));
				buffer = new char[10];
			}
			reader.close();

			content1 = stringBuilder.toString();
			System.out.println("cont" + content1);

			System.out.println(list.get(0));
			
				System.out.println("-----------------hfskhhshsdhfhshfs**************88"+content1);
				if(bytes.equals(bytes1)&&(content1.equals(content))){
					System.out.println("**********file comparision**********");
				}
			
			}
			request.setAttribute("msg", "File Uploaded Successfully..!! ");
			
			RequestDispatcher rd = request.getRequestDispatcher("Upload1.jsp");
			rd.forward(request, response);
		}
				
				
				
				
				
				
				
				
				
				
				
			
		}
		}



