package store.business;

import java.util.*; 

/**
* @authors Thierry KHAMPHOUSONE & Tata Joseph ASSOUMA
*/

public class PriceComparator implements Comparator<Product> {
	
	public int compare(Product p1, Product p2) {
		if(p1.getPrice() > p2.getPrice())
			return 1;
		else if(p1.getPrice() == p2.getPrice()) 
			return 0;
		else
			return -1;
	}

}
