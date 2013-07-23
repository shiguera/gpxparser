package com.mlab.tesis.java.gpx.data.test;

import junit.framework.TestCase;

import com.mlab.tesis.java.gpx.data.BSimpleWayPoint;
import com.mlab.tesis.java.gpx.data.BTrack;
import com.mlab.tesis.java.gpx.data.BTrackSegment;
import com.mlab.tesis.java.gpx.data.BWayPoint;

public class TestTrack extends TestCase {
	//private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	public void test() {
		//this.logger.info("TESTING Track");
		System.out.print("Testing Track()...");
		//
		BTrack track= new BTrack();
		assertNotNull(track);
		
		long t=System.currentTimeMillis();
		BSimpleWayPoint tp= new BSimpleWayPoint("Pto1","Punto de pruebas",t,-3.8,42.5,900.0);
		BSimpleWayPoint tp2= new BSimpleWayPoint("Pto2","Punto de pruebas",t+1000l,-3.9,43.5,920.0);
		BTrackSegment ts=new BTrackSegment();		
		ts.addWayPoint(tp);
		ts.addWayPoint(tp2);
		assertEquals(ts.size(), 2);
		//
		track.add(ts);
		assertEquals(track.size(), 1);
		
		BSimpleWayPoint tp3= new BSimpleWayPoint("Pto3","Punto de pruebas",t+2000l,-4.0,44.5,940.0);
		BSimpleWayPoint tp4= new BSimpleWayPoint("Pto4","Punto de pruebas",t+3000l,-4.1,44.6,930.0);
		BTrackSegment ts2=new BTrackSegment();		
		ts2.addWayPoint(tp3);
		ts2.addWayPoint(tp4);
		assertEquals(ts.size(), 2);
		
		//
		track.add(ts2);
		assertEquals(track.size(), 2);
		
		
		//this.logger.info("\n\n------------------------------------------------------------------\n"+
		//		track.asGpx()+"\n--------------------------------------------------------------------");
	
		System.out.println("OK");
		
	}
	
	public void testAsCsv() {
		System.out.print("Testing Track.asCsv()...");
		BTrack track= new BTrack();
		long t=System.currentTimeMillis();
		BWayPoint tp= new BSimpleWayPoint("Pto1","Punto de pruebas",t,-3.8,42.5,900.0);
		BWayPoint tp2= new BSimpleWayPoint("Pto2","Punto de pruebas",t+1000l,-3.9,43.5,920.0);
		BTrackSegment ts=new BTrackSegment();		
		ts.addWayPoint(tp);
		ts.addWayPoint(tp2);
		track.add(ts);
		//System.out.println(track.asCsv(false));
		//System.out.println(track.asCsv(false).substring(123,126));
		assertEquals("920",track.asCsv(false).substring(123,126));
		System.out.println("OK");
	}
	
	// TODO Falta test para método length()

	
}
