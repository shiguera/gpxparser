package com.mlab.tesis.java.gpx.data.test;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mlab.tesis.java.gpx.data.GpxFactory;
import com.mlab.tesis.java.gpx.data.SimpleWayPoint;
import com.mlab.tesis.java.gpx.data.TrackSegment;

public class TrackSegmentTest extends TestCase {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	public void test() {
		this.logger.info("TESTING TrackSegment.test()");
		
		long t=System.currentTimeMillis();
		SimpleWayPoint tp= new SimpleWayPoint("Pto1","Punto de pruebas",t,-3.8,42.5,900.0,23.7,123.2,-1.0);
		SimpleWayPoint tp2= new SimpleWayPoint("Pto2","Punto de pruebas",t+1000,-3.9,43.5,920.0,23.7,123.2,-1.0);
		SimpleWayPoint tp3= new SimpleWayPoint("Pto3","Punto de pruebas",t+2000,-4.0,44.5,940.0,23.7,123.2,-1.0);
		
		TrackSegment ts=new TrackSegment();
		assertNotNull(ts);
		
		logger.info(String.format("%b", ts.addWayPoint(tp)));
		logger.info(String.format("%b", ts.addWayPoint(tp2)));
		logger.info(String.format("%b", ts.addWayPoint(tp3)));
		assertEquals(3,ts.size());
		
		// Se saca la cadena en el TrackTest
		//this.logger.info(GpxDocument.format(ts.asGpx()));
	}
	
	public void testParseGpx() {
		this.logger.info("TESTING TrackSegment.testParseGpx()");
		TrackSegment ts=new TrackSegment();
		assertNotNull(ts);
		long t=System.currentTimeMillis();
		SimpleWayPoint tp= new SimpleWayPoint("Pto1","Punto de pruebas",t,-3.8,42.5,900.0,23.7,123.2,-1.0);
		ts.addWayPoint(tp);
		SimpleWayPoint tp2= new SimpleWayPoint("Pto2","Punto de pruebas",t+1000,-3.9,43.5,920.0,23.7,123.2,-1.0);
		ts.addWayPoint(tp2);
		SimpleWayPoint tp3= new SimpleWayPoint("Pto3","Punto de pruebas",t+2000,-4.0,44.5,940.0,23.7,123.2,-1.0);
		ts.addWayPoint(tp3);
		assertEquals(3,ts.wayPointCount());
		String cadgpx=ts.asGpx();
		//System.out.println(GpxFactory.format(cadgpx));
		
		GpxFactory factory = GpxFactory.getFactory(GpxFactory.Type.SimpleGpxFactory);
		TrackSegment parsed = factory.parseSegment(cadgpx);
		assertNotNull(parsed);
		assertEquals(3,parsed.wayPointCount());
		
		//logger.info(GpxDocument.format(parsed.asGpx()));
		
	}
	

}
