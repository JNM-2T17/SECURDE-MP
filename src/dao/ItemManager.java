package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Item;
import model.Review;
import model.SaleMapping;

public class ItemManager {
	public static void addItem(int type, String name, String description, double price) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		String sql = "INSERT INTO tl_item(itemtype,name,description,price) VALUES (?,?,?,?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1,type);
		ps.setString(2,name);
		ps.setString(3, description);
		ps.setDouble(4,price);
		ps.execute();
	}
	
	public static void editItem(int id, int type, String name, String description, double price) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		String sql = "UPDATE tl_item SET itemtype = ?, name = ?,description = ?,price = ? WHERE id = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1,type);
		ps.setString(2,name);
		ps.setString(3, description);
		ps.setDouble(4,price);
		ps.setInt(5,id);
		ps.execute();
	}
	
	public static Item[] getAllItems(Integer type, String query) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		String sql = "SELECT id, I.name, T.name AS itemtype, description, price "
				+ "FROM tl_item I INNER JOIN tl_item_type T ON I.itemtype = T.id AND "
				+ "I.status = 1 AND T.status = 1 ";
		if( type != null ) {
			sql += "AND itemtype = ? ";
		}
		if( query != null ) {
			sql += "AND name LIKE ?";
		}
		PreparedStatement ps = con.prepareStatement(sql);
		int index = 1;
		if( type != null ) {
			ps.setInt(index,type.intValue());
			index++;
		}
		if( query != null ) {
			ps.setString(index,"%" + query + "%");
		}
		ResultSet rs = ps.executeQuery();
		
		ArrayList<Item> items = new ArrayList<Item>();
		while(rs.next()) {
			items.add(new Item(rs.getInt("id"),rs.getString("name"),
						rs.getString("itemtype"),rs.getString("description"),
						rs.getDouble("price")));
		}
		return items.toArray(new Item[0]);
	}
	
	public static void purchaseItem(int userId, int itemId, int quantity) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		String sql = "INSERT INTO tl_purchase(userId,itemId,quantity) VALUES (?,?,?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1,userId);
		ps.setInt(2,itemId);
		ps.setInt(3, quantity);
		ps.execute();
	}
	
	public static void reviewItem(int userId, int itemId, int rating, String review) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		String sql = "INSERT INTO tl_review(userId,itemId,rating,review) VALUES (?,?,?,?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1,userId);
		ps.setInt(2,itemId);
		ps.setInt(3, rating);
		ps.setString(4,review);
		ps.execute();
	}
	
	public static double getTotalSales() throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		String sql = "SELECT SUM(quantity * price) AS TotalSales "
				+ "FROM tl_purchase P INNER JOIN tl_item I ON P.itemId = I.id AND "
				+ "P.status = 1 AND I.status = 1";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		if( rs.next() ) {
			return rs.getDouble("TotalSales");
		}
		return 0;
	}
	
	public static SaleMapping[] getTotalSales(int itemtype) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		String sql = "SELECT T.name AS ItemType, SUM(quantity * price) AS TotalSales "
				+ "FROM tl_purchase P INNER JOIN tl_item I ON P.itemId = I.id AND "
				+ "P.status = 1 AND I.status = 1 "
				+ "INNER JOIN tl_item_type T ON I.itemtype = T.id AND T.status = 1 "
				+ "GROUP BY T.id";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		ArrayList<SaleMapping> sm = new ArrayList<SaleMapping>();
		while( rs.next() ) {
			sm.add(new SaleMapping(rs.getString("ItemType"),rs.getDouble("TotalSales")));
		}
		return sm.toArray(new SaleMapping[0]);
	}
	
	public static SaleMapping[] getTotalSalesPerItem() throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		String sql = "SELECT I.name, SUM(quantity * price) AS TotalSales "
				+ "FROM tl_purchase P INNER JOIN tl_item I ON P.itemId = I.id AND "
				+ "P.status = 1 AND I.status = 1 "
				+ "GROUP BY I.id";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		ArrayList<SaleMapping> sm = new ArrayList<SaleMapping>();
		while( rs.next() ) {
			sm.add(new SaleMapping(rs.getString("name"),rs.getDouble("TotalSales")));
		}
		return sm.toArray(new SaleMapping[0]);
	}
	
	public static Review[] getReviews(int itemId, int start, int limit) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		String sql = "SELECT username, rating, review "
				+ "FROM tl_review R INNER JOIN tl_user U ON R.userId = U.id AND R.status = 1 AND U.status = 1 "
				+ "WHERE R.itemId = ?"
				+ "LIMIT ?,?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1,itemId);
		ps.setInt(2,start);
		ps.setInt(3,limit);
		ResultSet rs = ps.executeQuery();
		ArrayList<Review> reviews = new ArrayList<Review>();
		while(rs.next()) {
			reviews.add(new Review(rs.getString("username"),rs.getInt("rating"),rs.getString("review")));
		}
		return reviews.toArray(new Review[0]);
	}
}
