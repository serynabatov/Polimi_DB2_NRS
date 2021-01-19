package it.mirea.marketing.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.transaction.UserTransaction;

import it.mirea.marketing.entities.Product;
import it.mirea.marketing.entities.ProductOfTheDay;
import it.mirea.marketing.entities.Questions;
import it.mirea.marketing.entities.Response;
import it.mirea.marketing.exceptions.CredentialsException;

@Stateless
public class ProductOfTheDayService {

	@PersistenceContext(unitName = "GamifiedMarketingApp")
	private EntityManager em;

	public ProductOfTheDayService() { }
	
	// get the list of the products and choose from them
	public Boolean createProductOfTheDayAsProduct(Date productOTD, int p, List<String> questions) {
		
		if (checkForTheDate(productOTD)) {
			System.out.println("test if");
			ProductOfTheDay pOTD = new ProductOfTheDay();
			pOTD.setProductOTD((java.sql.Date)productOTD);
			pOTD.setProductId(p);
			em.persist(pOTD);
			
			ProductOfTheDay pp = getPOTDT(productOTD);

			List<Questions> qList = new ArrayList<Questions>();
			
			for (String qText : questions) {
				Questions q = new Questions();
				q.setText(qText);

				q.setPOTDId(pp.getProductOfTheDayId());
				em.persist(q);
				qList.add(q);
			}
			
			return true;
		} else {
			return false;
		}
	}
	
	public List<Date> getAllDates() {
		LocalDate now = LocalDate.now();
		Date d = convertToDateViaSqlDate(now);
		List<ProductOfTheDay> pd = null;
		List<Date> dates = new ArrayList<Date>();
		
		try {
			pd = em.createNamedQuery("ProductOfTheDay.findNotPOTD",
														  ProductOfTheDay.class)
					                    .setParameter(1, d)
					                    .getResultList();
			
		} catch (PersistenceException e) {
			System.out.println(e);
		}
		
		for(ProductOfTheDay p : pd) {
			dates.add(p.getProductOTD());
		}
		
		return dates;
	}
	
	public List<Date> getInspection() {
		
		LocalDate now = LocalDate.now();
		Date d = convertToDateViaSqlDate(now);
		List<ProductOfTheDay> pd = null;
		List<Date> dates = new ArrayList<Date>();
		
		try {
			pd = em.createNamedQuery("ProductOfTheDay.inspection", ProductOfTheDay.class)
					.setParameter(1, d)
					.getResultList();
		} catch (PersistenceException e) {
			System.out.println(e);
		}
		
		for (ProductOfTheDay p : pd) {
			dates.add(p.getProductOTD());
		}
		return dates;
		
	}
	
	private Boolean checkForTheDate(Date productOTD) {
		List<ProductOfTheDay> p = em.createNamedQuery("ProductOfTheDay.findByDate", 
													  ProductOfTheDay.class)
				  .setParameter(1, (java.sql.Date)productOTD)
				  .getResultList();
		System.out.println(p);
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
	
	private ProductOfTheDay getPOTDT(Date d) {
	
		List<ProductOfTheDay> p = em.createNamedQuery("ProductOfTheDay.findByDate", ProductOfTheDay.class)
					   	 	 	    .setParameter(1, d)
					   	 	 	    .getResultList();
		
		if(p.size() == 1) {
			return p.get(0);	
		}
		else
			return null;
	}

	private ProductOfTheDay getPOTD(Date d) {
		
		em.clear();
		List<ProductOfTheDay> p = em.createNamedQuery("ProductOfTheDay.findByDate", ProductOfTheDay.class)
					   	 	 	    .setParameter(1, d)
					   	 	 	    .setHint("javax.persistence.cache.storeMode", "REFRESH")
					   	 	 	    .getResultList();
		
		if(p.size() == 1) {
			return p.get(0);	
		}
		else
			return null;
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
	
	private List<Questions> getQuestions(int id) {
		
		List<Questions> qList = null;
		
		try {
			em.clear();
			qList = em.createNamedQuery("Questions.findByProductId", Questions.class)
					  .setParameter(1, id)
		   	 	 	  .setHint("javax.persistence.cache.storeMode", "REFRESH")
					  .getResultList();
		} catch (PersistenceException e) {
			System.out.println("Mya");
		}
		
		return qList;
	}
	
	// TODO optimize it!!
	public Map<String, List<String>> getQuestionsResponses(Date d) {
		ProductOfTheDay p = getPOTD(d);
		
		List<Questions> questionObj = getQuestions(p.getProductOfTheDayId());
		
		//List<Questions> questionObj = p.getQuestions();

		Map<String, List<String>> questionsNicknames = new HashMap<String, List<String>>();
		
		Iterator<Questions> iter = questionObj.iterator();
		
		while(iter.hasNext()) {
			Questions q = (Questions) iter.next(); System.out.println(q.getText());
			List<Response> r = q.getResponses();
			List<String> st = new ArrayList<String>();
			
			Iterator<Response> set = r.iterator();
			while(set.hasNext()) {
				Response text = (Response) set.next(); System.out.println(text.getText());
				st.add(text.getText());
				System.out.println(st);
			}
			
			questionsNicknames.put(q.getText(), st);
		}
		
		return questionsNicknames;
	}
	
	public Map<Integer, String> getMapQuestions(ProductOfTheDay p) {
		//em.getEntityManagerFactory().getCache().evictAll();
		List<Questions> questionsObj = p.getQuestions();
		Map<Integer, String> mapQuestions = new HashMap<Integer, String>();
	    Iterator<Questions> iter = questionsObj.iterator();
	    
	    while(iter.hasNext()) {
	    	Questions q = (Questions) iter.next();
	    	mapQuestions.put(q.getQuestionId(), q.getText());
	    }
	    
	    return mapQuestions;
	}	
	public Boolean deleteProductOfTheDay(Date d) throws CredentialsException {
		
		ProductOfTheDay p = getPOTD(d);
		
		int id = p.getProductOfTheDayId();
		
		em.remove(p);
		
		ProductOfTheDay p1 = em.find(ProductOfTheDay.class, id);
		
		if (p1 == null)
			return true;
		else
			return false;
	}

}
