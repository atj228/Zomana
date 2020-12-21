package store.business;

import java.util.*;
import java.sql.Timestamp;
import java.util.Date;

/**
* @authors Thierry KHAMPHOUSONE & Tata Joseph ASSOUMA
*/ 

public class Transaction {
	
	private UUID clientID;
	private UUID productID;
	private int numProducts;
	private Timestamp time; 

	public Transaction(UUID clientID, UUID productID, int numProducts, Timestamp time) {
		this.clientID = clientID;
		this.productID = productID;
		this.numProducts = numProducts;
		this.time = time;
	}

	public UUID getClientID() {
		return this.clientID; 
	}

	public UUID getProductID() {
		return this.productID; 
	}

	public int getNumProducts() {
		return this.numProducts; 
	}

	public Timestamp getTime() {
		return this.time;
	}
}
