package store.test;

import static org.junit.Assert.*;

import org.junit.Test;
import store.business.*;
import junit.framework.*;
import java.util.*;
import java.text.*;

import java.sql.Timestamp;
import javax.xml.parsers.*;

/**
* @authors Thierry KHAMPHOUSONE & Tata Joseph ASSOUMA
*/ 

public class TransactionTest extends TestCase {

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	UUID t1u1 = UUID.randomUUID();
	UUID t1u2 = UUID.randomUUID();
	UUID t2u1 = UUID.randomUUID();
	UUID t2u2 = UUID.randomUUID();

	Date parseDate1 = new Date(System.currentTimeMillis());
	Timestamp time1 = new Timestamp(parseDate1.getTime());
	Date parseDate2 = new Date(System.currentTimeMillis());
	Timestamp time2 = new Timestamp(parseDate2.getTime());

	Transaction t1 = new Transaction(t1u1, t1u2, 1, time1);
	Transaction t2 = new Transaction(t2u1, t2u2, 2, time2);

	public void testTransaction() {
		assertNotSame("error : Same transaction", t1,t2);
	}

	public void testGetClientID() {
		assertSame("incorrect ClientID", t1u1, t1.getClientID());
	}

	public void testGetProductID() {
		assertSame("incorrect ProductID", t1u2, t1.getProductID());
	}

	public void testGetNumProducts() {
		assertSame("incorrect number Product", 1, t1.getNumProducts());
	}

	public void testGetTime() {
		assertSame("incorrect time", time1, t1.getTime());
	}

}
