package controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.http.servlet.HttpsServlet;

public class Download extends HttpsServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<String> downfilelist=new ArrayList<String>();
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
try {
			
			HttpSession httpSession=request.getSession(false);
			//String email=(String)httpSession.getAttribute("email");
			String email=(String) httpSession.getAttribute("email");
			String file=request.getParameter("file");
			String data=request.getParameter("ofile");
			
			System.out.println("******************"+email);
			System.out.println("--------------->"+file);
			String file_enc = "webapps/Deduplicationn/Files/"+file;
			
			response.setContentType("APPLICATION/OCTET-STREAM"); 
			
			
			response.setHeader("Content-Disposition","attachment; filename=\"" + data + "\"");   
			  
			FileInputStream fileInputStream = new FileInputStream(file_enc);  
			            
			int i;   
			while ((i=fileInputStream.read()) != -1) {  
			out.write(i);   
			}   
			fileInputStream.close();   
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}	


	}
}
