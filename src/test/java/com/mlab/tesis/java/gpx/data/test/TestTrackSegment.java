package com.mlab.tesis.java.gpx.data.test;

import java.io.File;
import java.lang.reflect.Method;

import junit.framework.TestCase;

import com.mlab.tesis.java.gpx.Util;
import com.mlab.tesis.java.gpx.data.BGpxDocument;
import com.mlab.tesis.java.gpx.data.BGpxFactory;
import com.mlab.tesis.java.gpx.data.BGpxFactory.Type;
import com.mlab.tesis.java.gpx.data.BSimpleWayPoint;
import com.mlab.tesis.java.gpx.data.BTrack;
import com.mlab.tesis.java.gpx.data.BTrackSegment;
import com.mlab.tesis.java.gpx.data.extensions.ExtendedGpxFactory;

public class TestTrackSegment extends TestCase {
	
	//private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	public void test() {
		//this.logger.info("TESTING TrackSegment.test()");
		System.out.print("Testing TrackSegment()...");
		long t=System.currentTimeMillis();
		BSimpleWayPoint tp2= new BSimpleWayPoint("Pto2","Punto de pruebas",t+1000l,-3.9,43.5,920.0);
		BSimpleWayPoint tp3= new BSimpleWayPoint("Pto3","Punto de pruebas",t+2000l,-4.0,44.5,940.0);
		
		BTrackSegment ts=new BTrackSegment();
		assertNotNull(ts);
		
		BSimpleWayPoint tp= new BSimpleWayPoint("Pto1",
			"Punto de pruebas",t,-3.8,42.5,900.0);
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
		BTrackSegment ts=new BTrackSegment();
		assertNotNull(ts);
		
		long t=System.currentTimeMillis();
		BSimpleWayPoint tp= new BSimpleWayPoint("Pto1","Punto de pruebas",t,-3.8,42.5,900.0);
		ts.addWayPoint(tp);
		BSimpleWayPoint tp2= new BSimpleWayPoint("Pto2","Punto de pruebas",t+1000,-3.9,43.5,920.0);
		ts.addWayPoint(tp2);
		BSimpleWayPoint tp3= new BSimpleWayPoint("Pto3","Punto de pruebas",t+2000,-4.0,44.5,940.0);
		ts.addWayPoint(tp3);
		assertEquals(3,ts.size());
		//System.out.println(GpxFactory.format(cadgpx));
		
		BGpxFactory factory = BGpxFactory.getFactory(Type.BSimpleGpxFactory);
		BTrackSegment parsed=null;
		try {
			Method method=BGpxFactory.class.getDeclaredMethod(
				"parseTrackSegment", String.class);
			method.setAccessible(true);
			parsed = (BTrackSegment) method.invoke(factory, ts.asGpx());
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
		BGpxDocument doc = ExtendedGpxFactory.readGpxDocument(gpxFile);
		assertNotNull(doc);
		BTrack track = (BTrack)doc.getTracks().get(0);
		BTrackSegment segment = (BTrackSegment)track.nodes().get(0);
		assertEquals(9095, (int)segment.length());
		System.out.println("OK");
	}
	// TODO Falta test para método length()

}
