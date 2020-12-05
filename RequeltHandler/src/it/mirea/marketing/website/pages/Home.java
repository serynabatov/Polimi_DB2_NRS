package it.mirea.marketing.website.pages;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.ProductOfTheDayService;

@WebServlet("/HomePage")
public class Home extends HttpServlet {
	@EJB(name = "entities/ProductOfTheDayService")
	ProductOfTheDayService productOfTheDayService;
	
	private static final long serialVersionUID = 1L;  
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().print(productOfTheDayService.getNameImage().toString());
		response.getWriter().print(productOfTheDayService.findById(1));
		//response.getWriter().append("Served at: ").append(request.getContextPath());	
	}

}
