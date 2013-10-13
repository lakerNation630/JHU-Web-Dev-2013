package jhu.web.java.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import jhu.web.java.exceptions.InvalidPassowrdException;
import jhu.web.java.exceptions.ItemNotAvailableException;
import jhu.web.java.exceptions.UserNotFoundException;
import jhu.web.java.models.User;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="/spring/application-context.xml")
@Transactional
public class UserDAOTest {

	@PersistenceContext(unitName="h2")
	private EntityManager em;
	@Autowired
	private UserDAO uDao;
	
	@Test
	public void testUserIsMade() throws ItemNotAvailableException{
		
		String testUser = "fakeUser1";
		
		Assert.assertFalse(uDao.doesUserExist(testUser));
		
		User user = new User();
		user.setUsername(testUser);
		
		uDao.createUser(user);
		Assert.assertTrue(uDao.doesUserExist(testUser));
	}
	
	@Test(expected=UserNotFoundException.class)
	public void testNoUserFound() throws UserNotFoundException, InvalidPassowrdException{
		uDao.getUser("non-existant-user", "pasword");
	}
	
	@Test(expected=InvalidPassowrdException.class)
	public void testInvalidPassword() throws UserNotFoundException, InvalidPassowrdException{
		String testUser = "User1";
		User user = new User();
		user.setUsername(testUser);
		user.setPassword("realpassword");
		
		uDao.createUser(user);
		uDao.getUser(testUser, "fakepassword");
	}
}
