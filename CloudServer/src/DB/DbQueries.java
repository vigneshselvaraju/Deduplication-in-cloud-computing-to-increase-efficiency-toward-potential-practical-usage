package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbQueries {
	public String hashcheck(String hash) {
			String result = "no";
			String res="no";
			try {
				Connection connection = DbConnection.getConnection();
				String query = "select * from files where hash=?";
				PreparedStatement pr = connection.prepareStatement(query);
				pr.setString(1, hash);
				ResultSet rs = pr.executeQuery();
				if (rs.next()) {
					result = rs.getString("filename");
					res = rs.getString("path");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return result+"@"+res;
		}
}
