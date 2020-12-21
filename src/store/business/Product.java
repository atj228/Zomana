package store.business;

import java.util.*;

/**
* @authors Thierry KHAMPHOUSONE & Tata Joseph ASSOUMA
*/

public abstract class Product {
	
	private String name;
	private double price;
	private UUID identifier;
	private int stock;
	private String image; 

	public Product(String name, double price, int stock, String image, UUID identifier) {
		this.name = name;
		this.price = price;
		this.identifier = identifier;
		this.stock = stock;
		this.image = image;
	}

	public String toString() {
		return name + " " + price + " " + identifier.toString() + " " + stock + " " + image;
	}

	public String getProductName() {
		return this.name;
	}

	public UUID getProductUUID() {
		return this.identifier;
	}

	public double getPrice() {
		return this.price;
	}

	public int getProductStock() {
		return this.stock;
	}

	public String getProductImage() {
		return this.image;
	}

	public boolean allowedToBuyProductNb(int nb) {
		if(this.stock - nb < 0) {
			return false;
		}
		else {
			return true;
		}
	}

	public void removeToBuyProductNb(int nb) {
		this.stock = this.stock -nb;
	}

}
