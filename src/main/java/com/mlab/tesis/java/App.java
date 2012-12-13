package com.mlab.tesis.java;

import com.mlab.tesis.java.gpx.data.GpxDocument;
import com.mlab.tesis.java.gpx.data.Track;
import com.mlab.tesis.java.gpx.data.WayPoint;
import com.mlab.tesis.java.gpx.data.TrackSegment;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	GpxDocument gpxdoc=new GpxDocument();
		
		Track track=new Track();
		long t=System.currentTimeMillis();
		WayPoint tp= new WayPoint("Pto1","Punto de pruebas",t,-3.8,42.5,900.0,23.7,123.2,-1.0);
		WayPoint tp2= new WayPoint("Pto2","Punto de pruebas",t,-3.9,43.5,920.0,23.7,123.2,-1.0);
		//TrackSegment ts=new TrackSegment();		
		track.addWayPoint(tp,true);
		track.addWayPoint(tp2, false);
		//track.segments.add(ts);
		
		
		gpxdoc.addTrack(track);
		
		System.out.println(gpxdoc.format());
		
		gpxdoc.write("prueba.gpx", gpxdoc.format());
    }
}
