package jhu.web.java.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Persistence;

/**
 * This object represents a purchase made buy a {@link User} to buy one or more {@link Item items}
 *
 */
@Entity
public class Purchase {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/** The list of items that were purchased*/
	@ElementCollection
	private List<Item> items;
	/** The date that the item was purchased*/
	private Date purchaseDate;
	/** The user who has made the purchase*/
	@OneToOne
	private User user;
	
	public Long getId() {
		return id;
	}
	public List<Item> getItems() {
		return items;
	}
	
	
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public User getUser() {
		return user;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
