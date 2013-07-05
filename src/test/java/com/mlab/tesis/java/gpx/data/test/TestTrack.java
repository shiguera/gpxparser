package com.mlab.tesis.java.gpx.data.test;

import junit.framework.TestCase;

import com.mlab.tesis.java.gpx.data.SimpleWayPoint;
import com.mlab.tesis.java.gpx.data.Track;
import com.mlab.tesis.java.gpx.data.TrackSegment;
import com.mlab.tesis.java.gpx.data.WayPoint;

public class TestTrack extends TestCase {
	//private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	public void test() {
		//this.logger.info("TESTING Track");
		System.out.print("Testing Track()...");
		//
		Track track= new Track();
		assertNotNull(track);
		
		long t=System.currentTimeMillis();
		SimpleWayPoint tp= new SimpleWayPoint("Pto1","Punto de pruebas",t,-3.8,42.5,900.0,23.7,123.2,-1.0);
		SimpleWayPoint tp2= new SimpleWayPoint("Pto2","Punto de pruebas",t+1000l,-3.9,43.5,920.0,23.7,123.2,-1.0);
		TrackSegment ts=new TrackSegment();		
		ts.addWayPoint(tp);
		ts.addWayPoint(tp2);
		assertEquals(ts.size(), 2);
		//
		track.add(ts);
		assertEquals(track.size(), 1);
		
		SimpleWayPoint tp3= new SimpleWayPoint("Pto3","Punto de pruebas",t+2000l,-4.0,44.5,940.0,23.7,123.2,-1.0);
		SimpleWayPoint tp4= new SimpleWayPoint("Pto4","Punto de pruebas",t+3000l,-4.1,44.6,930.0,23.7,123.2,-1.0);
		TrackSegment ts2=new TrackSegment();		
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
		Track track= new Track();
		long t=System.currentTimeMillis();
		WayPoint tp= new SimpleWayPoint("Pto1","Punto de pruebas",t,-3.8,42.5,900.0,23.7,123.2,-1.0);
		WayPoint tp2= new SimpleWayPoint("Pto2","Punto de pruebas",t+1000l,-3.9,43.5,920.0,23.7,123.2,-1.0);
		TrackSegment ts=new TrackSegment();		
		ts.addWayPoint(tp);
		ts.addWayPoint(tp2);
		track.add(ts);
		//System.out.println(track.asCsv(false));
		//System.out.println(track.asCsv(false).substring(122,127));
		assertEquals("-3.90",track.asCsv(false).substring(122,127));
		System.out.println("OK");
	}
	
	// TODO Falta test para método length()

	
}
