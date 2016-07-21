package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import model.User;

public class ActivityManager {
	private static String start = "Anonymous user "; 
	
	public static void setUser(User u) {
		start = u == null ? "Anonymous user " : "User with " + u.getId() + " - " + u.getUsername() + " ";
	}
	
	public static void addActivity(String activity) throws Exception {
		Connection con = DBManager.getInstance().getConnection();
		
		String sql = "INSERT INTO tl_activity(activity) VALUES (?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1,start + activity);
		ps.execute();
	}
}
