package web;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.owasp.esapi.codecs.Hex;

public class CSPFilter implements Filter {

	/** Configuration member to specify if web app use web fonts */
	public static final boolean APP_USE_WEBFONTS = true;

	/** Configuration member to specify if web app use videos or audios */
	public static final boolean APP_USE_AUDIOS_OR_VIDEOS = true;

	/** Configuration member to specify if filter must add CSP directive used by Mozilla (Firefox) */
	public static final boolean INCLUDE_MOZILLA_CSP_DIRECTIVES = true;

	/** List CSP HTTP Headers */
	private List<String> cspHeaders = new ArrayList<String>();

	/** Collection of CSP polcies that will be applied */
	private String policies = null;

	/** Used for Script Nonce */
	private SecureRandom prng = null;
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest httpRequest = ((HttpServletRequest) request);
		HttpServletResponse httpResponse = ((HttpServletResponse) response);

		String prompt = "" + httpRequest.getSession().getAttribute("prompt");
		if( prompt == null || !prompt.equals("true") ) { 
			httpRequest.getSession().setAttribute("error", null);
			httpRequest.getSession().setAttribute("message", null);
		} else {
			httpRequest.getSession().setAttribute("prompt", false);
			
		}
		httpResponse.addHeader("X-FRAME-OPTIONS", "sameorigin");
		httpResponse.addHeader("X-XSS-Protection", "1;mode=block");
		httpResponse.addHeader("X-Content-Type-Options", "nosniff");
		httpResponse.addHeader("Content-Type", "text/html; charset=utf-8");
		
		/* Step 1 : Detect if target resource is a Frame */
		// Customize here according to your context...
		boolean isFrame = false;

		/* Step 2 : Add CSP policies to HTTP response */
		StringBuilder policiesBuffer = new StringBuilder(this.policies);

		// If resource is a frame add Frame/Sandbox CSP policy
		if (isFrame) {
			// Frame + Sandbox : Here sandbox allow nothing, customize sandbox options depending on your app....
			policiesBuffer.append(";").append("frame-src 'self';sandbox");
			if (INCLUDE_MOZILLA_CSP_DIRECTIVES) {
				policiesBuffer.append(";").append("frame-ancestors 'self'");
			}
		}

		// Add Script Nonce CSP Policy
		// --Generate a random number
		String randomNum = new Integer(this.prng.nextInt()).toString();
		// --Get its digest
		MessageDigest sha;
		try {
			sha = MessageDigest.getInstance("SHA-1");
		}
		catch (NoSuchAlgorithmException e) {
			throw new ServletException(e);
		}
		byte[] digest = sha.digest(randomNum.getBytes());
		// --Encode it into HEXA
		String scriptNonce = Hex.encode(digest,true);
		policiesBuffer.append(";").append("script-nonce ").append(scriptNonce);
		// --Made available script nonce in view app layer
		httpRequest.setAttribute("CSP_SCRIPT_NONCE", scriptNonce);

		// Add policies to all HTTP headers
		for (String header : this.cspHeaders) {
			httpResponse.setHeader(header, policiesBuffer.toString());
		}

		/* Step 3 : Let request continue chain filter */
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		// Init secure random
		try {
			this.prng = SecureRandom.getInstance("SHA1PRNG");
		}
		catch (NoSuchAlgorithmException e) {
			throw new ServletException(e);
		}

		// Define list of CSP HTTP Headers
		this.cspHeaders.add("Content-Security-Policy");
		this.cspHeaders.add("X-Content-Security-Policy");
		this.cspHeaders.add("X-WebKit-CSP");

		// Define CSP policies
		// Loading policies for Frame and Sandboxing will be dynamically defined : We need to know if context use Frame
		List<String> cspPolicies = new ArrayList<String>();
		String originLocationRef = "'self'";
		// --Disable default source in order to avoid browser fallback loading using 'default-src' locations
		cspPolicies.add("default-src " + originLocationRef);
		// --Define loading policies for Scripts
		cspPolicies.add("script-src " + originLocationRef + " 'unsafe-inline' 'unsafe-eval'");
		if (INCLUDE_MOZILLA_CSP_DIRECTIVES) {
			cspPolicies.add("options inline-script eval-script");
			cspPolicies.add("xhr-src 'self'");
		}
		// --Define loading policies for Plugins
		cspPolicies.add("object-src " + originLocationRef);
		// --Define loading policies for Styles (CSS)
		cspPolicies.add("style-src " + originLocationRef + " 'unsafe-inline'");
		// --Define loading policies for Images
		cspPolicies.add("img-src " + originLocationRef);
		// --Define loading policies for Form
		cspPolicies.add("form-action " + originLocationRef);
		// --Define loading policies for Audios/Videos
		if (APP_USE_AUDIOS_OR_VIDEOS) {
			cspPolicies.add("media-src " + originLocationRef);
		}
		// --Define loading policies for Fonts
		if (APP_USE_WEBFONTS) {
			cspPolicies.add("font-src " + originLocationRef);
		}
		// --Define loading policies for Connection
		cspPolicies.add("connect-src " + originLocationRef);
		// --Define loading policies for Plugins Types
		cspPolicies.add("plugin-types application/pdf application/x-shockwave-flash");
		// --Define browser XSS filtering feature running mode
		cspPolicies.add("reflected-xss block");

		// Target formating
		this.policies = cspPolicies.toString().replaceAll("(\\[|\\])", "").replaceAll(",", ";").trim();
	}

}
