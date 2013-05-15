package com.mlab.tesis.java.gpx.data.test;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mlab.tesis.java.gpx.data.Track;
import com.mlab.tesis.java.gpx.data.SimpleWayPoint;
import com.mlab.tesis.java.gpx.data.TrackSegment;

public class TrackTest extends TestCase {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	public void test() {
		this.logger.info("TESTING TrackSegment");
		
		//
		Track track= new Track();
		assertNotNull(track);
		
		long t=System.currentTimeMillis();
		SimpleWayPoint tp= new SimpleWayPoint("Pto1","Punto de pruebas",t,-3.8,42.5,900.0,23.7,123.2,-1.0);
		SimpleWayPoint tp2= new SimpleWayPoint("Pto2","Punto de pruebas",t,-3.9,43.5,920.0,23.7,123.2,-1.0);
		TrackSegment ts=new TrackSegment();		
		ts.addWayPoint(tp);
		ts.addWayPoint(tp2);

		//
		track.segments.add(ts);
		assertEquals(track.segments.size(), 1);
		
		SimpleWayPoint tp3= new SimpleWayPoint("Pto3","Punto de pruebas",t,-4.0,44.5,940.0,23.7,123.2,-1.0);
		SimpleWayPoint tp4= new SimpleWayPoint("Pto4","Punto de pruebas",t,-4.1,44.6,930.0,23.7,123.2,-1.0);
		TrackSegment ts2=new TrackSegment();		
		ts2.addWayPoint(tp3);
		ts2.addWayPoint(tp4);
		
		//
		track.segments.add(ts2);
		assertEquals(track.segments.size(), 2);
		
		
		//this.logger.info("\n\n------------------------------------------------------------------\n"+
		//		track.asGpx()+"\n--------------------------------------------------------------------");
		
	}
	
}
