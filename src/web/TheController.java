package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Cart;
import model.Item;
import model.Review;
import model.SaleMapping;
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
	
	private String genToken(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User u = restoreSession(request,response);
		String genHash = null;
		if( u != null ) {
			genHash = genHash(u,request.getRemoteAddr());
			request.getSession().setAttribute("sessionToken",genHash); 
		}
		return genHash;
	}
	
	private User restoreSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User u = (User)request.getSession().getAttribute("sessionUser");
		restoreToken(request,response);
		System.out.println(u);
		if( u == null ) {
			Cookie[] cookies = request.getCookies();
			if( cookies != null ) {
				boolean foundCookie = false;
				for(Cookie c : cookies) {
					if( c.getName().equals("tlSessionToken") ) {
						foundCookie = true;
						try {
							int dollar = c.getValue().indexOf('$');
							if( dollar == -1 ) {
								u = null;
								c.setMaxAge(0);
								response.addCookie(c);
								logoutUser(request,response);
								((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("had an invalid cookie and was logged out.");
								return null;
							}
							u = UserManager.getUser(Integer.parseInt(c.getValue().substring(0,dollar)));
							if( u != null ) {
								String genHash = genHash(u,request.getRemoteAddr());
								if( genHash.equals(c.getValue())) {
									request.getSession().invalidate();
									request.getSession(true).setAttribute("sessionUser",u);
									request.getSession().setAttribute("auditor", new ActivityManager(u,request));
									request.getSession().setAttribute("sessionToken",genHash);
									((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("refreshed their session.");
								} else {
									u = null;
									c.setMaxAge(0);
									response.addCookie(c);
									logoutUser(request,response);
									((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("had an invalid cookie and was logged out.");
								}
							} else {
								request.getSession().setAttribute("auditor", new ActivityManager(request));
							}
						} catch(SQLException se) {
							logError(se,request);
							se.printStackTrace();
						}
						break;
					}
				}
				if( !foundCookie ) {
					request.getSession().setAttribute("auditor", new ActivityManager(request));
				}
			}
		} else {
			if( u.isExpired() ) {
				((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("'s session expired.");
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
		if(token == null || !sessionToken.equals(token)) {
			throw new MissingTokenException();
		}
	}
	
	private void logError(Exception e,HttpServletRequest request){
		((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("ran into the error " + e.getMessage() + ".");
	}
	
	private boolean isAuth(HttpServletRequest request, HttpServletResponse response, String privilege) throws ServletException, IOException {
		User u = restoreSession(request,response);
		if( u == null ) {
			((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("tried to access " + privilege + " and failed.");
			response.sendRedirect("/SECURDE-MP/.");
		} else {
			if( u.isAuth(privilege) ) {
				return true;
			} else {
				((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("tried to access " + privilege + " and failed.");
				response.sendRedirect("/SECURDE-MP/.");
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
					logError(se,request);
					se.printStackTrace();
				}
				request.getRequestDispatcher("WEB-INF/view/admin-home.jsp").forward(request, response);
				break;
			case 2:
				Item[] items;
				try {
					items = ItemManager.getAllItems(null, null, 0, 25,null,null,null);
					if( items.length == 0 ) {
						items = null;
					}
					request.setAttribute("products",items);
				} catch (SQLException e) {
					logError(e,request);
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.getRequestDispatcher("WEB-INF/view/pm-home.jsp").forward(request, response);
				break;
			case 3:
				try {
					double total = ItemManager.getTotalSales();
					SaleMapping[] byType = ItemManager.getTotalSalesByType();
					SaleMapping[] byItem = ItemManager.getTotalSalesPerItem();
					request.setAttribute("total", total);
					request.setAttribute("byType", byType);
					request.setAttribute("byItem", byItem);
					request.getRequestDispatcher("WEB-INF/view/am-home.jsp").forward(request, response);
					break;
				} catch(SQLException se) {
					logError(se,request);
					se.printStackTrace();
				}
			case 1:
			default:
				request.getRequestDispatcher("WEB-INF/view/index.jsp").forward(request, response);
			}
		}
	}
	
	@ResponseBody
	@RequestMapping("/login")
	public void login(@RequestParam(value="token",required=false) String token,
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User u = restoreSession(request,response);
		if( u == null) {
			try {
				checkToken(token,request,response);
				u = UserManager.login(username, password);
				if( u != null ) {
					request.getSession().invalidate();
					request.getSession(true).setAttribute("sessionUser", u);
					String genHash = genToken(request,response);
					Cookie c = new Cookie("tlSessionToken",genHash);
					c.setMaxAge(User.SESSION_EXPIRY * 60);
					c.setSecure(true);
					c.setHttpOnly(true);
					response.addCookie(c);
					request.getSession().setAttribute("auditor", new ActivityManager(u,request));
					((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("logged in.");
				} else {
					request.getSession().setAttribute("error","Invalid username/password combination");
					request.getSession().setAttribute("prompt",true);
				}
			} catch(SQLException se) {
				logError(se,request);
				se.printStackTrace();
			} catch (LockoutException e) {
				// TODO Auto-generated catch block
				if(e.getMinutes() == UserManager.LOCKOUT_MINUTES) {
					((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("locked out " + username + "'s account." );
				} else {
					((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("tried to login to " + username + "'s locked account." );
				}
				request.getSession().setAttribute("error", "Account has been locked. Try again later.");
				request.getSession().setAttribute("prompt",true);
			} catch (ExpiredAccountException e) {
				// TODO Auto-generated catch block
				((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("tried to login to " + username + "'s expired account.");
				request.getSession().setAttribute("error", "Invalid username/password combination");
				request.getSession().setAttribute("prompt",true);
			} catch (AuthenticationException e) {
				// TODO Auto-generated catch block
				((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("failed to login to " + username + "'s account.");
				request.getSession().setAttribute("error", "Invalid username/password combination");
				request.getSession().setAttribute("prompt",true);
			} catch (MissingTokenException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.getSession().setAttribute("error", "An unexpected error occured.");
				request.getSession().setAttribute("prompt",true);
			} 
		}
		response.sendRedirect("/SECURDE-MP/.");
	}
	
	@RequestMapping(value="register",method = RequestMethod.GET)
	public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User u = restoreSession(request, response);
		if( u != null ) {
			request.getSession().setAttribute("error", "You can't register if you are already logged in!");
			request.getSession().setAttribute("prompt",true);
			response.sendRedirect("/SECURDE-MP/.");
		} else {
			request.getRequestDispatcher("WEB-INF/view/register.jsp").forward(request, response);
		}
	}
	
	@ResponseBody
	@RequestMapping("checkUsername")
	public void checkUsername(@RequestParam(value="token",required=false) String token,
							@RequestParam("username") String username,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			checkToken(token,request,response);
			response.getWriter().print(UserManager.getUser(username) == null);
		} catch (MissingTokenException e) {
			// TODO Auto-generated catch block
			((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("had an invalid token on check username.");
			response.getWriter().print("false");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logError(e,request);
			response.getWriter().print("false");
		}
		
	}
	
	@ResponseBody
	@RequestMapping(value="register",method = RequestMethod.POST)
	public void register(@RequestParam(value="token",required=false) String token,
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
			request.getSession().setAttribute("error", "You can't register if you are already logged in!");
			request.getSession().setAttribute("prompt",true);
			response.sendRedirect("/SECURDE-MP/.");
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
					if(UserManager.addUser(username, password, fname, mi, lname, email, billHouseNo, billStreet, billSubd, billCity, billPostCode, billCountry, shipHouseNo, shipStreet, shipSubd, shipCity, shipPostCode, shipCountry)) {
						login(token,username,password,request,response);
						((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("registered their account.");
					} else {
						((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("registered an existing account.");
						request.getSession().setAttribute("error","That username is already in use.");
						request.getSession().setAttribute("prompt",true);
						response.sendRedirect("/SECURDE-MP/register");
					}
				} else {
					logError(new Exception("Data validation failed on register."),request);
					request.getSession().setAttribute("error","Failed to register account.");
					request.getSession().setAttribute("prompt",true);
					response.sendRedirect("/SECURDE-MP/register");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logError(e,request);
				request.getSession().setAttribute("error","An unexpected error occured.");
				request.getSession().setAttribute("prompt",true);
				response.sendRedirect("/SECURDE-MP/register");
			} catch (MissingTokenException e) {
				// TODO Auto-generated catch block
				logError(e,request);
				request.getSession().setAttribute("error","An unexpected error occured.");
				request.getSession().setAttribute("prompt",true);
				response.sendRedirect("/SECURDE-MP/register");
			} 
		}
	}
	
	@RequestMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User u = restoreSession(request,response);
		if( u != null) {
			logoutUser(request,response);
		}
		homePage(null,request,response);
	}
	
	public void logoutUser(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("logged out.");
		request.getSession().invalidate();
		Cookie[] cookies = request.getCookies();
		for(Cookie c : cookies) {
			if( c.getName().equals("tlSessionToken") ) {
				c.setMaxAge(0);
				response.addCookie(c);
			}
		}
		request.getSession().setAttribute("auditor", new ActivityManager(request));
		request.getSession(true);
		restoreToken(request,response);
	}
	
	@RequestMapping("search")
	public void search(@RequestParam(value="type",required=false) Integer type, 
			@RequestParam(value="query",required=false) String query, 
			@RequestParam(value="start",required=false) String startS,
			@RequestParam(value="minRange",required=false) String minRangeS,
			@RequestParam(value="maxRange",required=false) String maxRangeS,
			@RequestParam(value="ratings[]",required=false) String[] ratingsS,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User u = restoreSession(request, response);
		if( u == null ) {
			request.getSession().setAttribute("auditor", new ActivityManager(request));
		}
		Integer start = null;
		if( startS == null ) {
			start = 0;
		} else {
			try {
				start = Integer.parseInt(startS);
			} catch(NumberFormatException nfe) {
				start = 0;
			}
		}
		
		if( type != null && type.intValue() == 0 ) {
			type = null;
		}
		try {
			Double minRange = null;
			if( minRangeS != null ) {
				try {
					minRange = Double.parseDouble(minRangeS);
				} catch(NumberFormatException nfe ) {}
			}
			Double maxRange = null;
			if(maxRangeS != null) {
				try {
					maxRange = Double.parseDouble(maxRangeS);
				} catch(NumberFormatException nfe ) {}
			}
			
			boolean error = false;
			int[] ratings = null;
			if( ratingsS != null ) {
				ratings = new int[ratingsS.length];
				for(int i = 0; !error && i < ratingsS.length; i++) {
					if( ratingsS[i] != null && ratingsS[i].matches("^[1-5]$") ) {
						ratings[i] = Integer.parseInt(ratingsS[i]);
					} else {
						error = true;
					}
					System.out.println(ratingsS[i] + " " + ratingsS[i].matches("^[1-5]$") + " " + error);
				}
			}
			
			if(!error && (minRange == null || maxRange == null || minRange < maxRange)) {
				Item[] items = ItemManager.getAllItems(type,query,start,0,minRange,maxRange,ratings);
				request.setAttribute("products", items);
				request.setAttribute("type",type == null ? 0 : type);
				request.setAttribute("query",query);
//				request.setAttribute("start",start);
//				request.setAttribute("more", items.length == 26 ? true : false);
				((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("searched for \"" + query + "\" of type " + type + ".");
				request.getRequestDispatcher("WEB-INF/view/search.jsp").forward(request,response);
			} else {
				((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("ran into data validation error on search.");
				request.getSession().setAttribute("error","An unexpected error occured.");
				request.getSession().setAttribute("prompt",true);
				search(type,query,"0",null,null,null,request,response);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logError(e,request);
			e.printStackTrace();
			home(request,response);
		}
	}
	
	@RequestMapping("viewProduct")
	public void viewProduct(@RequestParam("id") int id,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User u = restoreSession(request, response);
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
			request.setAttribute("review", r);
			request.setAttribute("canReview", canReview);
			((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("viewed item " + id + ": " + i.getName() + ".");
			request.getRequestDispatcher("WEB-INF/view/viewProduct.jsp").forward(request,response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logError(e,request);
			response.sendRedirect("/SECURDE-MP/.");
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
	public void addToCart(@RequestParam(value="token",required=false) String token,
			@RequestParam("productId") int productId,
			@RequestParam("quantity") int quantity,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if( isAuth(request,response,User.PURCHASE_PRODUCT)) {
			Cart c = refreshCart(request);
			try {
				checkToken(token,request,response);
				if( quantity > 0 && quantity <= 2000 ) {
					try {
						Item i = ItemManager.getItem(productId);
						if( i != null ) {
							c.addPurchase(ItemManager.getItem(productId), quantity);
							((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("added " + quantity + " instances of item " + productId + " to their cart.");
							response.sendRedirect("/SECURDE-MP/shoppingCart");
						} else {
							request.getSession().setAttribute("error","Failed to add item to cart");
							request.getSession().setAttribute("prompt",true);
							((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("tried to add a nonexistent item to their cart.");
							response.sendRedirect("/SECURDE-MP/viewProduct?id=" + productId);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						if( e instanceof SQLException ) {
							e.printStackTrace();
							logError(e,request);
							request.getSession().setAttribute("error","Failed to add item to cart");
							request.getSession().setAttribute("prompt",true);
							response.sendRedirect("/SECURDE-MP/viewProduct?id=" + productId);
						} else {
							((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("added too many instances of item " + productId + " to their cart.");
							request.getSession().setAttribute("error","You can't add any more of that item.");
							request.getSession().setAttribute("prompt",true);
							response.sendRedirect("/SECURDE-MP/viewProduct?id=" + productId);
						}
					}
				} else {
					((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("added too many instances of item " + productId + " to their cart.");
					request.getSession().setAttribute("error","Data validation error.");
					request.getSession().setAttribute("prompt",true);
					response.sendRedirect("/SECURDE-MP/viewProduct?id=" + productId);
				}
			} catch (MissingTokenException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logError(e,request);
				request.getSession().setAttribute("error","Failed to add item to cart");
				request.getSession().setAttribute("prompt",true);
				response.sendRedirect("/SECURDE-MP/viewProduct?id=" + productId);
			} 
		}
	}
	
	@RequestMapping("deleteCartItem")
	@ResponseBody
	public void deleteCartItem(@RequestParam(value="token",required=false) String token,
			@RequestParam("productId") int productId,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if( isAuth(request,response,User.PURCHASE_PRODUCT)) {
			Cart c = refreshCart(request);
			try {
				checkToken(token,request,response);
				c.deleteItem(productId);
				((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("deleted item " + productId + " from their cart.");
				response.getWriter().print((new DecimalFormat("#,##0.00")).format(c.getTotal()));
			} catch (MissingTokenException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logError(e,request);
				((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("had an invalid token on delete cart item.");
				response.getWriter().print("false");
				viewProduct(productId,request,response);
			} 
		} else {
			response.getWriter().print("null");
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
				request.getSession().setAttribute("error", "Cart is empty.");
				request.getSession().setAttribute("prompt",true);
				request.getRequestDispatcher("WEB-INF/view/shoppingCart.jsp").forward(request,response);
			}
		}
	}
	
	@ResponseBody
	@RequestMapping(value="checkout",method=RequestMethod.POST)
	public void checkout(@RequestParam(value="token",required=false) String token,
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
						request.getSession().setAttribute("message", "Transaction Successful");
						request.getSession().setAttribute("prompt",true);
						((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("checked out their cart with a total of " + c.getTotal() + ".");
						c.clear();
						response.sendRedirect("/SECURDE-MP/.");
					} else {
						logError(new Exception("Data validation failed on checkout."),request);
						request.getSession().setAttribute("error","Failed to checkout.");
						request.getSession().setAttribute("prompt",true);
						response.sendRedirect("/SECURDE-MP/checkout");
					}
				} else {
					request.getSession().setAttribute("error", "Cart is empty.");
					request.getSession().setAttribute("prompt",true);
					response.sendRedirect("/SECURDE-MP/shoppingCart");
				}
			} catch (MissingTokenException | SQLException e) {
				logError(e,request);
				e.printStackTrace();
				request.getSession().setAttribute("error", "An unexpected error occured.");
				request.getSession().setAttribute("prompt",true);
				response.sendRedirect("/SECURDE-MP/checkout");
			}
		}
	}
	
	@ResponseBody
	@RequestMapping("review")
	public void review(@RequestParam(value="token",required=false) String token,
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
							Item i = ItemManager.getItem(prodId);
							response.getWriter().print(i.getRating());
							((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("reviewed item " + prodId + ".");
						} else {
							logError(new Exception("Data validation failed on review product."),request);
							response.getWriter().print("Failed to review product.");
						}
					} else {
						response.getWriter().print("Please purchase this item before reviewing.");
						((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("tried to review item " + prodId + " but hasn't purchased it yet.");
					}
				} else {
					ItemManager.updateReview(u.getId(), prodId, rating, review);
					((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("updated their review of item " + prodId + ".");
					Item i = ItemManager.getItem(prodId);
					response.getWriter().print(i.getRating());
				}
			} catch (MissingTokenException | SQLException e) {
				logError(e,request);
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
			@RequestParam(value="token",required=false) String token,
			@RequestParam("name") String name,
			@RequestParam("itemtype") int itemtype,
			@RequestParam("description") String description,
			@RequestParam("price") double price,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(isAuth(request,response,User.ADD_PRODUCT)) {
			try {
				checkToken(token,request,response);
				if(name.matches( "^[A-Za-z0-9.,! \\-'&]+$") && ("" + itemtype).matches("^[1-4]$") &&  description.length() > 0 && 
						("" + price).matches( "^[0-9]+([.][0-9]+)?|[0-9]*[.][0-9]+$")) {
						ItemManager.addItem(itemtype,name,description,price);
						((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("added product " + name + ".");
						response.sendRedirect("/SECURDE-MP/.");					
				} else {
					request.getSession().setAttribute("error", "Failed to add product.");
					request.getSession().setAttribute("prompt",true);
					logError(new Exception("Data validation failed on add product."),request);
					response.sendRedirect("/SECURDE-MP/addProduct");
				}
				return;
			} catch(SQLException | MissingTokenException se) {
				logError(se,request);
				se.printStackTrace();
				request.getSession().setAttribute("error", "An unexpected error occured");
				request.getSession().setAttribute("prompt",true);
			}
		}
		response.sendRedirect("/SECURDE-MP/addProduct");
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
					request.getSession().setAttribute("error", "Invalid Item Id");
					request.getSession().setAttribute("prompt",true);
					response.sendRedirect("/SECURDE-MP/.");
					return;
				}
				request.getRequestDispatcher("WEB-INF/view/editProduct.jsp").forward(request, response);
			} catch(SQLException se) {
				logError(se,request);
				se.printStackTrace();
				request.getSession().setAttribute("error", "An unexpected error occured.");
				request.getSession().setAttribute("prompt",true);
				response.sendRedirect("/SECURDE-MP/.");
			}
		}
	}
	
	@ResponseBody
	@RequestMapping(value="editProduct",method = RequestMethod.POST)
	public void editProduct(@RequestParam(value="token",required=false) String token,
			@RequestParam("id") int id,
			@RequestParam("name") String name,
			@RequestParam("itemtype") int itemtype,
			@RequestParam("description") String description,
			@RequestParam("price") double price,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(isAuth(request,response,User.EDIT_PRODUCT)) {
			try {
				checkToken(token,request,response);
				if(name.matches( "^[A-Za-z0-9.,! \\-'&]+$") && ("" + itemtype).matches("^[1-4]$") &&  description.length() > 0 && 
						("" + price).matches( "^[0-9]+([.][0-9]+)?|[0-9]*[.][0-9]+$")) {
					ItemManager.editItem(id, itemtype, name, description, price);
					((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("edited item " + id + ".");
					response.sendRedirect("/SECURDE-MP/.");
				} else {
					request.getSession().setAttribute("error", "Failed to edit product.");
					request.getSession().setAttribute("prompt",true);
					logError(new Exception("Data validation failed on edit product."),request);
					response.sendRedirect("/SECURDE-MP/editProduct?id=" + id);
				}
				return;
			} catch(SQLException | MissingTokenException se) {
				logError(se,request);
				se.printStackTrace();
				request.getSession().setAttribute("error", "An unexpected error occured.");
				request.getSession().setAttribute("prompt",true);
				response.sendRedirect("/SECURDE-MP/editProduct?id=" + id);
			}
		}
	}
	
	@ResponseBody
	@RequestMapping("/deleteProduct")
	public void deleteProduct(@RequestParam(value="token",required=false) String token,
			@RequestParam("id") int id,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		if(isAuth(request,response,User.DELETE_PRODUCT)) {
			try {
				ItemManager.deleteItem(id);
				pw.println("true");
				((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("deleted item " + id + ".");
				return;
			} catch(SQLException se) {
				logError(se,request);
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
			@RequestParam(value="token",required=false) String token,
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
					request.getSession().setAttribute("error", "Authentication Failed.");
					request.getSession().setAttribute("prompt",true);
				} else {
					if(("" + role).matches("^(2|3)$") && username.matches("^[A-Za-z0-9_-]+$") && fname.matches("^[A-Za-z ,.'-]+$") && 
							mi.matches("^[A-Za-z]{0,2}.?$") && lname.matches("^[A-Za-z ,.'-]+$") && 
							email.matches("^([-.a-zA-Z0-9_]+)@([-.a-zA-Z0-9_]+)[.]([a-zA-Z]{2,5})$") && 
							UserManager.checkPass(password) && password.equals(confirmPassword)) {
						if(UserManager.addUser(role, username, password, fname, mi, lname, email)) {
							((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("created user " + username + ".");
							response.sendRedirect("/SECURDE-MP/.");
						} else {
							((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("tried to create existing user " + username + ".");
							request.getSession().setAttribute("error","Username in use.");
							request.getSession().setAttribute("prompt",true);
							response.sendRedirect("/SECURDE-MP/createAccount");
						}
					} else {
						logError(new Exception("Data validation failed on create account."),request);
						request.getSession().setAttribute("error","Failed to create account.");
						request.getSession().setAttribute("prompt",true);
						response.sendRedirect("/SECURDE-MP/createAccount");
					}
					return;
				}
			} catch(SQLException se) {
				logError(se,request);
				se.printStackTrace();
				request.getSession().setAttribute("error", "An unexpected error occured.");
				request.getSession().setAttribute("prompt",true);
			} catch (MissingTokenException e) {
				// TODO Auto-generated catch block
				logError(e,request);
				request.getSession().setAttribute("error", "An unexpected error occured.");
				request.getSession().setAttribute("prompt",true);
				createAccount(request,response);
			} catch (LockoutException e) {
				// TODO Auto-generated catch block
				((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("locked their account.");
				request.getSession().setAttribute("auditor", new ActivityManager(request));
				request.getSession().setAttribute("error", "Account locked. Try again later.");
				request.getSession().setAttribute("prompt",true);
				logout(request,response);
				return;
			} catch (ExpiredAccountException e) {
				// TODO Auto-generated catch block
				((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("tried to log into an expired account.");
				request.getSession().setAttribute("auditor", new ActivityManager(request));
				request.getSession().setAttribute("error", "Invalid username/password combination");
				request.getSession().setAttribute("prompt",true);
				logout(request,response);
				return;
			} catch (AuthenticationException e) {
				// TODO Auto-generated catch block
				request.getSession().setAttribute("error", "Authentication Failed.");
				request.getSession().setAttribute("prompt",true);
			} 
			response.sendRedirect("/SECURDE-MP/createAccount");
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
			@RequestParam(value="token",required=false) String token,
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
						((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("changed their password.");
						response.sendRedirect("/SECURDE-MP/.");
						return;
					} else {
						request.getSession().setAttribute("error", "Passwords don't match.");
						request.getSession().setAttribute("prompt",true);
					}
				} else {
					request.getSession().setAttribute("error", "Authentication Failed");
					request.getSession().setAttribute("prompt",true);
				}
			} catch(SQLException se) {
				logError(se,request);
				se.printStackTrace();
				request.getSession().setAttribute("error", "An unexpected error occured.");
				request.getSession().setAttribute("prompt",true);
			} catch (LockoutException e) {
				// TODO Auto-generated catch block
				if(e.getMinutes() == UserManager.LOCKOUT_MINUTES) {
					((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("locked out " + u.getUsername() + "'s account." );
				} else {
					((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("tried to login to " + u.getUsername() + "'s locked account." );
				}
				logout(request,response);
				return;
			} catch (ExpiredAccountException e) {
				// TODO Auto-generated catch block
				((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("tried to login to " + u.getUsername() + "'s expired account.");
				logout(request,response);
				return;
			} catch (AuthenticationException e) {
				// TODO Auto-generated catch block
				((ActivityManager)request.getSession().getAttribute("auditor")).addActivity("failed to login to " + u.getUsername() + "'s account.");
				request.getSession().setAttribute("error", "Authentication Failed");
				request.getSession().setAttribute("prompt",true);
			} catch (MissingTokenException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.getSession().setAttribute("error", "An unexpected error occured.");
				request.getSession().setAttribute("prompt",true);
			} 
			response.sendRedirect("/SECURDE-MP/editAccount");
		}
	}
}
