package it.mirea.marketing.controllers;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringEscapeUtils;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.mirea.marketing.entities.User;
import it.mirea.marketing.services.UserService;
import it.mirea.marketing.exceptions.CredentialsException;
import it.mirea.marketing.services.UserService;
//import it.polimi.db2.album.services.QueryService;

import javax.persistence.NonUniqueResultException;

import javax.naming.*;

@WebServlet("/CheckLogin")
public class CheckLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "it.mirea.marketing.services/UserService")
	private UserService userService;
	private String path = null;
	private String usrn = null;
	private String pwd = null;
	private User user;
	String userPrivilege = null;

	public void init() throws ServletException {
		ServletContext servletContext = getServletContext();
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// obtain and escape params		
		try {
			usrn = StringEscapeUtils.escapeJava(request.getParameter("username"));
			pwd = StringEscapeUtils.escapeJava(request.getParameter("password"));
			if (usrn == null || pwd == null || usrn.isEmpty() || pwd.isEmpty()) {
				throw new Exception("Missing or empty credential value");
			}
		} catch (Exception e) {
			// for debugging only e.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
			return;
		}

		try {
			//query db to authenticate for user
			user = userService.checkCredentials(usrn, pwd);
		} catch (CredentialsException | NonUniqueResultException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not check credentials");
			return;
		}

		// If the user exists, add info to the session and go to home page, otherwise
		// show login page with error message
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		userPrivilege = userService.checkYourPrivilege(user.getUserId());
		if (user == null) {
			ctx.setVariable("errorMsg", "Incorrect username or password"); 
			templateEngine.process(path, ctx, response.getWriter());
		} else if (user != null && !userPrivilege.equals("user") || !userPrivilege.equals("admin")) {
			ctx.setVariable("errorMsg", "Unknown privilege");
			templateEngine.process(path, ctx, response.getWriter());
		} else {
			//QueryService qService = null;
			try {
				/*
				* We need one distinct EJB for each user. Get the Initial Context for the JNDI
				* lookup for a local EJB. Note that the path may be different in different EJB
				* environments. In IntelliJ use: ic.lookup(
				* "java:/openejb/local/ArtifactFileNameWeb/ArtifactNameWeb/QueryServiceLocalBean"
				* );
				*/
				InitialContext ic = new InitialContext();
				// Retrieve the EJB using JNDI lookup
				//qService = (QueryService) ic.lookup("java:/openejb/local/QueryServiceLocalBean");
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.getSession().setAttribute("user", user);
			//request.getSession().setAttribute("queryService", qService);

			switch (userPrivilege) {
	        case "user": path = getServletContext().getContextPath() + "/Home";     				
	            break;
	        case "admin": path = getServletContext().getContextPath() + "/AdminPanel";
	            break;
			}
			
			path = getServletContext().getContextPath() + "/Home";
			response.sendRedirect(path);			
	    }
	}

}
