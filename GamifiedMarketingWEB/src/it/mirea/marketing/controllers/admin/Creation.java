package it.mirea.marketing.controllers.admin;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.text.SimpleDateFormat;

import javax.ejb.EJB;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import it.mirea.marketing.services.ProductOfTheDayService;
import it.mirea.marketing.services.ProductService;
import it.mirea.marketing.services.QuestionsService;
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
	@EJB(name = "it.mirea.marketing.services/QuestionsService")
	private QuestionsService questionsService;
	private String prodId;
	private java.util.Date prodDate;
	private java.sql.Date sqlDate;
	private String buttonAction;
	private String prodName;
	private String prodImage;
	private String numQuestion;
	private String[] prodQuestions;


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

		HttpSession session = request.getSession();
		numQuestion = (String) session.getAttribute("numQuestion");
			
		if(numQuestion != null) {
			ctx.setVariable("numQuestion", numQuestion);
		} else {
			ctx.setVariable("numQuestion", 1);
		}
		
		List<Product> products = productService.getAll();
		
		ctx.setVariable("nullProducts", products);
		if (products != null) {		
			ctx.setVariable("products", products);
		}
		
		templateEngine.process(path, ctx, response.getWriter());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = "/WEB-INF/admin/admin_creation.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		try {
			buttonAction = StringEscapeUtils.escapeJava(request.getParameter("buttonAction"));
			
			if (buttonAction == null  || buttonAction.isEmpty()) {
				throw new Exception("Missing button action");
			}
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
			return;
		}	
		
		if (buttonAction.equals("addProduct")) {
			System.out.println("addProduct");
			try {
				prodName = StringEscapeUtils.escapeJava(request.getParameter("prodName"));
				prodImage = StringEscapeUtils.escapeJava(request.getParameter("prodImage"));
				
				if (prodName == null || prodName.isEmpty() || prodImage == null || prodImage.isEmpty()) {
					throw new Exception("Missing or empty credential value");
				}
			} catch (Exception e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
				return;
			}
			
			BufferedImage img = null;
			try {
				img = ImageIO.read(new File(prodImage));
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			 ByteArrayOutputStream baos = new ByteArrayOutputStream();
			 ImageIO.write(img, "jpg", baos); //accept jpg image
			 byte[] bytedImg = baos.toByteArray();
			 
			productService.addProduct(prodName, bytedImg); //size

//			Part thumbFile = request.getPart("thumb");
//			InputStream thumbContent = thumbFile.getInputStream();
//			byte[] thumbByteArray = ImageUtils.readImage(thumbContent);
			
//			if (thumbByteArray.length == 0) {
//				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid photo parameters");
//			}
			
			path = getServletContext().getContextPath() + "/Admin/Creation";
			response.sendRedirect(path);
		}
		
		if (buttonAction.equals("addRow")) {
			System.out.println("addROW");
			try {
				numQuestion = StringEscapeUtils.escapeJava(request.getParameter("numQuestion"));

				if (numQuestion == null || numQuestion.isEmpty()) {
					throw new Exception("Missing or empty credential value");
				}
			} catch (Exception e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
				return;
			}
			
			path = getServletContext().getContextPath() + "/Admin/Creation";
			HttpSession session = request.getSession(false);//

			session.setAttribute("numQuestion", numQuestion);
			response.sendRedirect(path);
			
		}
		
		if (buttonAction.equals("addPOTD")) {
			System.out.print("addPOTD");
			try {
				prodId = StringEscapeUtils.escapeJava(request.getParameter("prodId"));
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",  Locale.ENGLISH);
				prodDate = format.parse(StringEscapeUtils.escapeJava(request.getParameter("prodDate")));
				prodQuestions = request.getParameterValues("prodQuestions");
				
				if (prodId == null || prodId.isEmpty()  || prodDate == null) {
					throw new Exception("Missing or empty credential value");
				}
			} catch (Exception e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
				return;
			}
			sqlDate = new java.sql.Date(prodDate.getTime());
			System.out.print("");
			for(int i = 0; i < prodQuestions.length; i++) {
	            System.out.println("i: " + i + " Quest: " + prodQuestions[i]);
	        }
			System.out.print("");
			POTDService.createProductOfTheDayAsProduct(sqlDate, Integer.parseInt(prodId), Arrays.asList(prodQuestions));
			path = getServletContext().getContextPath() + "/Admin";
			response.sendRedirect(path);
		}
		
	}

}
