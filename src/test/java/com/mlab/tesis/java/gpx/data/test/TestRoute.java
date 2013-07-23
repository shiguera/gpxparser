package com.mlab.tesis.java.gpx.data.test;

import java.lang.reflect.Method;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mlab.tesis.java.gpx.data.BGpxFactory;
import com.mlab.tesis.java.gpx.data.BGpxFactory.Type;
import com.mlab.tesis.java.gpx.data.BRoute;
import com.mlab.tesis.java.gpx.data.BSimpleWayPoint;
import com.mlab.tesis.java.gpx.data.BWayPoint;

public class TestRoute extends TestCase {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	public void test() {
		this.logger.info("TESTING Route()");
		System.out.print("Testing Route() ...");
		
		BRoute rte=new BRoute();
		assertNotNull(rte);
		
		long t=System.currentTimeMillis();
		BWayPoint tp= new BSimpleWayPoint("Pto1","Punto de pruebas",t,-3.8,42.5,900.0);
		BWayPoint tp2= new BSimpleWayPoint("Pto2","Punto de pruebas",t+1000,-3.9,43.5,920.0);
		BWayPoint tp3= new BSimpleWayPoint("Pto3","Punto de pruebas",t+2000,-4.0,44.5,940.0);
				
		assertTrue(rte.addWayPoint(tp));
		assertTrue(rte.addWayPoint(tp2));
		assertTrue(rte.addWayPoint(tp3));
		assertEquals(3,rte.size());
				
		BGpxFactory factory = BGpxFactory.getFactory(Type.BSimpleGpxFactory);
		BRoute parsed = null;
		try {
			Method method=BGpxFactory.class.getDeclaredMethod("parseRoute", String.class);
			method.setAccessible(true);
			parsed = (BRoute) method.invoke(factory, rte.asGpx());
		} catch (Exception e) {
			System.out.println("ERROR");
			e.printStackTrace();
		} 
		assertNotNull(parsed);
		assertEquals(3,parsed.size());
		
		// Se saca la cadena en el TrackTest
		//this.logger.info("\n"+GpxFactory.format(parsed.asGpx()));

		System.out.println("OK");

	
	}
}
