package jhu.web.java.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import jhu.web.java.models.Inventory;
import jhu.web.java.models.Item;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class InventoryDAO {
	@PersistenceContext(name="h2")
	private EntityManager em;
	
	public Inventory getInventoryForItem(Item item){
		TypedQuery<Inventory> inventory  = em.createNamedQuery("Inventory.findForItem", Inventory.class);
		inventory.setParameter("item", item);
		
		return inventory.getSingleResult();
	}
	
}
