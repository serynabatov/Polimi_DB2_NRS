package it.mirea.marketing.website.pages;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.persistence.NonUniqueResultException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.mirea.marketing.exceptions.CredentialsException;
import it.mirea.marketing.entities.OffensiveWords;
import it.mirea.marketing.entities.Product;
import it.mirea.marketing.entities.ProductOfTheDay;
//import it.mirea.marketing.services.UserService;
import it.mirea.marketing.services.ProductService;
import it.mirea.marketing.services.ProductOfTheDayService;


@WebServlet("/Home")
public class Home extends HttpServlet {
	
	@EJB(name = "it.mirea.marketing.services/ProductOfTheDayService")
	private ProductOfTheDayService POTDService;
//	
//	@EJB(name = "it.mirea.marketing.services/ProductService")
//	private ProductService productService;
	
	private static final long serialVersionUID = 1L;  
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().print(productOfTheDayService.getNameImage().toString());
//		try {
//			User user = userService.checkCredentials("alex", "alex");
//			response.getWriter().append(user.getUserName());
//		} catch (NonUniqueResultException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (CredentialsException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		//response.getWriter().print(userService.deleteUser(3));
		
		response.getWriter().print(POTDService.getNameImage(POTDService.findById(1)));;
		////productOfTheDayService.findById(1
		//response.getWriter().append("Served at: ").append(request.getContextPath());	
	}

}
