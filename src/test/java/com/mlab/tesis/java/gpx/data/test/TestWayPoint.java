package com.mlab.tesis.java.gpx.data.test;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mlab.tesis.java.gpx.data.AndroidWayPoint;
import com.mlab.tesis.java.gpx.data.GpxFactory;
import com.mlab.tesis.java.gpx.data.SimpleWayPoint;
import com.mlab.tesis.java.gpx.data.WayPoint;
import com.mlab.tesis.java.gpx.data.extensions.ExtendedWayPoint;

public class TestWayPoint extends TestCase {

	Logger logger=LoggerFactory.getLogger(getClass());
	
	
	public void testPruebas() {
		
	}
	
	public void testConstructor() {
		//logger.warn("\nTESTING WayPoint constructor");
		System.out.print("Testing WayPoint()...");
		long t=System.currentTimeMillis();
		
		// Basic constructor
		WayPoint wp = new SimpleWayPoint();
		assertNotNull(wp);
		assertEquals("", wp.getName());
		assertEquals("", wp.getDescription());
		assertEquals(-1l, wp.getTime());
		assertEquals(0.0, wp.getLongitude());
		assertEquals(0.0,wp.getLatitude());
		assertEquals(0.0,wp.getAltitude());

		// Constructor from parameters
		WayPoint tp= new SimpleWayPoint("Pto1","Punto de pruebas",t,-3.8,42.5,900.0);
		assertNotNull(tp);
		assertEquals("Pto1", tp.getName());
		assertEquals("Punto de pruebas", tp.getDescription());
		assertEquals(t, tp.getTime());
		assertEquals(-3.8, tp.getLongitude());
		assertEquals(42.5,tp.getLatitude());
		assertEquals(900.0,tp.getAltitude());

		// Constructor from List<Double> 				
		List<Double> values = new ArrayList<Double>();
		values.add(-3.8);
		values.add(42.5);
		values.add(900.0);
		SimpleWayPoint tp2= new SimpleWayPoint("Pto1","Punto de pruebas",t,values);
		assertNotNull(tp2);
		assertEquals("Pto1", tp2.getName());
		assertEquals("Punto de pruebas", tp2.getDescription());
		assertEquals(t, tp2.getTime());		
		assertEquals(-3.8,tp2.getLongitude());
		assertEquals(42.5,tp2.getLatitude());
		assertEquals(900.0,tp2.getAltitude());
		
		System.out.println("OK");
	}
	
	public void testAsCsv() {
		System.out.print("Testing WayPoint.asCsv()...");
		WayPoint tp= new SimpleWayPoint("Pto1","Punto de pruebas",1000l,-3.8,42.5,900.0);
		//System.out.println(tp.asCsv(false));
		assertEquals("1970-01-01T00:00:01.01Z,1000,-3.800000,42.500000,900.00",
				tp.asCsv(false));
		
		System.out.println("OK");
	}
	
	public void testParseGpx() {
		//logger.warn("TESTING WayPoint.parseGpx()");
		System.out.print("Testing WayPoint.parseGpx()...");
		GpxFactory factory = GpxFactory.getFactory(GpxFactory.Type.BSimpleGpxFactory);		
		// Crear una instancia de TrackPoint para generar una cadena gpx con la que probar el parse
		long t=System.currentTimeMillis();
		WayPoint tp= new SimpleWayPoint("Pto1","Punto de pruebas",t,-3.8,42.5,900.0);
//		String cadgpx=tp.asGpx();
//		System.out.println(GpxFactory.format(cadgpx));
		
		// Generar un TrackPoint parseando una cadena gpx y comprobar que no es nulo

		WayPoint parsedPoint = null;
		try {
			Method method=GpxFactory.class.getDeclaredMethod("parseWayPoint", String.class);
			method.setAccessible(true);
			parsedPoint = (SimpleWayPoint) method.invoke(factory, tp.asGpx());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNotNull(parsedPoint);
		//logger.info("\n"+parsedPoint.asGpx());		
		assertEquals(-3.8, parsedPoint.getLongitude());
		assertEquals(42.5, parsedPoint.getLatitude());
		assertEquals(900.0, parsedPoint.getAltitude());
		
		
		// Comprobar que el TrackPoint del constructor tp y el parsedPoint 
		// tienen la misma cadena gpx 
		//System.out.println(GpxFactory.format(tp.asGpx()));
		//System.out.println("-----------------");
		//System.out.println(parsedPoint.asGpx());
		assertEquals(tp.asGpx(), parsedPoint.asGpx());
		System.out.println("OK");
		
	}
	
	public void testClone() {
		System.out.print("Testing WayPoint.clone()...");
		long t=System.currentTimeMillis();
		WayPoint tp= new SimpleWayPoint("Pto1","Punto de pruebas",t,-3.8,42.5,900.0);
		WayPoint tp2 = tp.clone();
		assertNotNull(tp2);
		assertEquals("Pto1", tp2.getName());
		assertEquals("Punto de pruebas", tp2.getDescription());
		assertEquals(t, tp2.getTime());		
		assertEquals(-3.8,tp2.getLongitude());
		assertEquals(42.5,tp2.getLatitude());
		assertEquals(900.0,tp2.getAltitude());
		
		System.out.println("OK");
		
	}
}
