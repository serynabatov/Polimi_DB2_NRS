//package it.mirea.marketing.services;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.ejb.Stateless;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//import it.mirea.marketing.entities.ProductOfTheDay;
//import it.mirea.marketing.entities.Response;
//
//@Stateless
//public class ResponseService {
//
//	@PersistenceContext(unitName = "GamifiedMarketingEJB")
//	private EntityManager em;
//
//	public List<String> getAllResponses(ProductOfTheDay p) {
//	
//		List<Response> respList =  p.getResponses();
//		List<String> uText = new ArrayList<String>();
//	
//		for (Response response : respList) {
//			uText.add(response.getText());
//		}
//		
//		return uText;
//	}
//
//	
//	// here goes the transaction
//	
//}
