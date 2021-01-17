package it.mirea.marketing.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.mirea.marketing.entities.Product;
//import it.mirea.marketing.entities.Product;
import it.mirea.marketing.entities.ProductOfTheDay;
import it.mirea.marketing.entities.Questions;
import it.mirea.marketing.entities.Response;
import it.mirea.marketing.entities.User;

@Stateless
public class ProductOfTheDayService {

	@PersistenceContext(unitName = "GamifiedMarketingApp")
	private EntityManager em;
	
	public ProductOfTheDayService() { }
	
	// get the list of the products and choose from them
	public Boolean createProductOfTheDayAsProduct(Date productOTD, int p) {
		
		if (checkForTheDate(productOTD)) {
			ProductOfTheDay pOTD = new ProductOfTheDay();
			pOTD.setProductOTD((java.sql.Date)productOTD);
			pOTD.setProductId(p);
			em.persist(pOTD);
			return true;
		} else {
			return false;
		}
	}
	
	private Boolean checkForTheDate(Date productOTD) {
		List<ProductOfTheDay> p = em.createNamedQuery("ProductOfTheDay.findByDate", 
													  ProductOfTheDay.class)
				  .setParameter(1, productOTD)
				  .getResultList();
		if (p.size() == 0)
			return true;
		else
			return false;
	}
	
	// return the product of the day for today's date
	public ProductOfTheDay todayProductOfTheDay() {
		LocalDate now = LocalDate.now();
		List<ProductOfTheDay> p = em.createNamedQuery("ProductOfTheDay.findByDate", 
											           ProductOfTheDay.class)
							  .setParameter(1, convertToDateViaSqlDate(now))
							  .getResultList();
		
		if (p.size() == 1)
			return p.get(0);
		else
			return null;
	}
	
	//create product of the day and then as a product (maybe do as a trigger)
	public Boolean createProductOfTheDayThenProduct(int productOfTheDayId, Date productOTD,
			int productId, String productName, String linkImage, byte[] image) {
		if (checkForTheDate(productOTD)) {
			Product p = new Product();
			p.setProductId(productId);
			p.setProductName(productName);
			p.setImage(image);
			p.setLinkImage(linkImage);
			em.persist(p);
			ProductOfTheDay pOTD = new ProductOfTheDay();
			pOTD.setProductOfTheDay(productOfTheDayId);
			pOTD.setProductOTD((java.sql.Date)productOTD);
			pOTD.setProductId(productId);
			pOTD.setProduct(p); 
			em.persist(pOTD);
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean createProductOfTheDayThenProduct(int productOfTheDayId, Date productOTD,
			int productId, String productName, byte[] image) {
		if (checkForTheDate(productOTD)) {
			Product p = new Product();
			p.setProductId(productId);
			p.setProductName(productName);
			p.setImage(image);
			em.persist(p);
			ProductOfTheDay pOTD = new ProductOfTheDay();
			pOTD.setProductOfTheDay(productOfTheDayId);
			pOTD.setProductOTD((java.sql.Date)productOTD);
			pOTD.setProductId(productId);
			pOTD.setProduct(p); 
			em.persist(pOTD);
			return true;
		} else {
			return false;
		}
	}

	public Boolean createProductOfTheDayThenProduct(int productOfTheDayId, Date productOTD,
			int productId, String productName, String linkImage) {
		if (checkForTheDate(productOTD)) {
			Product p = new Product();
			p.setProductId(productId);
			p.setProductName(productName);
			p.setLinkImage(linkImage);
			em.persist(p);
			ProductOfTheDay pOTD = new ProductOfTheDay();
			pOTD.setProductOfTheDay(productOfTheDayId);
			pOTD.setProductOTD((java.sql.Date)productOTD);
			pOTD.setProductId(productId);
			pOTD.setProduct(p); 
			em.persist(pOTD);
			return true;
		} else {
			return false;
		}
	}
	
	private Date convertToDateViaSqlDate(LocalDate dateToConvert) {
	    return java.sql.Date.valueOf(dateToConvert);
	}
	
	public ProductOfTheDay findById(int id) {
		return em.find(ProductOfTheDay.class, id);
	}
	
	private Product findByProductId(int id) {
		return em.find(Product.class, id);
	}
			
	// return the name, the image
	public List<String> getNameImage(ProductOfTheDay p) {		
		List<String> m = new ArrayList<String>(2);
		
		int productId = p.getProductId();
		Product p1 = findByProductId(productId);
						
		m.add(p1.getProductName());
		m.add(p1.getImage());
			
		return m;
	}
	
	public List<String> getReviews(ProductOfTheDay p) {

		List<Response> responsesObj = p.getResponses();
		List<String> responses = new ArrayList<String>();

	    Iterator<Response> iter = responsesObj.iterator();
		
		while (iter.hasNext()) {
			Response response = (Response) iter.next();
			responses.add(response.getText());
		}
		return responses;
	}

	public List<String> getQuestions(ProductOfTheDay p) {

		List<Questions> questionsObj = p.getQuestions();
		List<String> questions = new ArrayList<String>();
	    Iterator<Questions> iter = questionsObj.iterator();

		while (iter.hasNext()) {
			Questions q = (Questions) iter.next();
			questions.add(q.getText());
		}

		return questions;
	}
	
	// TODO optimize it!!
	public Map<String, Set<String>> getQuestionsResponses(ProductOfTheDay p) {
		
		List<Questions> questionObj = p.getQuestions();
		Map<String, Set<String>> questionsNicknames = new HashMap<String, Set<String>>();
		
		Iterator<Questions> iter = questionObj.iterator();
		
		while(iter.hasNext()) {
			Questions q = (Questions) iter.next();
			Set<Response> r = q.getResponses();
			HashSet<String> st = new HashSet<String>();
			
			Iterator<Response> set = r.iterator();
			while(set.hasNext()) {
				Response text = (Response) set.next();
				st.add(text.getText());
			}
			
			questionsNicknames.put(q.getText(), st);
		}
		
		return questionsNicknames;
	}
	
	public Map<Integer, String> getMapQuestions(ProductOfTheDay p) {
		
		List<Questions> questionsObj = p.getQuestions();
		Map<Integer, String> mapQuestions = new HashMap<Integer, String>();
	    Iterator<Questions> iter = questionsObj.iterator();
	    
	    while(iter.hasNext()) {
	    	Questions q = (Questions) iter.next();
	    	mapQuestions.put(q.getQuestionId(), q.getText());
	    }
	    
	    return mapQuestions;
	}

}
