package jhu.web.java.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

/**
 * Maintains an inventory of each {@link Item} that is for sale
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Inventory.findAll",query="Select i from Inventory i"),
	@NamedQuery(name="Inventory.findAllAvailable",query="Select i from Inventory i Where i.available > 0"),
	@NamedQuery(name="Inventory.findForItem",query="Select i from Inventory i Where i.item = :item")
})
public class Inventory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@OneToOne
	/** The Item this inventory is for*/
	private Item item;
	/** The amount of {@link Item items} that are currently available*/
	private int available;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public int getAvailable() {
		return available;
	}
	public void setAvailable(int available) {
		this.available = available;
	}
}
