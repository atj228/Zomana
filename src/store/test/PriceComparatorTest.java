package store.test;

import static org.junit.Assert.*;

import org.junit.Test;
import store.business.*;
import junit.framework.*;
import java.util.*;

/**
* @authors Thierry KHAMPHOUSONE & Tata Joseph ASSOUMA
*/ 

public class PriceComparatorTest extends TestCase {
	private UUID identifier1 = UUID.randomUUID();
	private UUID identifier2 = UUID.randomUUID();
	private UUID identifier3 = UUID.randomUUID();
	PriceComparator pc = new PriceComparator();
	DVD expensive = new DVD("dvdTest1", 65.0, 1, "imagedvdTest1", identifier1, 10, Genre.ACTION);
	DVD cheap = new DVD("dvdTest2", 1.0, 2, "imagedvdTest2", identifier2, 20, Genre.HORROR);
	DVD samePrice = new DVD("dvdTest2", 1.0, 5, "imagedvdTest3", identifier3, 40, Genre.ACTION);


	public void testCompare() {
		int expected1 = 1;
		int output1 = pc.compare(expensive, cheap);
		Assert.assertEquals("comparator (p1 > p2) incorrect", expected1, output1);

		int expected2 = 0;
		int output2 = pc.compare(cheap, samePrice);
		Assert.assertEquals("comparator (p1 = p2) incorrect", expected2, output2);

		int expected3 = -1;
		int output3 = pc.compare(cheap, expensive);
		Assert.assertEquals("comparator (p1 < p2) incorrect", expected3, output3);
	}

}
