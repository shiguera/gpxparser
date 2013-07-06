package com.mlab.tesis.java.gpx.data.test;

import java.io.File;
import java.lang.reflect.Method;

import junit.framework.TestCase;

import com.mlab.tesis.java.gpx.data.GpxDocument;
import com.mlab.tesis.java.gpx.data.GpxFactory;
import com.mlab.tesis.java.gpx.data.GpxFactory.Type;
import com.mlab.tesis.java.gpx.data.SimpleWayPoint;
import com.mlab.tesis.java.gpx.data.Track;
import com.mlab.tesis.java.gpx.data.TrackSegment;
import com.mlab.tesis.java.gpx.data.Util;
import com.mlab.tesis.java.gpx.data.extensions.ExtendedGpxFactory;

public class TestTrackSegment extends TestCase {
	
	//private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	public void test() {
		//this.logger.info("TESTING TrackSegment.test()");
		System.out.print("Testing TrackSegment()...");
		long t=System.currentTimeMillis();
		SimpleWayPoint tp2= new SimpleWayPoint("Pto2","Punto de pruebas",t+1000l,-3.9,43.5,920.0,23.7,123.2,-1.0);
		SimpleWayPoint tp3= new SimpleWayPoint("Pto3","Punto de pruebas",t+2000l,-4.0,44.5,940.0,23.7,123.2,-1.0);
		
		TrackSegment ts=new TrackSegment();
		assertNotNull(ts);
		
		SimpleWayPoint tp= new SimpleWayPoint("Pto1",
			"Punto de pruebas",t,-3.8,42.5,900.0,23.7,123.2,-1.0);
		assertTrue(ts.addWayPoint(tp));
		assertTrue(ts.addWayPoint(tp2));
		assertTrue(ts.addWayPoint(tp3));
		assertEquals(3,ts.size());
		
		// Se saca la cadena en el TrackTest
		//this.logger.info(GpxDocument.format(ts.asGpx()));
		
		System.out.println("OK");
	}
	
	public void testParseGpx() {
		//this.logger.info("TESTING TrackSegment.testParseGpx()");
		System.out.print("Testing TrackSegment.parseGpx()...");
		TrackSegment ts=new TrackSegment();
		assertNotNull(ts);
		
		long t=System.currentTimeMillis();
		SimpleWayPoint tp= new SimpleWayPoint("Pto1","Punto de pruebas",t,-3.8,42.5,900.0,23.7,123.2,-1.0);
		ts.addWayPoint(tp);
		SimpleWayPoint tp2= new SimpleWayPoint("Pto2","Punto de pruebas",t+1000,-3.9,43.5,920.0,23.7,123.2,-1.0);
		ts.addWayPoint(tp2);
		SimpleWayPoint tp3= new SimpleWayPoint("Pto3","Punto de pruebas",t+2000,-4.0,44.5,940.0,23.7,123.2,-1.0);
		ts.addWayPoint(tp3);
		assertEquals(3,ts.size());
		//System.out.println(GpxFactory.format(cadgpx));
		
		GpxFactory factory = GpxFactory.getFactory(Type.SimpleGpxFactory);
		TrackSegment parsed=null;
		try {
			Method method=GpxFactory.class.getDeclaredMethod(
				"parseTrackSegment", String.class);
			method.setAccessible(true);
			parsed = (TrackSegment) method.invoke(factory, ts.asGpx());
		} catch (Exception e) {
			System.out.println("ERROR");
			e.printStackTrace();
		} 				
		//System.out.println(parsed);
		assertNotNull(parsed);
		assertEquals(3,parsed.size());
		
		//logger.info(GpxDocument.format(parsed.asGpx()));
		System.out.println("OK");
		
	}
	
	public void testLength() {
		System.out.print("Testing TrackSegment.length()...");
		File gpxFile = Util.readResourceFile("test.gpx");
		GpxDocument doc = ExtendedGpxFactory.readGpxDocument(gpxFile);
		assertNotNull(doc);
		Track track = (Track)doc.getTracks().get(0);
		TrackSegment segment = (TrackSegment)track.nodes().get(0);
		assertEquals(9095, (int)segment.length());
		System.out.println("OK");
	}
	// TODO Falta test para método length()

}
