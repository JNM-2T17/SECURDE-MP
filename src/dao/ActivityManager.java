package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ActivityManager {
	public void addActivity(String activity) throws Exception {
		Connection con = DBManager.getInstance().getConnection();
		
		String sql = "INSERT INTO tl_activity(activity) VALUES (?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1,activity);
		ps.execute();
	}
}
