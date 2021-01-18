package it.mirea.marketing.services;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import it.mirea.marketing.entities.Canceled;
import it.mirea.marketing.entities.OffensiveWords;
import it.mirea.marketing.entities.ProductOfTheDay;
import it.mirea.marketing.entities.Response;
import it.mirea.marketing.entities.StatisticalResponse;
import it.mirea.marketing.entities.User;
import it.mirea.marketing.exceptions.CredentialsException;

@Stateful
public class PagingService {
	
	@PersistenceContext(unitName = "GamifiedMarketingApp", type = PersistenceContextType.EXTENDED)
	private EntityManager em;
	private List<Response> responses = new ArrayList<Response>();
	private StatisticalResponse stat = null;

	public PagingService() { }
	
	public void answerQuestion(int questionId, String text, int userId, int pOTDid) {
		Response resp = new Response();
		
		resp.setPOTDId(pOTDid);
		resp.setQuestionId(questionId);
		resp.setUserId(userId);
		resp.setText(text);
		
		this.responses.add(resp);
	}
	
	public void deleteQuestion(int questionId) {
		
		Iterator<Response> itr = responses.iterator();
		while (itr.hasNext()) {
			Response r = (Response)itr.next();
			if (r.getQuestionId() == questionId)
				itr.remove();
		}
		
	}
	
	public void statQuestion(int age, Boolean sex, int expLevel, int userId, int pOTDid) {
		
		stat = new StatisticalResponse();
		stat.setAge(age);
		stat.setExpertLevel(expLevel);
		stat.setProductOfTheDayId(pOTDid);
		stat.setUserId(userId);
		stat.setSex(sex);
		
	}
	
	private String[] divideText(String text) {
		
		String []s = text.replaceAll("^[,\\s]+", "").split("[,\\s]+");
		return s;
		
	}
	
	private List<String> divideOffensives(List<OffensiveWords> of) {
		
		List<String> offensives = new ArrayList<String>();
		
		for (OffensiveWords o : of) {
			offensives.add(o.getWords());
		}
		return offensives;
	}
		
	private Boolean checkOffense(Response response, List<OffensiveWords> of) {
		
		String[] letters = divideText(response.getText());
		List<String> offensives = divideOffensives(of);
				
		for (String s : letters) {
			if (offensives.contains(s.toLowerCase())) {
				return true;
			}
		}
		
		return false;
		
	}
	
	private void canceled(int userId, int cancel, Date pOTD) {
		
		List<Canceled> c = null;
		
		try {
			c = em.createNamedQuery("canceled.byUserId", Canceled.class)
					   .setParameter(1, userId)
					   .setParameter(2, pOTD)
					   .getResultList();
		} catch (PersistenceException e) {
			// TODO: create exceptions!
			System.out.println("troubles with canceled");
		}
				
		if (c.isEmpty()) {
			Canceled canc = new Canceled();
			canc.setUserId(userId);
			canc.setDate(pOTD);
			canc.setCanceled(cancel);
			em.persist(canc);
		}
		else if (c.size() == 1) {		
			System.out.println("No such user in canceled!");		
		}

	}
	
	public void submit(int userId, Date pOTD) {
		
		Boolean t = false;
				
		List<OffensiveWords> of = em.createNamedQuery("OffensiveWords.findAll", OffensiveWords.class)
							.getResultList();
		
		for (Response r : responses) {
			
			Boolean offensive = checkOffense(r, of);
			
			if (offensive == true) {

				User u = em.find(User.class, userId);
				u.setBlocked(true);
				t = true;
				break;
			} 
		}

		if (t == false) {
			for (Response r : responses) {
				Timestamp tm = new Timestamp(System.currentTimeMillis());
				r.setResponseDT(tm);
				em.persist(r);
			}
			Timestamp tm = new Timestamp(System.currentTimeMillis());

			stat.setReponseDate(tm);
			
			em.persist(stat);
			canceled(userId, 2, pOTD);
		}
		
	}
	
	public Boolean cancel(int userId, Date pOTD) {
		responses.clear();
		stat = null;
		
		canceled(userId, 1, pOTD);

		
		if (responses.isEmpty() && (stat == null)) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getResp(int i) {
		return this.responses.get(i).getText();
	}
	
	public List<Response> getResponses() {
		return this.responses;
	}
	
	public StatisticalResponse getStat() {
		return this.stat;
	}
	
	@Remove
	public void remove() {
		System.out.println("Congratulations!");
	}
}
