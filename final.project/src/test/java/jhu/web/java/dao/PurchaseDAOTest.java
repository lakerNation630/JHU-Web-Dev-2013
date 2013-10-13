package jhu.web.java.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import jhu.web.java.exceptions.ItemNotAvailableException;
import jhu.web.java.models.Inventory;
import jhu.web.java.models.Item;
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
public class PurchaseDAOTest {

	@PersistenceContext(unitName="h2")
	private EntityManager em;
	@Autowired
	private PurchaseDAO pDao;
	
	@Test
	@Transactional
	public void testAllPurchasesAreReturned() throws ItemNotAvailableException{
		
		User testuser  = new User();
		testuser.setFirstName("Test");
		em.persist(testuser);
		
		TypedQuery<Item> query = em.createNamedQuery("Item.findAll", Item.class);
		List<Item> items = query.getResultList();
		
		Item item = items.get(0);
		
		TypedQuery<Inventory> inventoryQuery = em.createNamedQuery("Inventory.findForItem", Inventory.class);
		inventoryQuery.setParameter("item", item);
		int amountAvailable = inventoryQuery.getSingleResult().getAvailable();
		
		int amountToBuy  = amountAvailable;
		for(int i=0; i< amountToBuy; i++){
			pDao.purchaseItems(testuser, item);
		}
		
		Assert.assertEquals(amountToBuy, pDao.getAllPurchasesForUser(testuser).size());
		
		//check that no more items are available to buy
		inventoryQuery = em.createNamedQuery("Inventory.findForItem", Inventory.class);
		inventoryQuery.setParameter("item", item);
		Assert.assertEquals(0,inventoryQuery.getSingleResult().getAvailable());
	}
}
