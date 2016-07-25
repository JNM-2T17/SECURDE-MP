package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Item;
import model.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import dao.ActivityManager;
import dao.ItemManager;
import dao.UserManager;

@Controller
public class TheController {
	private User restoreSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User u = (User)request.getSession().getAttribute("sessionUser");
		System.out.println(u);
		if( u == null ) {
			Cookie[] cookies = request.getCookies();
			for(Cookie c : cookies) {
				if( c.getName().equals("sessionUser") ) {
					try {
						u = UserManager.getUser(Integer.parseInt(c.getValue()));
						request.getSession().setAttribute("sessionUser",u);
						if( u != null ) {
							ActivityManager.setUser(u);
							ActivityManager.addActivity("refreshed their session.");
						}
					} catch(SQLException se) {
						logError(se);
						se.printStackTrace();
					}
				}
			}
		} else {
			if( u.isExpired() ) {
				try {
					ActivityManager.addActivity("'s session expired.");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				logoutUser(request,response);
				return null;
			}
			u.refreshIdle();
		}
		return u;
	}
	
	private void logError(Exception e) {
		try {
			ActivityManager.addActivity("ran into the error " + e.getMessage() + ".");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private boolean isAuth(HttpServletRequest request, HttpServletResponse response, String privilege) throws ServletException, IOException {
		try {
			User u = restoreSession(request,response);
			if( u == null ) {
				ActivityManager.addActivity("tried to access " + privilege + " and failed.");
				request.getRequestDispatcher("WEB-INF/view/index.jsp").forward(request, response);
			} else {
				if( u.isAuth(privilege) ) {
					return true;
				} else {
					ActivityManager.addActivity("tried to access " + privilege + " and failed.");
					home(request,response);
				}
			}
		} catch (SQLException e) {
			logError(e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	@RequestMapping("/")
	public void home(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User u = restoreSession(request,response);
		homePage(u,request,response);
	}
	
	public void homePage(User u, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if( u == null ) {
			request.getRequestDispatcher("WEB-INF/view/index.jsp").forward(request, response);
		} else {
			switch(u.getRole()) {
			case 4:
				try {
					User[] users = UserManager.getSpecialUsers();
					request.setAttribute("users", users.length == 0 ? null : users);
				} catch(SQLException se) {
					logError(se);
					se.printStackTrace();
				}
				request.getRequestDispatcher("WEB-INF/view/admin-home.jsp").forward(request, response);
				break;
			case 3:
				request.getRequestDispatcher("WEB-INF/view/am-home.jsp").forward(request, response);
				break;
			case 2:
				Item[] items;
				try {
					items = ItemManager.getAllItems(null, null, 0, 25);
					if( items.length == 0 ) {
						items = null;
					}
					request.setAttribute("products",items);
				} catch (SQLException e) {
					logError(e);
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.getRequestDispatcher("WEB-INF/view/pm-home.jsp").forward(request, response);
				break;
			case 1:
			default:
				request.getRequestDispatcher("WEB-INF/view/index.jsp").forward(request, response);
			}
		}
	}
	
	@ResponseBody
	@RequestMapping("/login")
	public void login(@RequestParam("username") String username,
			@RequestParam("password") String password,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("sessionUser") == null) {
			try {
				User u = UserManager.login(username, password);
				if( u != null ) {
					request.getSession().setAttribute("sessionUser", u);
					Cookie c = new Cookie("sessionUser","" + u.getId());
					c.setMaxAge(1800);
					response.addCookie(c);
					ActivityManager.setUser(u);
					ActivityManager.addActivity("logged in.");
				} else {
					request.setAttribute("error","Invalid username/password combination");
				}
			} catch(SQLException se) {
				logError(se);
				se.printStackTrace();
			} catch(Exception e) {
				logError(e);
				e.printStackTrace();
				request.setAttribute("error","Invalid username/password combination");
			}
		}
		home(request,response);
	}
	
	@RequestMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User u = restoreSession(request,response);
		if( u != null) {
			logoutUser(request,response);
		}
		homePage(null,request,response);
	}
	
	public void logoutUser(HttpServletRequest request,HttpServletResponse response) {
		request.getSession().invalidate();
		Cookie[] cookies = request.getCookies();
		for(Cookie c : cookies) {
			if( c.getName().equals("sessionUser") ) {
				c.setMaxAge(0);
				response.addCookie(c);
			}
		}
		try {
			ActivityManager.addActivity("logged out.");
		} catch (SQLException e) {
			logError(e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ActivityManager.setUser(null);
	}
	
	@RequestMapping(value="/addProduct",method=RequestMethod.GET)
	public void addProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(isAuth(request,response,User.ADD_PRODUCT)) {
			request.getRequestDispatcher("WEB-INF/view/addProduct.jsp").forward(request, response);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/addProduct",method=RequestMethod.POST)
	public void addProductPost(
			@RequestParam("name") String name,
			@RequestParam("itemtype") int itemtype,
			@RequestParam("description") String description,
			@RequestParam("price") double price,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(isAuth(request,response,User.ADD_PRODUCT)) {
			try {
				ItemManager.addItem(itemtype,name,description,price);
				ActivityManager.addActivity("added product " + name + ".");
				home(request,response);
				return;
			} catch(SQLException se) {
				logError(se);
				se.printStackTrace();
			}
		}
		request.getRequestDispatcher("WEB-INF/view/addProduct.jsp").forward(request, response);
	}
	
	@RequestMapping(value="/editProduct",method=RequestMethod.GET)
	public void editProduct(@RequestParam("id") int id,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(isAuth(request,response,User.EDIT_PRODUCT)) {
			try {
				Item i = ItemManager.getItem(id);
				if( i != null ) {
					request.setAttribute("item",i);
				} else {
					request.setAttribute("error", "Invalid Item Id");
					home(request,response);
					return;
				}
				request.getRequestDispatcher("WEB-INF/view/editProduct.jsp").forward(request, response);
			} catch(SQLException se) {
				logError(se);
				se.printStackTrace();
				request.setAttribute("error", "An unexpected error occured.");
				home(request,response);
			}
		}
	}
	
	@ResponseBody
	@RequestMapping(value="editProduct",method = RequestMethod.POST)
	public void editProduct(@RequestParam("id") int id,
			@RequestParam("name") String name,
			@RequestParam("itemtype") int itemtype,
			@RequestParam("description") String description,
			@RequestParam("price") double price,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(isAuth(request,response,User.EDIT_PRODUCT)) {
			try {
				ItemManager.editItem(id, itemtype, name, description, price);
				ActivityManager.addActivity("edited item " + id + ".");
				home(request,response);
				return;
			} catch(SQLException se) {
				logError(se);
				se.printStackTrace();
				editProduct(id,request,response);
			}
		}
	}
	
	@ResponseBody
	@RequestMapping("/deleteProduct")
	public void deleteProduct(@RequestParam("id") int id,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		if(isAuth(request,response,User.DELETE_PRODUCT)) {
			try {
				ItemManager.deleteItem(id);
				pw.println("true");
				ActivityManager.addActivity("deleted item " + id + ".");
				return;
			} catch(SQLException se) {
				logError(se);
				se.printStackTrace();
			}
		}
		pw.println("false");
	}
	
	@RequestMapping(value="/createAccount",method=RequestMethod.GET)
	public void createAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(isAuth(request,response,User.CREATE_ACCOUNT)) {
			request.getRequestDispatcher("WEB-INF/view/createAccount.jsp").forward(request, response);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/createAccount",method=RequestMethod.POST)
	public void createAccount(
			@RequestParam("authPassword") String authPassword,
			@RequestParam("role") int role,
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("fname") String fname,
			@RequestParam("mi") String mi,
			@RequestParam("lname") String lname,
			@RequestParam("email") String email,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(isAuth(request,response,User.CREATE_ACCOUNT)) {
			try {
				User u = (User)request.getSession().getAttribute("sessionUser");
				u = UserManager.login(u.getUsername(), authPassword);
				if( u == null ) {
					request.setAttribute("error", "Authentication Failed.");
				} else {
					UserManager.addUser(role, username, password, fname, mi, lname, email);
					ActivityManager.addActivity("created user " + username + ".");
					home(request,response);
					return;
				}
			} catch(SQLException se) {
				logError(se);
				se.printStackTrace();
				request.setAttribute("error", "An unexpected error occured.");
			} catch (Exception e) {
				logError(e);
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("error", "Authentication Failed.");
			}
			request.getRequestDispatcher("WEB-INF/view/createAccount.jsp").forward(request, response);
		}
	}
	
	@RequestMapping(value="/editAccount",method=RequestMethod.GET)
	public void editAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(isAuth(request, response, User.EDIT_ACCOUNT)) {
			request.getRequestDispatcher("WEB-INF/view/editAccount.jsp").forward(request, response);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/editAccount",method=RequestMethod.POST)
	public void editAccount(
			@RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword,
			@RequestParam("confirmPassword") String confirmPassword,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(isAuth(request, response, User.EDIT_ACCOUNT)) {
			User u = (User)request.getSession().getAttribute("sessionUser");
			try {
				if( UserManager.login(u.getUsername(),oldPassword) != null ) {
					if( newPassword.equals(confirmPassword) ) {
						UserManager.changePass(u.getId(), newPassword);
						ActivityManager.addActivity("changed their password.");
						home(request,response);
						return;
					} else {
						request.setAttribute("error", "Passwords don't match.");
					}
				} else {
					request.setAttribute("error", "Authentication Failed");
				}
			} catch(SQLException se) {
				logError(se);
				se.printStackTrace();
				request.setAttribute("error", "An unexpected error occured.");
			} catch(Exception e) {
				logError(e);
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("error", "Authentication Failed.");
			}
			request.getRequestDispatcher("WEB-INF/view/editAccount.jsp").forward(request, response);
		}
	}
}
