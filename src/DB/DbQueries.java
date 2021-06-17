package DB;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.DbConnection;
import model.DownloadPojo;
import model.RegPojo;
import model.UploadPojo;

public class DbQueries{

	public String userlogin(String email, String password) {
		String result = "no";
		try {
			Connection connection = DbConnection.getConnection();
			String query = "select * from register where email=? and password=?";
			PreparedStatement pr = connection.prepareStatement(query);
			pr.setString(1, email);
			pr.setString(2, password);
			ResultSet rs = pr.executeQuery();
			if (rs.next()) {
				result = rs.getString("email")+" "+rs.getString("status");
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public String getId(String hash) {
		String result = "no";
		String path = "";
		try {
			Connection connection = DbConnection.getConnection();
			String query = "select * from upload where hash=?";
			PreparedStatement pr = connection.prepareStatement(query);
			pr.setString(1, hash);
			ResultSet rs = pr.executeQuery();
			if (rs.next()) {
				result = rs.getString("Id");
				path = rs.getString("path");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public String count(String hash) {
		String result = "no";
		
		try {
			Connection connection = DbConnection.getConnection();
			String query = "SELECT COUNT(*) as count FROM mapping where hash=?";
			PreparedStatement pr = connection.prepareStatement(query);
			pr.setString(1, hash);
			ResultSet rs = pr.executeQuery();
			if (rs.next()) {
				result = rs.getString("count");
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public String path(String hash) {
		String result = "no";
		String path = "";
		try {
			Connection connection = DbConnection.getConnection();
			String query = "select * from upload where hash=?";
			PreparedStatement pr = connection.prepareStatement(query);
			pr.setString(1, hash);
			ResultSet rs = pr.executeQuery();
			if (rs.next()) {
				result = rs.getString("Id");
				path = rs.getString("path");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}

	public boolean userregister(RegPojo pojo) {
		boolean status = false;
		try {

			System.out.println("--------------------------");
			Connection connection = DbConnection.getConnection();
			List<RegPojo> userPojo = getUserDetails();
			String email = "";
			for (RegPojo userpojo1 : userPojo) {
				email = userpojo1.getEmail();
				System.out.println("----------email-------------->" + email);
			}
			if (pojo.getEmail().equalsIgnoreCase(email)) {
				status = false;
			} else {
				String query = "insert into register(name,email,password,phoneno,city,state,country,status) values(?,?,?,?,?,?,?,?)";
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setString(1, pojo.getName());
				ps.setString(2, pojo.getEmail());
				ps.setString(3, pojo.getPassword());
				ps.setString(4, pojo.getPhone());
				ps.setString(5, pojo.getCity());
				ps.setString(6, pojo.getState());
				ps.setString(7, pojo.getCountry());
				ps.setString(8, pojo.getStatus());
				int i = ps.executeUpdate();

				if (i > 0) {

					status = true;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}
	

	

	public boolean fileupload(UploadPojo user) {
		boolean status = false;
		
		try {

			System.out.println("--------------------------");
			Connection connection = DbConnection.getConnection();

				String query = "insert into upload(email,file,path,hash,bytes) values(?,?,?,?,?)";
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setString(1, user.getEmail());
				ps.setString(2, user.getFile());
				ps.setString(3, user.getPath());
				ps.setString(4, user.getHash());
				ps.setDouble(5, user.getBytes());
				int i = ps.executeUpdate();

				if (i > 0) {

					status = true;
				}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}
	
	public List<UploadPojo> getValueDetails() {
		List<UploadPojo> list = new ArrayList<UploadPojo>();
		try {
			Connection connection = DbConnection.getConnection();
			String query = "select * from upload";

			PreparedStatement pr = connection.prepareStatement(query);
			ResultSet rs = pr.executeQuery();
			while (rs.next()) {
				UploadPojo userpojo = new UploadPojo(rs.getString("email"),
						rs.getString("file"), 
						rs.getString("path"), 
						rs.getString("hash"), 
						rs.getString("id"),
						rs.getDouble("bytes"));
				list.add(userpojo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	public boolean ownerUpload(UploadPojo user) {
		boolean status = false;
		try {

			System.out.println("--------------------------");
			Connection connection = DbConnection.getConnection();

				String query1 = "insert into mapping(uid,email,file,path,hash,bytes) values(?,?,?,?,?,?)";
				PreparedStatement ps1 = connection.prepareStatement(query1);
				ps1.setString(1, user.getId());
				ps1.setString(2, user.getEmail());
				ps1.setString(3, user.getFile());
				ps1.setString(4, user.getPath());
				ps1.setString(5, user.getHash());
				ps1.setDouble(6, user.getBytes());
				int j = ps1.executeUpdate();

				if (j > 0) {

					status = true;
				}
				
				

		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}
	
	private List<RegPojo> getUserDetails() {
		List<RegPojo> list = new ArrayList<RegPojo>();
		try {
			Connection connection = DbConnection.getConnection();
			String query = "select * from register";

			PreparedStatement pr = connection.prepareStatement(query);
			ResultSet rs = pr.executeQuery();
			while (rs.next()) {
				RegPojo userpojo = new RegPojo(rs.getString("name"), rs
						.getString("email"), rs.getString("password"), rs
						.getString("phoneno"), rs.getString("city"), rs
						.getString("state"), rs.getString("country"),rs.getString("status"));
				list.add(userpojo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public boolean UserIdUpdate(String email, String file) {
		
		System.out.println("---------email--------"+email+"---------file--------"+file);
		boolean result = false;
		try {
			
			System.out.println("--------------------------");
			Connection connection = DbConnection.getConnection();
			
			String query = "update upload set file='" + file+ "' where email1='" + email
			+ "'";
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setString(1, file);
				ps.setString(2, email);
				int i = ps.executeUpdate();

				if (i > 0) {

					result = true;
				}
		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public String getCheck(String email, String hash) {
		// TODO Auto-generated method stub
		String result = "no";
		try {
			Connection connection = DbConnection.getConnection();
			String query = "select * from mapping where email=? and hash=?";
			PreparedStatement pr = connection.prepareStatement(query);
			pr.setString(1, email);
			pr.setString(2, hash);
			ResultSet rs = pr.executeQuery();
			if (rs.next()) {
				result = rs.getString("hash");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean cloudUpload(UploadPojo user1) {
		boolean status = false;
		try {

			System.out.println("--------------------------");
			Connection connection = DbConnection.getConnection();

				String query1 = "insert into mapping(uid,email,file,path,hash,bytes) values(?,?,?,?,?,?)";
				PreparedStatement ps1 = connection.prepareStatement(query1);
				ps1.setString(1, user1.getId());
				ps1.setString(2, user1.getEmail());
				ps1.setString(3, user1.getFile());
				ps1.setString(4, user1.getPath());
				ps1.setString(5, user1.getHash());
				ps1.setDouble(6, user1.getBytes());
				int j = ps1.executeUpdate();

				if (j > 0) {

					status = true;
				}
				
				

		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}
	public List<UploadPojo> getname(String filename){
		List<UploadPojo> list = new ArrayList<UploadPojo>();
		try {
			Connection connection = DbConnection.getConnection();
			String query = "select * from upload where file=?";

			PreparedStatement pr = connection.prepareStatement(query);
			ResultSet rs = pr.executeQuery();
			pr.setString(1, filename);
			while (rs.next()) {
				UploadPojo userpojo = new UploadPojo(rs.getString("email"),rs
						.getString("file"), rs.getString("path"), rs
						.getString("hash"), rs
						.getString("id"),rs.getDouble("bytes"));
				list.add(userpojo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}
	public List<DownloadPojo> getFileDetails(String email){
		List<DownloadPojo> list = new ArrayList<DownloadPojo>();
		try {
			Connection connection = DbConnection.getConnection();
			String query = "SELECT upload.file as ofile, mapping.email, mapping.file, mapping.path, mapping.hash FROM upload INNER JOIN mapping ON mapping.uid = upload.Id WHERE mapping.email = '"+email+"'";

			PreparedStatement pr = connection.prepareStatement(query);
			ResultSet rs = pr.executeQuery();
			while (rs.next()) {
				DownloadPojo userpojo = new DownloadPojo(rs.getString("ofile"),
						rs.getString("file"), 
						rs.getString("email"), 
						rs.getString("path"), 
						rs.getString("hash"));
				list.add(userpojo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}
	public boolean Delete(String hash, String email) {
		boolean status = false;
		try {

			System.out.println("--------------------------");
			Connection connection = DbConnection.getConnection();
			
				String query = "DELETE FROM mapping WHERE hash=? AND email=?";
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setString(1, hash);
				ps.setString(2, email);
				
				int i = ps.executeUpdate();

				if (i > 0) {

					status = true;
				}

			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}
	public boolean ODelete(String hash, String email) {
		boolean status = false;
		try {

			System.out.println("--------------------------");
			Connection connection = DbConnection.getConnection();
			
				String query = "DELETE FROM upload WHERE hash=?";
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setString(1, hash);
				
				
				int i = ps.executeUpdate();

				if (i > 0) {

					status = true;
				}

			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}
}