package Sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.http.servlet.HttpsServlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class upload extends HttpsServlet {
	private final String UPLOAD_DIRECTORY = "webapps\\Deduplication\\Files\\UploadedFiles\\";
	String test;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		// PrintWriter out = response.getWriter();
		test = request.getParameter("test");
		System.out.println("----" + test);

	}

	protected void doPost(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		String file = "";
		HttpSession session = request.getSession(true);
		session.setAttribute("test", test);
		String uemail = (String) session.getAttribute("email");
		String value;
		System.out.println("----------" + test);
		// Part filePart = request.getPart("file");
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<String> list = new ArrayList<String>();
		List<String> filename = new ArrayList<String>();
		// String[]test;
		String prestring;
		String id = null;
		try {
			String filepath = "webapps\\Deduplication\\Files\\UploadedFiles\\"
					+ uemail;
			List<FileItem> items = upload.parseRequest(request);

			File fs = new File(UPLOAD_DIRECTORY + File.separator + uemail);
			if (!fs.isDirectory()) {
				fs.mkdir();
				System.out.println("created");
			} else {
				System.out.println("directory alrerady exists");
			}

			try {

				Connection connection = (Connection) getServletContext()
						.getAttribute("connection");

				System.out.println("connection success");

				Statement st = connection.createStatement();
				String query = "select * from owner2 where hash='" + test + "'";

				ResultSet rs = st.executeQuery(query);
				if (!rs.next()) {
					System.out.println("new");
					for (FileItem locFileItem : items) {

						if (locFileItem.isFormField()) {
							System.out.println("---"
									+ locFileItem.getFieldName());

							if (locFileItem.getFieldName().equals("file")) {
								file = locFileItem.getString();
							}
							if (locFileItem.getFieldName().equals("email")) {
								uemail = locFileItem.getString();
							}

						} else {
							System.out.println("--------ELSE--------");
							System.out.println("file : " + file);
							System.out.println("email : " + uemail);

							String path = filepath;
							file = locFileItem.getName();

							try {

								System.out.println("connection success");
								Statement sto = connection.createStatement();
								ResultSet trs = sto.executeQuery(query);
								System.out.println("" + trs);

								if (!trs.next()) {

									PreparedStatement pr = connection
											.prepareStatement("insert into owner2(file,email,hash)values(?,?,?)");
									pr.setString(1, file);
									pr.setString(2, uemail);
									pr.setString(3, test);

									int j = pr.executeUpdate();

									if (j > 0) {
										System.out.println("successful");


									} else {
										System.out.println("unsuccessful");

									}
								}
								rs = st
										.executeQuery("select * from owner2 where hash='"
												+ test + "'");
								if (rs.next()) {
									id = rs.getString("id");
								}
								if (!trs.next()) {

									PreparedStatement pr = connection.prepareStatement("insert into ownermapping(email,filename,refid,hash)values(?,?,?,?)");
									pr.setString(1, uemail);
									pr.setString(2, file);
									pr.setString(3, id);
									pr.setString(4, test);
									int j = pr.executeUpdate();
								}

							}


							catch (Exception e) {
								e.printStackTrace();
							}

							File f = new File(path);
							if (!f.exists()) {
								f.mkdirs();
							}
							String email = locFileItem.getName();
							System.out.println("file is " + email);
							File tosave = new File(path, email);
							locFileItem.write(tosave);

							// -------------------------------------------------------------------------------------------------
							request.setAttribute("msg",
									"File Uploaded Successfully");
							RequestDispatcher rd = request
									.getRequestDispatcher("Upload.jsp");
							rd.include(request, response);

						}
					}

				} else {
					rs = st.executeQuery("select * from owner2 where hash='"
							+ test + "'");
					if (rs.next()) {
						String file1path = rs.getString("file");
						String email1 = rs.getString("email");
						String id1 = rs.getString("id");
						System.out.println("--------------" + file1path);
						String ext = getFileExtension(file1path);
						System.out.println("" + ext);

						if (ext.equalsIgnoreCase(".txt")) {
							String copyyfile ="";

							for (FileItem locFileItem : items) {
								//filename.add(copyyfile)
								copyyfile = locFileItem.getName();
								filename.add(copyyfile);
								value = locFileItem.getString();
								list.add(value);

							}
							System.out.println("copy"+copyyfile);
							BufferedReader reader = new BufferedReader(
									new FileReader(
											"webapps\\Deduplication\\Files\\UploadedFiles\\"
													+ email1 + "\\" + file1path));
							StringBuilder stringBuilder = new StringBuilder();
							char[] buffer = new char[10];
							while (reader.read(buffer) != -1) {
								stringBuilder.append(new String(buffer));
								buffer = new char[10];
							}
							reader.close();

							String content = stringBuilder.toString();
							System.out.println("cont" + content);

							System.out.println(list.get(0));
							request.setAttribute("uploadedtxt", list.get(0));
							request.setAttribute("foundtxt", content);
							request.setAttribute("copyfile", filename.get(0));
							request.setAttribute("hash", test);
							request.setAttribute("id", id1);

							RequestDispatcher rd = request
									.getRequestDispatcher("Compare.jsp");
							rd.forward(request, response);

						}

						rs = st.executeQuery("select * from owner2 where email='"
										+ uemail + "' and hash='" + test + "'");
						if (rs.next()) {
							System.out.println("File already exit");
							request.setAttribute("msg", "File already exit");
							RequestDispatcher rd = request
									.getRequestDispatcher("Upload.jsp");
							rd.include(request, response);

						} else {
							rs = st
									.executeQuery("select * from owner2 where email!='"
											+ uemail
											+ "' and hash='"
											+ test
											+ "'");
							if (rs.next()) {
								id = rs.getString("id");
								file = rs.getString("file");

								rs = st
										.executeQuery("select * from ownermapping where email='"
												+ uemail
												+ "' and refid='"
												+ id
												+ "'");
								if (rs.next()) {
									System.out.println("error");
									request.setAttribute("msg", "error");
									RequestDispatcher rd = request
											.getRequestDispatcher("Upload.jsp");
									rd.include(request, response);

								} else {

									PreparedStatement pr = connection
											.prepareStatement("insert into ownermapping(email,filename,refid)values(?,?,?)");
									pr.setString(1, uemail);
									pr.setString(2, file);
									pr.setString(3, id);
									int j = pr.executeUpdate();
									request.setAttribute("msg",
											"mapping successfully");
									RequestDispatcher rd = request
											.getRequestDispatcher("Upload.jsp");
									rd.include(request, response);

								}
							}

							System.out.println("exist");
							RequestDispatcher rd = request
									.getRequestDispatcher("Upload.jsp");
							rd.include(request, response);

						}

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String getFileExtension(String name) {

		int lastIndexOf = name.lastIndexOf(".");
		if (lastIndexOf == -1) {
			return ""; // empty extension
		}
		return name.substring(lastIndexOf);
	}

}
