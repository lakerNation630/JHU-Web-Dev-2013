package jhu.web.java.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * This class denotes an item that can be sold
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Item.findAll",query="Select i from Item i")
})
public class Item {
	/** A description of the item*/
	private String description;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/** The name of the item*/
	private String name;
	/** The retail cost of the item*/
	private float price;
	
	public String getDescription() {
		return description;
	}
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public float getPrice() {
		return price;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPrice(float price) {
		this.price = price;
	}
}
