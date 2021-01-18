package it.mirea.marketing.controllers.admin;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import java.text.SimpleDateFormat;
import java.time.ZoneId;

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

import java.util.List;
import java.util.Locale;

import it.mirea.marketing.services.ProductOfTheDayService;
import it.mirea.marketing.services.ProductService;
import it.mirea.marketing.services.QuestionsService;
import it.mirea.marketing.entities.Product;
import it.mirea.marketing.entities.ProductOfTheDay;
import it.mirea.marketing.utils.ImageUtils;

@WebServlet("/Admin/Creation")
public class Creation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "it.mirea.marketing.services/ProductOfTheDayService")
	private ProductOfTheDayService POTDService;
	@EJB(name = "it.mirea.marketing.services/ProductService")
	private ProductService productService;
	@EJB(name = "it.mirea.marketing.services/QuestionsService")
	private QuestionsService questionsService;
	private int productId;
	private java.util.Date prodDate;
	private java.sql.Date sqlDate;
	private String buttonVal;

	public void init() throws ServletException {
		ServletContext servletContext = getServletContext();
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = "/WEB-INF/admin/admin_creation.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		List<Product> products = productService.getAll();
		ctx.setVariable("nullProducts", products);
		if (products != null) {		
			ctx.setVariable("products", products);
		}
		
		templateEngine.process(path, ctx, response.getWriter());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		try {
			productId = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("productId")));
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",  Locale.ENGLISH);
			prodDate = format.parse(StringEscapeUtils.escapeJava(request.getParameter("prodDate")));
			buttonVal = StringEscapeUtils.escapeJava(request.getParameter("button"));
       	  	
			if (productId == 0) {
				throw new Exception("Missing or empty credential value");
			}
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
			return;
		}
		
//		ProductOfTheDay pOTD = POTDService.todayProductOfTheDay();
//		pOTD.getPOTDDATE; // maybe if prodDate = productOTD_date = error.
		
		
		if(buttonVal.equals("Add row"))
		    System.out.println("BUTTON" + buttonVal);
		else if(buttonVal.equals("Submit")) {
			 System.out.println("BUTTON" + buttonVal);
		}

		
		sqlDate = new java.sql.Date(prodDate.getTime());
//		InputStream stream = new ByteArrayInputStream(pImage.getBytes(StandardCharsets.UTF_8));
//		System.out.print(ImageUtils.readImage(stream).toString());
		
		POTDService.createProductOfTheDayAsProduct(sqlDate, productId);	
//		questionsService.createQuestions(questions, productService.getProduct(productId));
		
	}

}
