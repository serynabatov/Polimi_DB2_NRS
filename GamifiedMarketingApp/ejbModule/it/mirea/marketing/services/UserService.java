package it.mirea.marketing.services;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.*;

import it.mirea.marketing.entities.Canceled;
import it.mirea.marketing.entities.LogInTime;
import it.mirea.marketing.entities.ProductOfTheDay;
import it.mirea.marketing.entities.User;
import it.mirea.marketing.exceptions.CredentialsException;
import it.mirea.marketing.exceptions.EmailCredentialsException;

@Stateless
public class UserService {

	// annotation PersistenceContext() declare a dependency on a persistence context
	// so we don't need EntityManagerFactory
	// PersistenceContext is a set of managed entity instances within an entity manager at
	// any given time
	@PersistenceContext(unitName = "GamifiedMarketingApp")
	private EntityManager em;

	public UserService() { }

	public User checkCredentials(String username, String password)
			throws CredentialsException, NonUniqueResultException{

		List<User> uList = null;
		try {
			uList = em.createNamedQuery("user.checkCredentials", User.class)
					// TODO: encrypt the password
					  .setParameter(1, username).setParameter(2, password)
					  .getResultList();
		} catch (PersistenceException e) {
				throw new CredentialsException("Could not verify credentals");
		}
		if (uList.isEmpty())
			return null;
		else if (uList.size() == 1) {
			User u = uList.get(0);
			LogInTime logTime = new LogInTime();
			
			logTime.setLogg(new Timestamp(System.currentTimeMillis()));
			logTime.setUserId(u.getUserId());
			
			em.persist(logTime);
			
			return uList.get(0);
		}
		throw new NonUniqueResultException("More than one user registered with same credentials");
	}

	private User checkCredentials(String email)
			throws EmailCredentialsException, CredentialsException {

		List<User> uList = null;
		try {
			uList = em.createNamedQuery("User.checkCredentialsMail", User.class)
					.setParameter(1, email)
					.getResultList();
		} catch (PersistenceException e) {
			throw new CredentialsException("Could not verify credentials");
		}
		if (uList.isEmpty())
			return null;
		else if (uList.size() == 1) {		
			return uList.get(0);
		}
		throw new EmailCredentialsException("A user with the same email has been already registered");

	}

	public Boolean registerTheUser(String userName, String password, String email)
			throws NonUniqueResultException, CredentialsException, EmailCredentialsException {
		
		if((checkCredentials(userName, password) == null) && (checkCredentials(email) == null)) {
			User u = new User();
			u.setPassword(password);
			u.setUserName(userName);
			u.setMail(email);
			em.persist(u);
			return true;
		} else {
			return false;
		}
	}
	
	private User findById(int id) {
		return em.find(User.class, id);
	}
	
	// 0 - user didn't do anything
	// 1 - user canceled
	// 2 - user submitted
	public List<String> getCanceled(int t, Date d) {
		List<String> uList = new ArrayList<String>();
		List<Canceled> cancList = null;
		
		try {
			cancList = em.createNamedQuery("canceled.getCancel", Canceled.class)
						 .setParameter(1, t)
						 .setParameter(2, d)
						 .getResultList();
			
		} catch (PersistenceException e) {
			return null;
		}
		
		for (Canceled c : cancList) {
			User u = findById(c.getUserId());
			
			uList.add(u.getUserName());
		}
		
		if (uList.isEmpty())
			return null;
		else
			return uList;
	}
	
	public Boolean submitted(int id, int t, Date d) {
		
		List<Canceled> cancList = null;
		
		try {
			cancList = em.createNamedQuery("canceled.getCancel", Canceled.class)
					     .setParameter(1, t)
					     .setParameter(2, d)
					     .getResultList();
		} catch (PersistenceException e) {
			return null;
		}
		
		Boolean submit = false;
		
		for (Canceled c : cancList) {
			if (c.getUserId() == id) {
				submit = true;
				break;
			}
		}
		
		return submit;
	}

	public List<User> getLeaderBoard() {

		List<User> uList = null;
		try {
			em.clear();
			uList = em.createNamedQuery("user.leaderboard", User.class)
					  .setParameter(1, "admin")
					  .setParameter(2, true)
					  .setHint("javax.persistence.cache.storeMode", "REFRESH")
					  .getResultList();
		} catch (PersistenceException e) {
			return null;
		}
		if (uList.isEmpty())
			return null;
		else
			return uList;
	}
	
	public List<User> getLeaderBoardProduct() {
		List<User> uList = null;
		try {
			em.clear();
			uList = em.createNamedQuery("user.leaderboardProduct", User.class)
					  .setParameter(1, "admin")
					  .setParameter(2, true)
					  .setHint("javax.persistence.cache.storeMode", "REFRESH")
					  .getResultList();
		} catch (PersistenceException e) {
			return null;
		}
		if (uList.isEmpty())
			return null;
		else 
			return uList;
	}

	public Boolean deleteUser(int id) {

		User u = em.find(User.class, id);

		em.getTransaction().begin();
		em.remove(u);
		em.getTransaction().commit();

		User u1 = em.find(User.class, id);

		if (u1 == null)
			return true;
		else
			return false;
	}
	
	public String checkYourPrivilege(int id) {
		
		User u = em.find(User.class, id);
		
		return u.getRole();
	}

	public Boolean getBlocked(int id) {
		
		User u = em.find(User.class, id);
		
		return u.getBlocked();
		
	}
	
}
