package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.BCrypt;
import model.User;

public class UserManager {
	public static void addUser(int role, String username, 
								String password, String fname, String mi, 
								String lname,String email) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		String sql = "INSERT INTO tl_user(role,username,password,fName,mi,lName,emailAddress) "
				+ "VALUES (?,?,?,?,?,?,?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, role);
		ps.setString(2, username);
		String hashPW = BCrypt.hashpw(password, BCrypt.gensalt());
		ps.setString(3, hashPW);
		ps.setString(4, fname);
		ps.setString(5, mi);
		ps.setString(6, lname);
		ps.setString(7, email);
		ps.execute();
	}
	
	public static void changePass(int id, String password) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		String sql = "UPDATE tl_user SET password = ? WHERE id = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		String hashPW = BCrypt.hashpw(password, BCrypt.gensalt());
		ps.setString(1,hashPW);
		ps.setInt(2,id);
		ps.execute();		
	}
	
	public static User login(String username, String password) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		String sql = "SELECT U.id,R.name AS role,username,password,fName,mi,"
				+ "lName,emailAddress,billHouseNo,billStreet,billSubd,"
				+ "billCity,billPostCode,billCountry,shipHouseNo,shipStreet,"
				+ "shipSubd,shipCity,shipPostCode,shipCountry,searchProduct,"
				+ "purchaseProduct,reviewProduct,addProduct,editProduct,"
				+ "deleteProduct,viewRecords,createAccount "
				+ "FROM tl_user U INNER JOIN tl_role R ON U.role = R.id AND U.status = 1 AND R.status = 1 "
				+ "WHERE username = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1,username);
		ResultSet rs = ps.executeQuery();
		if( rs.next() ) {
			if(BCrypt.checkpw(password, rs.getString("password"))) {
				return new User(rs.getInt("id"),rs.getInt("role"),
						rs.getString("username"),rs.getString("fName"),
						rs.getString("mi"),rs.getString("lName"),
						rs.getString("emailAddress"),rs.getString("billHouseNo"),
						rs.getString("billStreet"),rs.getString("billSubd"),
						rs.getString("billCity"),rs.getString("billPostCode"),
						rs.getString("billCountry"),rs.getString("shipHouseNo"),
						rs.getString("shipStreet"),rs.getString("shipSubd"),
						rs.getString("shipCity"),rs.getString("shipPostCode"),
						rs.getString("shipCountry"),rs.getBoolean("searchProduct"),
						rs.getBoolean("purchaseProduct"),rs.getBoolean("reviewProduct"),
						rs.getBoolean("addProduct"),rs.getBoolean("editProduct"),
						rs.getBoolean("deleteProduct"),rs.getBoolean("viewRecords"),
						rs.getBoolean("createAccount"));
			}
		}
		return null;
	}
}
