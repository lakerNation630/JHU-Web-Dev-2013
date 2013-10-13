package jhu.web.java.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jhu.web.java.exceptions.ItemNotAvailableException;
import jhu.web.java.models.Inventory;
import jhu.web.java.models.Item;
import jhu.web.java.models.Purchase;
import jhu.web.java.models.User;

@Transactional
@Component
public class PurchaseDAO {
	@PersistenceContext(name="h2")
	private EntityManager em;
	
	public List<Purchase> getAllPurchasesForUser(User user){
		List<Purchase> purchases = new ArrayList<>();
		
		TypedQuery<Purchase> query = em.createNamedQuery("Purchase.findAllForUser", Purchase.class);
		query.setParameter("user", user);
		
		for(Purchase p : query.getResultList()){
			purchases.add(p);
		}
		
		return purchases;
	}
	
	/**
	 * Attempts to purchase one or more items for a {@link User}. This method will check to make
	 * sure that there is an item currently in stock that can be purchased. If not then
	 * this method will throw and exception back to the caller and the transaction will be
	 * rolled back.
	 * 
	 * @param user The user making the purchase
	 * @param items The items that the user wishes to purchase
	 * @return A {@link Purchase} objects capturing the details of the purchase
	 */
	public Purchase purchaseItems(User user, Item... items) throws ItemNotAvailableException{
		/*
		 * Update the amount of available for each item to pre-mark them as being
		 * 'Purchased'. If not enough are available throw the exception which will
		 * roll-back any changes to the database
		 */
		
		for(Item i : items){
			//Get the items inventory
			TypedQuery<Inventory> query = em.createNamedQuery("Inventory.findForItem", Inventory.class);
			query.setParameter("item", i);
			Inventory inventory = query.getSingleResult();
			
			if(inventory.getAvailable() > 0){
				//decrease the amount in the inventory by one
				inventory.setAvailable((inventory.getAvailable()-1));
				em.merge(inventory);
			}else{
				throw new ItemNotAvailableException(i);
			}
		}
		
		//Now that all inventory counts have been updated create the 'Purchase' object
		Purchase purchase = new Purchase();
		purchase.setItems(Arrays.asList(items));
		purchase.setUser(user);
		purchase.setPurchaseDate(Calendar.getInstance().getTime());
		em.persist(purchase);
		return purchase;
	}
}
