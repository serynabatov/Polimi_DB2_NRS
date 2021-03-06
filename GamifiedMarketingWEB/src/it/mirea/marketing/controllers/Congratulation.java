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

import it.mirea.marketing.entities.ProductOfTheDay;
import it.mirea.marketing.services.PagingService;
import it.mirea.marketing.services.ProductOfTheDayService;


@WebServlet("/Questionnaire/Congratulation")
public class Congratulation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	private String age;
	private String sex;
	private String expertise;
	private String userID;
	private String POTDid;
	private String button;
	@EJB(name = "it.polimi.db2.album.services/ProductOfTheDayService")
	private ProductOfTheDayService POTDService;

    public void init() throws ServletException {
    	ServletContext servletContext = getServletContext();
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String path = "/WEB-INF/congrats.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		templateEngine.process(path, ctx, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String path = "/WEB-INF/congrats.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		try {
			age = StringEscapeUtils.escapeJava(request.getParameter("age"));
			sex = StringEscapeUtils.escapeJava(request.getParameter("gender"));
			expertise = StringEscapeUtils.escapeJava(request.getParameter("exp_lvl"));
			userID = StringEscapeUtils.escapeJava(request.getParameter("userID"));
			POTDid = StringEscapeUtils.escapeJava(request.getParameter("POTDid"));
			button = StringEscapeUtils.escapeJava(request.getParameter("button"));
			
			if (age == null || sex == null || expertise == null || age.isEmpty() || sex.isEmpty() || expertise.isEmpty()
					|| userID == null || POTDid == null || userID.isEmpty() || POTDid.isEmpty() || button.isEmpty()
					|| button.isEmpty()) {
				throw new Exception("Missing or empty credential value");
			}
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
			return;
		}
		
		if (button.equals("cancel")) {
			path = getServletContext().getContextPath() + "/Home";     				
			response.sendRedirect(path);			
		} else {
			PagingService pagingService = (PagingService) request.getSession().getAttribute("pagingService");
		    pagingService.statQuestion(Integer.parseInt(age), Boolean.parseBoolean(sex), Integer.parseInt(expertise), Integer.parseInt(userID), Integer.parseInt(POTDid));
		
		    ProductOfTheDay p = POTDService.todayProductOfTheDay();
		    
		    pagingService.submit(Integer.parseInt(userID), p.getProductOTD());
		    templateEngine.process(path, ctx, response.getWriter());
		}

	}

}
