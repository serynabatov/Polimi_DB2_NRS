package it.mirea.marketing.controllers.admin;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

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
import it.mirea.marketing.entities.User;
import it.mirea.marketing.services.ProductOfTheDayService;
import it.mirea.marketing.services.UserService;


@WebServlet("/Admin/Inspection")
public class Inspection extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "it.mirea.marketing.services/ProductOfTheDayService")
	private ProductOfTheDayService POTDService;
	@EJB(name = "it.mirea.marketing.services/UserService")
	private UserService userService;
	private java.util.Date inspectionDate;
	private java.sql.Date sqlDate;
    

	public void init() throws ServletException {
		ServletContext servletContext = getServletContext();
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = "/WEB-INF/admin/admin_inspection_request.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

		templateEngine.process(path, ctx, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = "/WEB-INF/admin/admin_inspection_response.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",  Locale.ENGLISH);
			inspectionDate = format.parse(StringEscapeUtils.escapeJava(request.getParameter("inspectionDate")));
       	  	
			if (inspectionDate == null) {
				throw new Exception("Missing or empty credential value");
			}
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
			return;
		}
		sqlDate = new java.sql.Date(inspectionDate.getTime());
		List<String> canceledUsers = userService.getCanceled(1, sqlDate);
		List<String> submittedUsers = userService.getCanceled(2, sqlDate);
		Map<String, List<String>> questionResponses = POTDService.getQuestionsResponses(sqlDate);
		
		System.out.print(questionResponses);
		
		ctx.setVariable("nullCancel", canceledUsers);
		ctx.setVariable("nullSubmit", submittedUsers);
		ctx.setVariable("nullResponse", questionResponses);
		if (canceledUsers != null) {
			ctx.setVariable("canceledUsers", canceledUsers);
		} 
		
		if (submittedUsers != null) {
			ctx.setVariable("submittedUsers", submittedUsers);
		} 
		
		if (questionResponses != null) {
			ctx.setVariable("questionResponses", questionResponses);
		} 
		
		templateEngine.process(path, ctx, response.getWriter());
	}

}
