package com.mlab.gpx.test.impl;

import java.lang.reflect.Method;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mlab.gpx.api.GpxFactory;
import com.mlab.gpx.api.WayPoint;
import com.mlab.gpx.api.GpxFactory.Type;
import com.mlab.gpx.impl.Route;
import com.mlab.gpx.impl.SimpleWayPoint;

public class TestRoute extends TestCase {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	public void test() {
		this.logger.info("TESTING Route()");
		System.out.print("Testing Route() ...");
		
		Route rte=new Route();
		assertNotNull(rte);
		
		long t=System.currentTimeMillis();
		WayPoint tp= new SimpleWayPoint("Pto1","Punto de pruebas",t,-3.8,42.5,900.0);
		WayPoint tp2= new SimpleWayPoint("Pto2","Punto de pruebas",t+1000,-3.9,43.5,920.0);
		WayPoint tp3= new SimpleWayPoint("Pto3","Punto de pruebas",t+2000,-4.0,44.5,940.0);
				
		assertTrue(rte.addWayPoint(tp));
		assertTrue(rte.addWayPoint(tp2));
		assertTrue(rte.addWayPoint(tp3));
		assertEquals(3,rte.size());
				
		GpxFactory factory = GpxFactory.getFactory(Type.SimpleGpxFactory);
		Route parsed = null;
		try {
			Method method=GpxFactory.class.getDeclaredMethod("parseRoute", String.class);
			method.setAccessible(true);
			parsed = (Route) method.invoke(factory, rte.asGpx());
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
