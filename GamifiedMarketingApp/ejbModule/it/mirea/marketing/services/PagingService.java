package it.mirea.marketing.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

import it.mirea.marketing.entities.OffensiveWords;
import it.mirea.marketing.entities.Response;
import it.mirea.marketing.entities.StatisticalResponse;

@Stateful
public class PagingService {
	
	@PersistenceContext(unitName = "GamifiedMarketingApp", type = PersistenceContextType.EXTENDED)
	private EntityManager em;
	private List<Response> responses = new ArrayList<Response>();
	private StatisticalResponse stat = null;
	private OffensiveWords offensive = null;
	
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
		
		// we could insert more complex regex (if there is a time for it)
		String []s = text.split(" ");
		
		return s;
		
	}
	
	// maybe do it via binary search?
	private Boolean checkOffense(Response response, List<OffensiveWords> of) {
		
		String[] letters = divideText(response.getText());
		
		for (String s : letters) {
			if (of.contains(s)) {
				return false;
			}
		}
		
		return true;
		
	}
	
	public void submit() {
		
		offensive = new OffensiveWords();
		
		List<OffensiveWords> of = em.createNamedQuery("OffensiveWords.findAll", OffensiveWords.class)
							.getResultList();

		for (Response r : responses) {
			
			Boolean offensive = checkOffense(r, of);
			
			if (offensive == true) {
				// TODO: block the user				
			} else {
				Timestamp tm = new Timestamp(System.currentTimeMillis());
				r.setResponseDT(tm);
				em.persist(r);
			}
		}
		
		Timestamp tm = new Timestamp(System.currentTimeMillis());

		stat.setReponseDate(tm);
		
		em.persist(stat);
		
	}
	
	public Boolean cancel() {
		responses.clear();
		stat = null;
		
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
