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

import it.mirea.marketing.services.ProductOfTheDayService;
import it.mirea.marketing.services.ProductService;
import it.mirea.marketing.entities.Product;

import it.mirea.marketing.utils.ImageUtils;

@WebServlet("/Admin/Creation")
public class Creation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "it.mirea.marketing.services/ProductOfTheDayService")
	private ProductOfTheDayService POTDService;
	@EJB(name = "it.mirea.marketing.services/ProductService")
	private ProductService productService;
	private int productId;
	private java.util.Date pDate;
	private String pImage;
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
			SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
			pDate = format.parse(StringEscapeUtils.escapeJava(request.getParameter("pDate")));
			pImage = StringEscapeUtils.escapeJava(request.getParameter("pImage"));
			
//			pDate.toInstant()                  // Convert from legacy class `java.util.Date` (a moment in UTC) to a modern `java.time.Instant` (a moment in UTC).
//	        .atZone( ZoneId.of( "Africa/Tunis" ) )  // Adjust from UTC to a particular time zone, to determine a date. Instantiating a `ZonedDateTime`.
//	        .toLocalDate();  
//			java.sql.Date sqlDate = java.sql.Date.valueOf( todayLocalDate );
       	  	sqlDate = new java.sql.Date(pDate.getTime());
			    
			if (productId == 0) {
				throw new Exception("Missing or empty credential value");
			}
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
			return;
		}
		
		//InputStream stream = new ByteArrayInputStream(pImage.getBytes(StandardCharsets.UTF_8));
//		System.out.print(ImageUtils.readImage(stream).toString());
		POTDService.createProductOfTheDayAsProduct(sqlDate, productId);		
		
	}

}
