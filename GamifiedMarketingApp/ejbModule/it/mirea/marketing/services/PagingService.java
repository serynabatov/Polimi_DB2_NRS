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

import it.mirea.marketing.entities.Response;
import it.mirea.marketing.entities.StatisticalResponse;

@Stateful
public class PagingService {
	
	@PersistenceContext(unitName = "GamifiedMarketingApp", type = PersistenceContextType.EXTENDED)
	private EntityManager em;
	//private TypedQuery<Response> responseJPAquery = null;
	//private TypedQuery<StatisticalResponse> statJPAquery = null;
	private List<Response> responses = new ArrayList<Response>();
	private StatisticalResponse stat = null;
	
	public PagingService() { }
	
	public void answerQuestion(int questionId, String text, int userId, int pOTDid) {
		Response resp = new Response();
		
		resp.setPOTDId(pOTDid);
		resp.setQuestionId(questionId);
		resp.setUserId(userId);
		resp.setText(text);
		
		responses.add(resp);
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
		
		stat.setAge(age);
		stat.setExpertLevel(expLevel);
		stat.setProductOfTheDayId(pOTDid);
		stat.setUserId(userId);
		stat.setSex(sex);
		
	}
	
	public void submit() {
		
		for (Response r : responses) {
			Timestamp tm = new Timestamp(System.currentTimeMillis());
			r.setResponseDT(tm);
			em.persist(r);
		}
		
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
