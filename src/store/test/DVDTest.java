package store.test;

import store.business.*;
import junit.framework.*;
import java.util.*;

/**
* @authors Thierry KHAMPHOUSONE & Tata Joseph ASSOUMA
*/ 

public class DVDTest extends TestCase {
	
	private UUID identifier1 = UUID.randomUUID();
	private UUID identifier2 = UUID.randomUUID();
	private UUID identifier3 = UUID.randomUUID();
	DVD dvd1 = new DVD("dvdTest1", 2.0, 1, "imagedvdTest1", identifier1, 10, Genre.ACTION);
	DVD dvd2 = new DVD("dvdTest2", 4.0, 2, "imagedvdTest2", identifier2, 20, Genre.HORROR);
	DVD dvd3 = new DVD("dvdTest3", 8.0, 4, "imagedvdTest3", identifier3, 40, Genre.DRAMA);


	public void testDVD() {
		assertNotSame(dvd1, dvd2);
	}

	public void testGetLength() {
		assertEquals("Length incorrect", 20, dvd2.getLength());
	}


	public void testGetVectorActors() {
		//Get 2 object and compare them if they're the same object or not
		dvd2.addActors("actors2");
		Vector <String> expectedActor2 = dvd2.getVectorActors();
		Vector <String> expectedActor1 = dvd1.getVectorActors();
		//if not the same = true 
		assertNotSame("Same object", expectedActor1, expectedActor2);


	}
	
	public void testAddActors() {
		//adding 1 object into the Vector and compare the size of the Vector
		dvd1.addActors("actors1");
		//if the size is the same = true
		assertEquals("Size incorrect", 1, dvd1.getVectorActors().size());
		dvd1.addActors("actors11");
		assertEquals("Size incorrect", 2, dvd1.getVectorActors().size());
	}

	
	public void testGetActors() {
		//adding 2 objects into the Vector and compare if they're the same or not
		dvd3.addActors("actors1");
		dvd3.addActors("actors2");	
		//if not the same = true
		assertNotSame("Same object", dvd3.getActors(0), dvd3.getActors(1));
	}

	public void testGetGenre() {
		assertEquals("Genre incorrect", Genre.HORROR, dvd2.getGenre());
	}

}
