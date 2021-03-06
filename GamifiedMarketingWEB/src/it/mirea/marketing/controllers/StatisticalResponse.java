package it.mirea.marketing.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.persistence.NonUniqueResultException;
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
import it.mirea.marketing.services.PagingService;
import it.mirea.marketing.services.ProductOfTheDayService;

@WebServlet("/Questionnaire/StatisticalResponse")
public class StatisticalResponse extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	private String[] productQuestions;
	Map<Integer, String> mapQuestions;
	List<String> listQuestions;
	private String questionKeys;
	private String[] userResponse; 
	private String userID;
	private String POTDid;
	@EJB(name = "it.mirea.marketing.services/ProductOfTheDayService")
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
		Map<Integer, String> productQuestions = POTDService.getMapQuestions(POTDService.todayProductOfTheDay());// todo check null
		User user = (User) request.getSession().getAttribute("user");
		ProductOfTheDay todayPOTD = POTDService.todayProductOfTheDay();//TODO: check null
		
		String path = "/WEB-INF/statistical_response.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		System.out.println(productQuestions.values());
		if (todayPOTD != null) {
			ctx.setVariable("userID", user.getUserId());
			ctx.setVariable("POTDid", todayPOTD.getProductOfTheDayId());

		}
		templateEngine.process(path, ctx, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		String path = "/WEB-INF/statistical_response.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		try {
			questionKeys = StringEscapeUtils.escapeJava(request.getParameter("prodQuestionsKeys"));
			userResponse = request.getParameterValues("userResponse");
			productQuestions = request.getParameterValues("prodQuestions");
			userID = StringEscapeUtils.escapeJava(request.getParameter("userID"));
			POTDid = StringEscapeUtils.escapeJava(request.getParameter("POTDid"));
			if (productQuestions == null || questionKeys == null || questionKeys.isEmpty() || userResponse == null 
					|| userID == null || userID.isEmpty() || POTDid == null ||  POTDid.isEmpty()) {
				throw new Exception("Missing or empty credential value");
			}
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
			return;
		}

		StringBuilder sb = new StringBuilder(questionKeys);//add to short answer
		sb.deleteCharAt(questionKeys.length() - 1);
		sb.deleteCharAt(0);
		List<String> keyList = Arrays.asList(sb.toString().split("\\s*,\\s*"));
		
		List<String> valuesList = Arrays.asList(userResponse);
		
		PagingService pagingService = (PagingService) request.getSession().getAttribute("pagingService");
		for(int i = 0; i < keyList.size(); i++){
	    	pagingService.answerQuestion(Integer.parseInt(keyList.get(i)), valuesList.get(i), Integer.parseInt(userID), Integer.parseInt(POTDid));
	    }
	    
		ctx.setVariable("userID", userID);
		ctx.setVariable("POTDid", POTDid);
		templateEngine.process(path, ctx, response.getWriter());
	}
}
