package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.RegPojo;
import model.UploadPojo;
import DB.DbConnection;

public class DBConnection {
	public List<RegPojo> getUserDetails() {
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
	public boolean update(String email,String status){
		boolean result=false;
try {
			
			System.out.println("--------------------------");
			Connection connection = DbConnection.getConnection();
			
			String query = "update register set status=? where email=?";
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setString(1, status);
				ps.setString(2, email);
				int status1 = ps.executeUpdate();
				if (status1 > 0) {
					result = true;
				}
		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return result;
	}
	public List<UploadPojo> getValueDetails() {
		List<UploadPojo> list = new ArrayList<UploadPojo>();
		try {
			Connection connection = DbConnection.getConnection();
			String query = "select * from upload";

			PreparedStatement pr = connection.prepareStatement(query);
			ResultSet rs = pr.executeQuery();
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
	}}
