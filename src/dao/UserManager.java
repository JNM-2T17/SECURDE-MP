package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import security.AuthenticationException;
import security.ExpiredAccountException;
import security.LockoutException;
import model.BCrypt;
import model.User;

public class UserManager {
	public static final int LOGIN_MAX_ATTEMPTS = 5;
	public static final int LOCKOUT_MINUTES = 15;
	
	public static boolean checkPass(String password) {
		if( password.length() < 8 ) {
			return false;
		}
		
		boolean cap = false;
		boolean low = false;
		boolean num = false;
		boolean spec = false;
		
		for(int i = 0; i < password.length(); i++) {
			if( password.substring(i,i + 1).matches("/[A-Z]/")) {
				cap = true;
			} else if(password.substring(i,i + 1).matches("/[a-z]/")) {
				low = true;
			} else if(password.substring(i,i + 1).matches("/[0-9]/")) {
				num = true;
			} else {
				spec = true;
			}
		}
		
		return (cap && low && num && spec);
	}
	
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
			con.close();
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean addUser(String username, 
						String password, String fname, String mi, 
						String lname,String emailAddress,String billHouseNo,
						String billStreet,String billSubd,String billCity,
						String billPostCode,String billCountry,
						String shipHouseNo,String shipStreet,String shipSubd,
						String shipCity,String shipPostCode,String shipCountry) throws SQLException {
		if( getUser(username) == null ) { 
			Connection con = DBManager.getInstance().getConnection();
			String sql = "INSERT INTO tl_user(role,username,password,fName,mi,lName,emailAddress,billHouseNo,billStreet,billSubd,billCity,billPostCode,billCountry,shipHouseNo,shipStreet,shipSubd,shipCity,shipPostCode,shipCountry) "
			+ "VALUES (1,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, username);
			String hashPW = BCrypt.hashpw(password, BCrypt.gensalt(12));
			ps.setString(2, hashPW);
			ps.setString(3, fname);
			ps.setString(4, mi);
			ps.setString(5, lname);
			ps.setString(6, emailAddress);
			ps.setString(7,billHouseNo);
			ps.setString(8,billStreet);
			ps.setString(9,billSubd);
			ps.setString(10,billCity);
			ps.setString(11,billPostCode);
			ps.setString(12,billCountry);
			ps.setString(13,shipHouseNo);
			ps.setString(14,shipStreet);
			ps.setString(15,shipSubd);
			ps.setString(16,shipCity);
			ps.setString(17,shipPostCode);
			ps.setString(18,shipCountry);
			ps.execute();
			sql = "UPDATE tl_user SET expiresOn = DATE_ADD(NOW(),INTERVAL 3 MONTH) WHERE username = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, username);
			ps.execute();
			con.close();
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
		con.close();	
	}
	
	@SuppressWarnings("resource")
	public static User login(String username, String password) throws LockoutException, SQLException, ExpiredAccountException, AuthenticationException {
		Connection con = DBManager.getInstance().getConnection();
		try {
			String sql = "SELECT U.id,role,username,password,fName,mi,"
					+ "lName,emailAddress,billHouseNo,billStreet,billSubd,"
					+ "billCity,billPostCode,billCountry,shipHouseNo,shipStreet,"
					+ "shipSubd,shipCity,shipPostCode,shipCountry,searchProduct,"
					+ "purchaseProduct,reviewProduct,addProduct,editProduct,"
					+ "deleteProduct,viewRecords,createAccount,U.dateEdited > '0000-00-00 00:00:00' AS passChanged, expiresOn IS NOT NULL AND expiresOn < NOW() AS expired, "
					+ "loginAttempts,IFNULL(NOW() > lockedUntil,TRUE) AS unlocked, lockedUntil, "
					+ "TIMESTAMPDIFF(MINUTE,NOW(),lockedUntil) AS unlockTime "
					+ "FROM tl_user U INNER JOIN tl_role R ON U.role = R.id AND U.status = 1 AND R.status = 1 "
					+ "WHERE username = BINARY ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1,username);
			ResultSet rs = ps.executeQuery();
			if( rs.next() ) {
				System.out.println(rs.getBoolean("expired"));
				if(rs.getBoolean("expired")) {
					sql = "UPDATE tl_user SET status = 0 WHERE username = ?";
					ps = con.prepareStatement(sql);
					ps.setString(1,username);
					ps.execute();
					throw new ExpiredAccountException();
				} else if(rs.getBoolean("unlocked")) {
					if(BCrypt.checkpw(password, rs.getString("password"))) {
						if( rs.getBoolean("passChanged")) {
							sql = "UPDATE tl_user SET expiresOn = DATE_ADD(NOW(),INTERVAL 3 MONTH) WHERE username = ?";
							ps = con.prepareStatement(sql);
							ps.setString(1,username);
							ps.execute();
						}
						sql = "UPDATE tl_user SET lockedUntil = NULL WHERE username = ?";
						ps = con.prepareStatement(sql);
						ps.setString(1,username);
						ps.execute();
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
					} else {
						int loginAttempts = rs.getInt("loginAttempts") + 1;
						sql = "UPDATE tl_user SET loginAttempts = " + (loginAttempts >= LOGIN_MAX_ATTEMPTS ? "0, lockedUntil = DATE_ADD(NOW(),INTERVAL " + LOCKOUT_MINUTES + " MINUTE)" : 
							"loginAttempts + 1") + " WHERE username = ?";
						ps = con.prepareStatement(sql);
						ps.setString(1,username);
						ps.execute();
						if( loginAttempts >= LOGIN_MAX_ATTEMPTS) {
							throw new LockoutException("Account locked out for " + LOCKOUT_MINUTES + " minutes", LOCKOUT_MINUTES);
						} else {
							throw new AuthenticationException();
						}
					}
				} else {
					int minute = rs.getInt("unlockTime");
					throw new LockoutException("Account locked out for " + minute + " minutes", minute);
				}
			}
			return null;
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static User getUser(int id) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		try {
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
		} catch(Exception e) {
			throw e;
		} finally {
			con.close();
		}
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
		con.close();
		return users.toArray(new User[0]);
	}
	
	public static User getUser(String username) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		try {
			String sql = "SELECT id,username,fName,mi,"
					+ "lName,emailAddress,billHouseNo,billStreet,billSubd,"
					+ "billCity,billPostCode,billCountry,shipHouseNo,shipStreet,"
					+ "shipSubd,shipCity,shipPostCode,shipCountry "
					+ "FROM tl_user "
					+ "WHERE status = 1 AND username = BINARY ?";
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
		} catch(Exception e) {
			throw e;
		} finally {
			con.close();
		}
	}
}
