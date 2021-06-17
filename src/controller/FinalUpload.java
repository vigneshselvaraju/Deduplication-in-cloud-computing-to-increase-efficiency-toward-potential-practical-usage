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
import DB.DbQueries;

public class FinalUpload extends HttpServlet {
	String email,file,mac,impo;
	List<FileItem>item;
	

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession httpSession = request.getSession();
		
		email = (String)httpSession.getAttribute("email");
		file = (String)httpSession.getAttribute("file");
		mac = (String)httpSession.getAttribute("mac");
		item = (List<FileItem>)httpSession.getAttribute("filepath");
		System.out.println("fgdfgdfgdf"+item);
		
		
		
		try {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			
				
				for (FileItem locFileItem : item) {
					impo = locFileItem.getName();
					
					
					 double bytes = impo.length();
					 System.out.println("--------size of this file is : " +bytes);
					
					
					String path = "webapps/Deduplicationn/Files";
					File fe = new File(path);
					if (!fe.exists()) {
						fe.mkdirs();
					}
					DbQueries dao = new DbQueries();
					UploadPojo user = new UploadPojo(email, impo, path, mac,"",bytes);
					System.out.println("email" + email + "-->" + path + "--->"
							+ mac);
					boolean result = dao.fileupload(user);

					String Id = dao.getId(mac);
					System.out.println("===========" + Id);
				
					UploadPojo user1 = new UploadPojo(email, impo, path, mac,
							Id,bytes);
					boolean result2 = dao.ownerUpload(user1);
					
					if (result && result2) {
						
					File tosave = new File(path + File.separator + impo);
					locFileItem.write(tosave);
					
					String path2 = "webapps/Deduplicationn/bind/"+impo;
					File file = new File(path2);
			        if(file.delete()){
			        	
			        	request.setAttribute("msg", "File uploaded successfully");
						RequestDispatcher rd = request.getRequestDispatcher("success.jsp");
						rd.forward(request, response);
						
			            System.out.println("File deleted");
			        }
			        
				}
				}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		System.out.println("ema :"+email+" file "+file+" mac "+mac);
	}

}
