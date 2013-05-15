package com.mlab.tesis.java;

import com.mlab.tesis.java.gpx.data.SimpleGpxDocument;
import com.mlab.tesis.java.gpx.data.GpxFactory;
import com.mlab.tesis.java.gpx.data.Track;
import com.mlab.tesis.java.gpx.data.Util;
import com.mlab.tesis.java.gpx.data.SimpleWayPoint;
import com.mlab.tesis.java.gpx.data.TrackSegment;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	SimpleGpxDocument gpxdoc=new SimpleGpxDocument(GpxFactory.Type.SimpleGpxFactory);
		
		Track track=new Track();
		long t=System.currentTimeMillis();
		SimpleWayPoint tp= new SimpleWayPoint("Pto1","Punto de pruebas",t,-3.8,42.5,900.0,23.7,123.2,-1.0);
		SimpleWayPoint tp2= new SimpleWayPoint("Pto2","Punto de pruebas",t,-3.9,43.5,920.0,23.7,123.2,-1.0);
		//TrackSegment ts=new TrackSegment();		
		track.addWayPoint(tp,true);
		track.addWayPoint(tp2, false);
		//track.segments.add(ts);
		
		
		gpxdoc.addTrack(track);
		
		System.out.println(gpxdoc.format());
		
		Util.write("prueba.gpx", gpxdoc.format());
    }
}
