package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import model.User;

public class ActivityManager {
	private static String start = "Anonymous user "; 
	
	public static void setUser(User u) {
		start = u == null ? "Anonymous user " : "User with id#" + u.getId() + " - " + u.getUsername() + " ";
	}
	
	public static void setUser(HttpServletRequest request) {
		start = "Anonymous user with ip address " + request.getRemoteAddr() + " ";
	}
	
	public static void addActivity(String activity) {
		Connection con = DBManager.getInstance().getConnection();
		try {
			String sql = "INSERT INTO tl_activity(activity) VALUES (?)";
			System.out.println(con);
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1,start + activity);
			ps.execute();
		} catch(SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				con.close();
			} catch(SQLException se) {
				se.printStackTrace();
			}
		}
	}
}
