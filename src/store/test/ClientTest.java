package store.test;

import static org.junit.Assert.*;

import org.junit.Test;
import store.business.*;
import junit.framework.*;
import java.util.*;

/**
* @authors Thierry KHAMPHOUSONE & Tata Joseph ASSOUMA
*/ 

public class ClientTest extends TestCase {

	private UUID identifier1 = UUID.randomUUID();
	private UUID identifier2 = UUID.randomUUID();
	Client client1 = new Client("clientName1","clientLastName1", "address1", identifier1);
	Client client2 = new Client("clientName2","clientLastName2", "address2", identifier2);

	public void testClient() {
		assertNotSame(client1, client2);
	}

	public void testGetFirstName() {
		assertEquals("FirstName incorrect", "clientName1", client1.getFirstName());
	}

	public void testGetFirstLastName() {
		assertEquals("FirstLastName incorrect", "clientName1 clientLastName1", client1.getFirstLastName());
	}

	public void testGetLastName() {
		assertEquals("LastName incorrect", "clientLastName1", client1.getLastName());
	}

	public void testGetAddress() {
		assertEquals("Address incorrect", "address1", client1.getAddress());
	}

	public void testGetUniqueID() {
		assertEquals("FirstName incorrect", identifier1, client1.getUniqueID());
	}

	public void testEqualsClient() {
		boolean flag = client1.equals(client1);
		assertEquals("equals incorrect", true, flag);
	}

}
