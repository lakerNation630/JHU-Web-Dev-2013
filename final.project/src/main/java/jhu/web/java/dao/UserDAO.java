package jhu.web.java.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import jhu.web.java.exceptions.InvalidPassowrdException;
import jhu.web.java.exceptions.UserNotFoundException;
import jhu.web.java.models.User;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class UserDAO {
	private static Logger log = Logger.getLogger(UserDAO.class);
	@PersistenceContext(name="h2")
	private EntityManager em;
	
	public boolean doesUserExist(String username){
		TypedQuery<User> query  = em.createNamedQuery("User.findByUsername", User.class);
		query.setParameter("username", username);
		
		try{
			User u = query.getSingleResult();
			return true;
		}catch(NoResultException e){
			log.debug("No entry in database with a username of "+username);
		}
		
		return false;
	}
	
	/**
	 * Retrieves the registered {@link User}
	 * 
	 * @param username The registered username of the user
	 * @param password The password for the user
	 * @return The User object
	 * @throws UserNotFoundException If there is no user in the system with the registered 
	 * Username
	 * @throws InvalidPassowrdException If the supplied password doesn't match what is registered
	 * within the database
	 */
	public User getUser(String username, String password)
			throws UserNotFoundException,InvalidPassowrdException{
		
		if(!doesUserExist(username)){
			throw new UserNotFoundException(username);
		}
		
		TypedQuery<User> query  = em.createNamedQuery("User.findByUsername", User.class);
		query.setParameter("username", username);
		User u = query.getSingleResult();
		
		if(!u.getPassword().equals(password)){
			throw new InvalidPassowrdException(u); 
		}
		
		return u;
	}
	
	/**
	 * Registers a new user inside of the database
	 * 
	 * @param The user to register
	 * @return <code>true</code> if the new user was successfully registered
	 */
	public boolean createUser(User newUser){
		if(doesUserExist(newUser.getUsername())){
			log.error(String.format("A user with the name [%s] already exists",newUser.getUsername()));
			return false;
		}
		em.persist(newUser);
		return true;
	}
}
