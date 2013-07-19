package com.mlab.tesis.java.gpx.data.test;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		SimpleWayPoint tp= new SimpleWayPoint("Pto1","Punto de pruebas",t,-3.8,42.5,900.0,23.7,123.2,-1.0);
		
		assertNotNull(tp);
		//logger.info("\n"+tp.asGpx());		
		
		t=System.currentTimeMillis();
		// testing short values[]
		List<Double> values = new ArrayList<Double>();
		values.add(-3.8);
		values.add(42.5);
		values.add(900.0);
		//SimpleWayPoint tp2= new SimpleWayPoint("Pto2","Short values",t,values);
		//assertNotNull(tp2);
		//assertEquals(-3.5,tp2.getLongitude());
		//assertEquals(42.5,tp2.getLatitude());
		//assertEquals(900.0,tp2.getAltitude());
		
		//logger.info("\n"+tp2.asGpx());	
		
		
		
		System.out.println("OK");
	}
	
	public void testAsCsv() {
		System.out.print("Testing WayPoint.asCsv()...");
		WayPoint tp= new SimpleWayPoint("Pto1","Punto de pruebas",1000l,-3.8,42.5,900.0,23.7,123.2,-1.0);
		//System.out.println(tp.asCsv(false));
		assertEquals("1970-01-01T00:00:01.01Z,1000,-3.800000,42.500000,900.00,23.70,123.20,-1.00", 
				tp.asCsv(false));

		
		WayPoint tp2= new ExtendedWayPoint("Pto1","Punto de pruebas",1000l,-3.8,42.5,900.0,23.7,123.2,-1.0,10.01,9.81,3.4,980.0);
		//System.out.println(tp2.asCsv(false));
		assertEquals("1970-01-01T00:00:01.01Z,1000,-3.800000,42.500000,900.00,23.70,123.20,-1.00,10.010000,9.810000,3.400000,980.00",
				tp2.asCsv(false));
		
		//System.out.println(tp2.asCsv(true));
		assertEquals("1970-01-01T00:00:01.01Z,1000,-3.800000,42.500000,900.00,434266.90,4705603.11,23.70,123.20,-1.00,10.010000,9.810000,3.400000,980.00",
				tp2.asCsv(true));
		System.out.println("OK");
	}
	
	public void testParseGpx() {
		//logger.warn("TESTING WayPoint.parseGpx()");
		System.out.print("Testing WayPoint.parseGpx()...");
		GpxFactory factory = GpxFactory.getFactory(GpxFactory.Type.SimpleGpxFactory);		
		// Crear una instancia de TrackPoint para generar una cadena gpx con la que probar el parse
		long t=System.currentTimeMillis();
		SimpleWayPoint tp= new SimpleWayPoint("Pto1","Punto de pruebas",t,-3.8,42.5,900.0,23.7,123.2,10.0);
//		String cadgpx=tp.asGpx();
//		System.out.println(GpxFactory.format(cadgpx));
		
		// Generar un TrackPoint parseando una cadena gpx y comprobar que no es nulo

		SimpleWayPoint parsedPoint = null;
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
		assertEquals(23.7, parsedPoint.getSpeed());
		assertEquals(123.2, parsedPoint.getBearing());
		assertEquals(10.0, parsedPoint.getAccuracy());
		
		
		// Comprobar que el TrackPoint del constructor tp y el parsedPoint 
		// tienen la misma cadena gpx 
		//System.out.println(GpxFactory.format(tp.asGpx()));
		//System.out.println("-----------------");
		//System.out.println(parsedPoint.asGpx());
		assertEquals(tp.asGpx(), parsedPoint.asGpx());
		System.out.println("OK");
		
	}
}
