package services;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.*;

import entities.User;
import exceptions.CredentialsException;
import exceptions.EmailCredentialsException;

@Stateless
public class UserService {

	// annotation PersistenceContext() declare a dependency on a persistence context
	// so we don't need EntityManagerFactory
	// PersistenceContext is a set of managed entity instances within an entity manager at
	// any given time
	@PersistenceContext(unitName = "GamifiedMarketingAppEJB")
	private EntityManager em;
	
	public UserService() { }
	
	public User checkCredentials(String username, String password) 
			throws CredentialsException, NonUniqueResultException{
		
		List<User> uList = null;
		try {
			uList = em.createNamedQuery("User.checkCredentials", User.class)
						// TODO: encrypt the password
					   .setParameter(1, username).setParameter(2, password)
					   .getResultList();
		} catch (PersistenceException e) {
			throw new CredentialsException("Could not verify credentals");
		}
		if (uList.isEmpty())
			return null;
		else if (uList.size() == 1)
			return uList.get(0);
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
		else if (uList.size() == 1)
			return uList.get(0);
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
	
}
