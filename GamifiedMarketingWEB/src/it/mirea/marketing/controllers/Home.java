package it.mirea.marketing.controllers;

import java.io.IOException;
import java.io.InputStream;

import java.util.List;

import javax.ejb.EJB;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import it.mirea.marketing.services.ProductOfTheDayService;

import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.TemplateEngine; 


@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
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
		String path = "/WEB-INF/home.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		List<String> productNameImage = POTDService.getNameImage(POTDService.todayProductOfTheDay());//TODO: check nullX
		List<String> productReviews = POTDService.getReviews(POTDService.todayProductOfTheDay());//TODO: check null
		List<String> productQuestions = POTDService.getQuestions(POTDService.todayProductOfTheDay());// todo check null
		if (productNameImage != null) {		
			ctx.setVariable("pOTDName", productNameImage.get(0));
			ctx.setVariable("pOTDImage", productNameImage.get(1));
			ctx.setVariable("pOTDReviews", productReviews);
			ctx.setVariable("productQuestions", productQuestions);
		}
		templateEngine.process(path, ctx, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
