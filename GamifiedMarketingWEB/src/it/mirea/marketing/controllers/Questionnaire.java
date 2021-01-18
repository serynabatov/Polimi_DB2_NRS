package it.mirea.marketing.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.mirea.marketing.services.ProductOfTheDayService;
import it.mirea.marketing.services.UserService;
import it.mirea.marketing.entities.ProductOfTheDay;
import it.mirea.marketing.entities.User;

@WebServlet("/Questionnaire")
public class Questionnaire extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "it.mirea.marketing.services/ProductOfTheDayService")
	private ProductOfTheDayService POTDService;
	@EJB(name = "it.mirea.marketing.services/UserService")
	private UserService userService;


	public void init() throws ServletException {
		ServletContext servletContext = getServletContext();
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<Integer, String> productQuestions = POTDService.getMapQuestions(POTDService.todayProductOfTheDay());// todo check null
		User user = (User) request.getSession().getAttribute("user");
		ProductOfTheDay todayPOTD = POTDService.todayProductOfTheDay();//TODO: check null
		
		String path = "/WEB-INF/questionnaire.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		if (todayPOTD != null) {
			ctx.setVariable("prodQuestionsKeys", productQuestions.keySet());
			ctx.setVariable("prodQuestionsValues", productQuestions.values());
			ctx.setVariable("prodQuestions", productQuestions);
			ctx.setVariable("userID", user.getUserId());
			ctx.setVariable("POTDid", todayPOTD.getProductOfTheDayId());

		}
		
		templateEngine.process(path, ctx, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
