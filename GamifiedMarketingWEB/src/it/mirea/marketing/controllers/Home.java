package it.mirea.marketing.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

import it.mirea.marketing.services.ProductOfTheDayService;

import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine; 

@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "it.mirea.marketing.services/ProductOfTheDayService")
	private ProductOfTheDayService POTDService;
	private java.sql.Date sqlDate;
	@Resource UserTransaction tx;
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
		List<String> productNameImage = null;
		Map<String, List<String>> questionResponses = null;
		try {
			productNameImage = POTDService.getNameImage(POTDService.todayProductOfTheDay());//TODO: check null
			ctx.setVariable("pOTDName", productNameImage.get(0));
			ctx.setVariable("pOTDImage", productNameImage.get(1));
			LocalDate now = LocalDate.now();
		
			sqlDate = java.sql.Date.valueOf(now);
			questionResponses = POTDService.getQuestionsResponses(sqlDate);
			ctx.setVariable("questionResponses", questionResponses);
		
		} catch (Exception e) {
			
			if (productNameImage == null) {	
				ctx.setVariable("pOTDName", "Today there is no Product of the day");
				BufferedImage no_img = ImageIO.read(new File("E:\\eclipse-workspace\\Polimi_DB2_NRS\\assest\\noProduct.png"));
				ctx.setVariable("pOTDImage", no_img);
			}
			if (questionResponses == null) {
				Map<String, List<String>> no_reviews = new HashMap<String, List<String>>();
				no_reviews.put("No reviews", null);
				ctx.setVariable("questionResponses", no_reviews);
			}
		}
		templateEngine.process(path, ctx, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
