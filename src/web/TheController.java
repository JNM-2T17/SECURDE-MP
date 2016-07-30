package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Cart;
import model.Item;
import model.Review;
import model.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import security.AuthenticationException;
import security.ExpiredAccountException;
import security.Hasher;
import security.LockoutException;
import security.MissingTokenException;
import security.Randomizer;


import dao.ActivityManager;
import dao.ItemManager;
import dao.UserManager;

@Controller
public class TheController {
	private void restoreToken(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String token = (String)request.getSession().getAttribute("sessionToken");
		if(token == null || token.length() == 0 ) {
			try {
				Hasher hash = new Hasher(Hasher.SHA256);
				Randomizer random = new Randomizer(1234);
				hash.updateHash(random.getRandomLong() + "","UTF-8");
				hash.updateHash(request.getRemoteAddr(),"UTF-8");
				token = hash.getHashBASE64();
				request.getSession().setAttribute("sessionToken",token);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private String genHash(User u,String ip) {
		Hasher hash;
		try {
			hash = new Hasher(Hasher.SHA256);
			hash.updateHash(u.getId() + u.getUsername(),"UTF-8");
			hash.updateHash(ip,"UTF-8");
			String token = u.getId() + "$" + hash.getHashBASE64();
			return token;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	private void genToken(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User u = restoreSession(request,response);
		if( u != null ) {
			request.getSession().setAttribute("sessionToken",genHash(u,request.getRemoteAddr())); 
		}
	}
	
	private User restoreSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User u = (User)request.getSession().getAttribute("sessionUser");
		restoreToken(request,response);
		System.out.println(u);
		if( u == null ) {
			Cookie[] cookies = request.getCookies();
			if( cookies != null ) {
				for(Cookie c : cookies) {
					if( c.getName().equals("sessionToken") ) {
						try {
							int dollar = c.getValue().indexOf('$');
							u = UserManager.getUser(Integer.parseInt(c.getValue().substring(0,dollar)));
							if( u != null ) {
								String genHash = genHash(u,request.getRemoteAddr());
								if( genHash.equals(c.getValue())) {			
									request.getSession().setAttribute("sessionUser",u);
									ActivityManager.setUser(u);
									ActivityManager.addActivity("refreshed their session.");
								} 
							} else {
								ActivityManager.setUser(request);
							}
						} catch(SQLException se) {
							logError(se);
							se.printStackTrace();
						}
						break;
					}
				}
			}
		} else {
			if( u.isExpired() ) {
				ActivityManager.addActivity("'s session expired.");
				logoutUser(request,response);
				return null;
			}
			u.refreshIdle();
		}
		return u;
	}
	
	private void checkToken(String token,HttpServletRequest request,HttpServletResponse response) throws MissingTokenException, ServletException, IOException {
		String sessionToken = (String)request.getSession().getAttribute("sessionToken");
		if( sessionToken == null ) {
			restoreSession(request,response);
			sessionToken = (String)request.getSession().getAttribute("sessionToken");
		}
		if(!sessionToken.equals(token)) {
			throw new MissingTokenException();
		}
	}
	
	private void logError(Exception e) {
		ActivityManager.addActivity("ran into the error " + e.getMessage() + ".");
	}
	
	private boolean isAuth(HttpServletRequest request, HttpServletResponse response, String privilege) throws ServletException, IOException {
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
	public void login(@RequestParam("token") String token,
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User u = restoreSession(request,response);
		if( u == null) {
			try {
				checkToken(token,request,response);
				u = UserManager.login(username, password);
				if( u != null ) {
					String genHash = genHash(u,request.getRemoteAddr());
					request.getSession().setAttribute("sessionUser", u);
					Cookie c = new Cookie("sessionToken",genHash);
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
			} catch (LockoutException e) {
				// TODO Auto-generated catch block
				if(e.getMinutes() == UserManager.LOCKOUT_MINUTES) {
					ActivityManager.addActivity("locked out " + username + "'s account." );
					request.setAttribute("error", e.getMessage());
				} else {
					ActivityManager.addActivity("tried to login to " + username + "'s locked account." );
					request.setAttribute("error", e.getMessage());
				}
			} catch (ExpiredAccountException e) {
				// TODO Auto-generated catch block
				ActivityManager.addActivity("tried to login to " + username + "'s expired account.");
				request.setAttribute("error", "Invalid username/password combination");
			} catch (AuthenticationException e) {
				// TODO Auto-generated catch block
				ActivityManager.addActivity("failed to login to " + username + "'s account.");
				request.setAttribute("error", "Invalid username/password combination");
			} catch (MissingTokenException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("error", "An unexpected error occured.");
			} 
		}
		home(request,response);
	}
	
	@RequestMapping(value="register",method = RequestMethod.GET)
	public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User u = restoreSession(request, response);
		if( u != null ) {
			home(request,response);
		} else {
			request.getRequestDispatcher("WEB-INF/view/register.jsp").forward(request, response);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="register",method = RequestMethod.POST)
	public void register(@RequestParam("token") String token,
						@RequestParam("username") String username,
						@RequestParam("password") String password,
						@RequestParam("confirmPassword") String confirmPassword,
						@RequestParam("fname") String fname,
						@RequestParam("mi") String mi,
						@RequestParam("lname") String lname,
						@RequestParam("email") String email,
						@RequestParam("billHouseNo") String billHouseNo,
						@RequestParam("billStreet") String billStreet,
						@RequestParam("billSubd") String billSubd,
						@RequestParam("billCity") String billCity,
						@RequestParam("billPostCode") String billPostCode,
						@RequestParam("billCountry") String billCountry,
						@RequestParam("shipHouseNo") String shipHouseNo,
						@RequestParam("shipStreet") String shipStreet,
						@RequestParam("shipSubd") String shipSubd,
						@RequestParam("shipCity") String shipCity,
						@RequestParam("shipPostCode") String shipPostCode,
						@RequestParam("shipCountry") String shipCountry,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User u = restoreSession(request, response);
		if( u != null ) {
			home(request,response);
		} else {
			try {
				checkToken(token,request,response);
				if( username.matches("^[A-Za-z0-9_-]+$") && fname.matches("^[A-Za-z ,.'-]+$") && 
						mi.matches("^[A-Za-z]{0,2}.?$") && lname.matches("^[A-Za-z ,.'-]+$") && 
						email.matches("^([-.a-zA-Z0-9_]+)@([-.a-zA-Z0-9_]+)[.]([a-zA-Z]{2,5})$") && 
						billHouseNo.matches("^[#]?[0-9]+[A-Za-z -]*$") &&	billStreet.matches("^[0-9]*[A-Za-z ,.'-]+$") && 
						billSubd.matches("^[A-Za-z ,.'-]+$") && billCity.matches("^[A-Za-z ,.'-]+$") && 
						billPostCode.matches("^[A-Za-z0-9]+$") && billCountry.matches("^[A-Za-z ]+$") && 
						shipHouseNo.matches("^[#]?[0-9]+[A-Za-z -]*$") && shipStreet.matches("^[0-9]*[A-Za-z ,.'-]+$") && 
						shipSubd.matches("^[A-Za-z ,.'-]+$") && shipCity.matches("^[A-Za-z ,.'-]+$") && 
						shipPostCode.matches("^[A-Za-z0-9]+$") && shipCountry.matches("^[A-Za-z ]+$") && 
						UserManager.checkPass(password) && password.equals(confirmPassword)) {
					UserManager.addUser(username, password, fname, mi, lname, email, billHouseNo, billStreet, billSubd, billCity, billPostCode, billCountry, shipHouseNo, shipStreet, shipSubd, shipCity, shipPostCode, shipCountry);
					login(token,username,password,request,response);
					ActivityManager.addActivity("registered their account.");
				} else {
					logError(new Exception("Data validation failed on register."));
					request.setAttribute("error","Failed to register account.");
					register(request,response);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logError(e);
				request.setAttribute("error","An unexpected error occured.");
				register(request,response);
			} catch (MissingTokenException e) {
				// TODO Auto-generated catch block
				logError(e);
			} 
		}
	}
	
	@RequestMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User u = restoreSession(request,response);
		if( u != null) {
			logoutUser(request,response);
			restoreToken(request,response);
		}
		homePage(null,request,response);
	}
	
	public void logoutUser(HttpServletRequest request,HttpServletResponse response) {
		request.getSession().invalidate();
		Cookie[] cookies = request.getCookies();
		for(Cookie c : cookies) {
			if( c.getName().equals("sessionToken") ) {
				c.setMaxAge(0);
				response.addCookie(c);
			}
		}
		ActivityManager.addActivity("logged out.");
		ActivityManager.setUser(request);
	}
	
	@RequestMapping("search")
	public void search(@RequestParam(value="type",required=false) Integer type, 
			@RequestParam(value="query",required=false) String query, 
			@RequestParam(value="start",required=false) Integer start,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User u = restoreSession(request, response);
		if( u == null ) {
			ActivityManager.setUser(request);
		}
		if( start == null ) {
			start = 0;
		}
		if( type != null && type.intValue() == 0 ) {
			type = null;
		}
		try {
			Item[] items = ItemManager.getAllItems(type,query,start,26);
			request.setAttribute("products", items);
			request.setAttribute("type",type);
			request.setAttribute("query",query);
			request.setAttribute("start",start);
			request.setAttribute("more", items.length == 26 ? true : false);
			ActivityManager.addActivity("searched for \"" + query + "\" of type " + type + ".");
			request.getRequestDispatcher("WEB-INF/view/search.jsp").forward(request,response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logError(e);
			e.printStackTrace();
			home(request,response);
		}
	}
	
	@RequestMapping("viewProduct")
	public void viewProduct(@RequestParam("id") int id,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User u = restoreSession(request, response);
		if( u == null ) {
			ActivityManager.setUser(request);
		}
		try {
			Item i = ItemManager.getItem(id);
			request.setAttribute("p", i);
			Review r = u == null ? null : ItemManager.getReview(u.getId(),id);
			boolean canReview = (r == null);
			if( canReview ) {
				canReview = u == null ? false : ItemManager.canReview(u.getId(),id);
			}
			Review[] reviews = ItemManager.getReviews(id, null, null);
			request.setAttribute("reviews",reviews.length == 0 ? null : reviews);
			request.setAttribute("loadMore",reviews.length == 11 ? true : false);
			request.setAttribute("review", r);
			request.setAttribute("canReview", canReview);
			ActivityManager.addActivity("viewed item " + id + ": " + i.getName() + ".");
			request.getRequestDispatcher("WEB-INF/view/viewProduct.jsp").forward(request,response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logError(e);
			home(request,response);
		}
	}
	
	private Cart refreshCart(HttpServletRequest request) throws ServletException, IOException {
		Cart c = (Cart)request.getSession().getAttribute("sessionCart");
		if( c == null ) {
			c = new Cart();
			request.getSession().setAttribute("sessionCart",c);
		}
		return c;
	}
	
	@RequestMapping("addToCart")
	@ResponseBody
	public void addToCart(@RequestParam("token") String token,
			@RequestParam("productId") int productId,
			@RequestParam("quantity") int quantity,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if( isAuth(request,response,User.PURCHASE_PRODUCT)) {
			Cart c = refreshCart(request);
			try {
				checkToken(token,request,response);
				c.addPurchase(ItemManager.getItem(productId), quantity);
				ActivityManager.addActivity("added " + quantity + " instances of item " + productId + " to their cart.");
				shoppingCart(request,response);
			} catch (SQLException | MissingTokenException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logError(e);
				request.setAttribute("error","Failed to add item to cart");
				viewProduct(productId,request,response);
			} 
		}
	}
	
	@RequestMapping("shoppingCart")
	public void shoppingCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(isAuth(request,response,User.PURCHASE_PRODUCT)) {
			refreshCart(request);
			request.getRequestDispatcher("WEB-INF/view/shoppingCart.jsp").forward(request,response);
		}
	}
	
	@RequestMapping(value="checkout",method=RequestMethod.GET)
	public void checkout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(isAuth(request,response,User.PURCHASE_PRODUCT)) {
			Cart c = refreshCart(request);
			if( c.size() > 0 ) {
				request.getRequestDispatcher("WEB-INF/view/checkout.jsp").forward(request,response);
			} else {
				request.setAttribute("error", "Cart is empty.");
				request.getRequestDispatcher("WEB-INF/view/shoppingCart.jsp").forward(request,response);
			}
		}
	}
	
	@ResponseBody
	@RequestMapping(value="checkout",method=RequestMethod.POST)
	public void checkout(@RequestParam("token") String token,
			@RequestParam("ccno") String ccno,
			@RequestParam("cardtype") String cardtype,
			@RequestParam("expmm") int expmm,
			@RequestParam("expyy") int expyy,
			@RequestParam("cvc") String cvc,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(isAuth(request,response,User.PURCHASE_PRODUCT)) {
			try {
				checkToken(token,request,response);
				User u = restoreSession(request,response);
				Cart c = refreshCart(request);
				if( c.size() > 0 ) {
					if( ccno.matches("^([0-9]{16}|([0-9]{4}-){3}[0-9]{4})$") && 
							cardtype.matches("^(visa|mastercard)$") && 
							("" + expmm).matches("^((0|)[1-9]|1[0-2])$") && 
							("" + expyy).matches("^2[0-9]{3}$") && 
							cvc.matches("^[0-9]{3}$") ){
						ItemManager.purchaseCart(u.getId(), c, cardtype, expmm, expyy, cvc);
						request.setAttribute("message", "Transaction Successful");
						ActivityManager.addActivity("checked out their cart with a total of " + c.getTotal() + ".");
						c.clear();
						home(request,response);
					} else {
						logError(new Exception("Data validation failed on checkout."));
						request.setAttribute("error","Failed to checkout.");
						checkout(request,response);
					}
				} else {
					request.setAttribute("error", "Cart is empty.");
					request.getRequestDispatcher("WEB-INF/view/shoppingCart.jsp").forward(request,response);
				}
			} catch (MissingTokenException | SQLException e) {
				logError(e);
				e.printStackTrace();
				request.setAttribute("error", "An unexpected error occured.");
				checkout(request,response);
			}
		}
	}
	
	@ResponseBody
	@RequestMapping("review")
	public void review(@RequestParam("token") String token,
			@RequestParam("prodId") int prodId,
			@RequestParam("review") String review,
			@RequestParam("rating") int rating,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(isAuth(request,response,User.REVIEW_PRODUCT)) {
			try {
				checkToken(token,request,response);
				User u = restoreSession(request,response);
				Review r = ItemManager.getReview(u.getId(), prodId);
				if( r == null ) {
					boolean canReview = ItemManager.canReview(u.getId(), prodId);
					if( canReview ) {
						if( review.length() > 0 && rating >= 1 && rating <= 5 ) {
							ItemManager.reviewItem(u.getId(), prodId, rating, review);
							request.setAttribute("message", "Review Submitted");
							response.getWriter().print("true");
							ActivityManager.addActivity("reviewed item " + prodId + ".");
						} else {
							logError(new Exception("Data validation failed on review product."));
							response.getWriter().print("Failed to review product.");
						}
					} else {
						response.getWriter().print("Please purchase this item before reviewing.");
						ActivityManager.addActivity("tried to review item " + prodId + " but hasn't purchased it yet.");
					}
				} else {
					ItemManager.updateReview(u.getId(), prodId, rating, review);
					response.getWriter().print("true");
				}
			} catch (MissingTokenException | SQLException e) {
				logError(e);
				e.printStackTrace();
				response.getWriter().print("An unexpected error occured.");
			}
		}
	}
	
	@RequestMapping(value="/addProduct",method=RequestMethod.GET)
	public void addProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(isAuth(request,response,User.ADD_PRODUCT)) {
			request.getRequestDispatcher("WEB-INF/view/addProduct.jsp").forward(request, response);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/addProduct",method=RequestMethod.POST)
	public void addProduct(
			@RequestParam("token") String token,
			@RequestParam("name") String name,
			@RequestParam("itemtype") int itemtype,
			@RequestParam("description") String description,
			@RequestParam("price") double price,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(isAuth(request,response,User.ADD_PRODUCT)) {
			try {
				checkToken(token,request,response);
				ItemManager.addItem(itemtype,name,description,price);
				ActivityManager.addActivity("added product " + name + ".");
				home(request,response);
				return;
			} catch(SQLException | MissingTokenException se) {
				logError(se);
				se.printStackTrace();
				request.setAttribute("error", "An unexpected error occured");
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
	public void editProduct(@RequestParam("token") String token,
			@RequestParam("id") int id,
			@RequestParam("name") String name,
			@RequestParam("itemtype") int itemtype,
			@RequestParam("description") String description,
			@RequestParam("price") double price,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(isAuth(request,response,User.EDIT_PRODUCT)) {
			try {
				checkToken(token,request,response);
				ItemManager.editItem(id, itemtype, name, description, price);
				ActivityManager.addActivity("edited item " + id + ".");
				home(request,response);
				return;
			} catch(SQLException | MissingTokenException se) {
				logError(se);
				se.printStackTrace();
				request.setAttribute("error", "An unexpected error occured.");
				editProduct(id,request,response);
			}
		}
	}
	
	@ResponseBody
	@RequestMapping("/deleteProduct")
	public void deleteProduct(@RequestParam("token") String token,
			@RequestParam("id") int id,
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
			@RequestParam("token") String token,
			@RequestParam("authPassword") String authPassword,
			@RequestParam("role") int role,
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("confirmPassword") String confirmPassword,
			@RequestParam("fname") String fname,
			@RequestParam("mi") String mi,
			@RequestParam("lname") String lname,
			@RequestParam("email") String email,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(isAuth(request,response,User.CREATE_ACCOUNT)) {
			try {
				checkToken(token,request,response);
				User u = (User)request.getSession().getAttribute("sessionUser");
				u = UserManager.login(u.getUsername(), authPassword);
				if( u == null ) {
					request.setAttribute("error", "Authentication Failed.");
				} else {
					if(username.matches("^[A-Za-z0-9_-]+$") && fname.matches("^[A-Za-z ,.'-]+$") && 
							mi.matches("^[A-Za-z]{0,2}.?$") && lname.matches("^[A-Za-z ,.'-]+$") && 
							email.matches("^([-.a-zA-Z0-9_]+)@([-.a-zA-Z0-9_]+)[.]([a-zA-Z]{2,5})$") && 
							UserManager.checkPass(password) && password.equals(confirmPassword)) {
						UserManager.addUser(role, username, password, fname, mi, lname, email);
						ActivityManager.addActivity("created user " + username + ".");
						home(request,response);
					} else {
						logError(new Exception("Data validation failed on create account."));
						request.setAttribute("error","Failed to create account.");
						createAccount(request,response);
					}
					return;
				}
			} catch(SQLException se) {
				logError(se);
				se.printStackTrace();
				request.setAttribute("error", "An unexpected error occured.");
			} catch (MissingTokenException e) {
				// TODO Auto-generated catch block
				logError(e);
				createAccount(request,response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
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
			@RequestParam("token") String token,
			@RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword,
			@RequestParam("confirmPassword") String confirmPassword,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(isAuth(request, response, User.EDIT_ACCOUNT)) {
			User u = (User)request.getSession().getAttribute("sessionUser");
			try {
				checkToken(token,request,response);
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
			} catch (LockoutException e) {
				// TODO Auto-generated catch block
				if(e.getMinutes() == UserManager.LOCKOUT_MINUTES) {
					ActivityManager.addActivity("locked out " + u.getUsername() + "'s account." );
				} else {
					ActivityManager.addActivity("tried to login to " + u.getUsername() + "'s locked account." );
				}
				logout(request,response);
				return;
			} catch (ExpiredAccountException e) {
				// TODO Auto-generated catch block
				ActivityManager.addActivity("tried to login to " + u.getUsername() + "'s expired account.");
				logout(request,response);
				return;
			} catch (AuthenticationException e) {
				// TODO Auto-generated catch block
				ActivityManager.addActivity("failed to login to " + u.getUsername() + "'s account.");
				request.setAttribute("error", "Authentication Failed");
			} catch (MissingTokenException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("error", "An unexpected error occured.");
			} 
			request.getRequestDispatcher("WEB-INF/view/editAccount.jsp").forward(request, response);
		}
	}
}
