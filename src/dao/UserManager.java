package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.BCrypt;
import model.User;

public class UserManager {
	public static boolean addUser(int role, String username, 
								String password, String fname, String mi, 
								String lname,String email) throws SQLException {
		if( getUser(username) == null ) {
			Connection con = DBManager.getInstance().getConnection();
			String sql = "INSERT INTO tl_user(role,username,password,fName,mi,lName,emailAddress) "
					+ "VALUES (?,?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, role);
			ps.setString(2, username);
			String hashPW = BCrypt.hashpw(password, BCrypt.gensalt(12));
			ps.setString(3, hashPW);
			ps.setString(4, fname);
			ps.setString(5, mi);
			ps.setString(6, lname);
			ps.setString(7, email);
			ps.execute();
			sql = "UPDATE tl_user SET expiresOn = DATE_ADD(NOW(),INTERVAL 1 DAY) WHERE username = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, username);
			ps.execute();
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean addUser(int role, String username, 
						String password, String fname, String mi, 
						String lname,String emailAddress,String billHouseNo,
						String billStreet,String billSubd,String billCity,
						String billPostCode,String billCountry,
						String shipHouseNo,String shipStreet,String shipSubd,
						String shipCity,String shipPostCode,String shipCountry) throws SQLException {
		if( getUser(username) == null ) { 
			Connection con = DBManager.getInstance().getConnection();
			String sql = "INSERT INTO tl_user(role,username,password,fName,mi,lName,emailAddress,billHouseNo,billStreet,billSubd,billCity,billPostCode,billCountry,shipHouseNo,shipStreet,shipSubd,shipCity,shipPostCode,shipCountry) "
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, role);
			ps.setString(2, username);
			String hashPW = BCrypt.hashpw(password, BCrypt.gensalt(12));
			ps.setString(3, hashPW);
			ps.setString(4, fname);
			ps.setString(5, mi);
			ps.setString(6, lname);
			ps.setString(7, emailAddress);
			ps.setString(8,billHouseNo);
			ps.setString(9,billStreet);
			ps.setString(10,billSubd);
			ps.setString(11,billCity);
			ps.setString(12,billPostCode);
			ps.setString(13,billCountry);
			ps.setString(14,shipHouseNo);
			ps.setString(15,shipStreet);
			ps.setString(16,shipSubd);
			ps.setString(17,shipCity);
			ps.setString(18,shipPostCode);
			ps.setString(19,shipCountry);
			ps.execute();
			return true;
		} else {
			return false;
		}
	}
	
	public static void changePass(int id, String password) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		String sql = "UPDATE tl_user SET password = ?,dateEdited = NOW(),expiresOn = DATE_ADD(NOW(), INTERVAL 3 MONTH) WHERE id = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		String hashPW = BCrypt.hashpw(password, BCrypt.gensalt(12));
		ps.setString(1,hashPW);
		ps.setInt(2,id);
		ps.execute();		
	}
	
	@SuppressWarnings("resource")
	public static User login(String username, String password) throws Exception {
		Connection con = DBManager.getInstance().getConnection();
		String sql = "SELECT U.id,role,username,password,fName,mi,"
				+ "lName,emailAddress,billHouseNo,billStreet,billSubd,"
				+ "billCity,billPostCode,billCountry,shipHouseNo,shipStreet,"
				+ "shipSubd,shipCity,shipPostCode,shipCountry,searchProduct,"
				+ "purchaseProduct,reviewProduct,addProduct,editProduct,"
				+ "deleteProduct,viewRecords,createAccount,U.dateEdited > U.dateAdded AS passChanged, expiresOn IS NOT NULL AND expiresOn < NOW() AS expired "
				+ "FROM tl_user U INNER JOIN tl_role R ON U.role = R.id AND U.status = 1 AND R.status = 1 "
				+ "WHERE username = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1,username);
		ResultSet rs = ps.executeQuery();
		if( rs.next() ) {
			if(rs.getBoolean("expired")) {
				sql = "UPDATE tl_user SET status = 0 WHERE username = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1,username);
				ps.execute();
				throw new Exception("Expired Account");
			} else if(BCrypt.checkpw(password, rs.getString("password"))) {
				if( rs.getBoolean("passChanged")) {
					sql = "UPDATE tl_user SET expiresOn = DATE_ADD(NOW(),INTERVAL 3 MONTH) WHERE username = ?";
					ps = con.prepareStatement(sql);
					ps.setString(1,username);
					ps.execute();
				}
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
	
	public static User getUser(int id) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		String sql = "SELECT U.id,role,username,password,fName,mi,"
				+ "lName,emailAddress,billHouseNo,billStreet,billSubd,"
				+ "billCity,billPostCode,billCountry,shipHouseNo,shipStreet,"
				+ "shipSubd,shipCity,shipPostCode,shipCountry,searchProduct,"
				+ "purchaseProduct,reviewProduct,addProduct,editProduct,"
				+ "deleteProduct,viewRecords,createAccount "
				+ "FROM tl_user U INNER JOIN tl_role R ON U.role = R.id AND U.status = 1 AND R.status = 1 "
				+ "WHERE U.id = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1,id);
		ResultSet rs = ps.executeQuery();
		if( rs.next() ) {
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
		return null;
	}
	
	public static User[] getSpecialUsers() throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		String sql = "SELECT U.id,R.roleName as role,username,fName,mi,lName,emailAddress "
				+ "FROM tl_user U INNER JOIN tl_role R ON U.role = R.id AND U.status = 1 AND R.status = 1 "
				+ "WHERE U.role != 1 "
				+ "ORDER BY username";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		ArrayList<User> users = new ArrayList<User>();
		while(rs.next()){
			users.add(new User(rs.getInt("id"),rs.getString("role"),rs.getString("username"),rs.getString("fName"),rs.getString("mi"),rs.getString("lName"),rs.getString("emailAddress")));
		}
		return users.toArray(new User[0]);
	}
	
	public static User getUser(String username) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		String sql = "SELECT id,username,fName,mi,"
				+ "lName,emailAddress,billHouseNo,billStreet,billSubd,"
				+ "billCity,billPostCode,billCountry,shipHouseNo,shipStreet,"
				+ "shipSubd,shipCity,shipPostCode,shipCountry "
				+ "FROM tl_user "
				+ "WHERE status = 1 AND username = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1,username);
		ResultSet rs = ps.executeQuery();
		if( rs.next() ) {
			return new User(rs.getInt("id"),
					rs.getString("username"),rs.getString("fName"),
					rs.getString("mi"),rs.getString("lName"),
					rs.getString("emailAddress"),rs.getString("billHouseNo"),
					rs.getString("billStreet"),rs.getString("billSubd"),
					rs.getString("billCity"),rs.getString("billPostCode"),
					rs.getString("billCountry"),rs.getString("shipHouseNo"),
					rs.getString("shipStreet"),rs.getString("shipSubd"),
					rs.getString("shipCity"),rs.getString("shipPostCode"),
					rs.getString("shipCountry"));
		}
		return null;
	}
}
