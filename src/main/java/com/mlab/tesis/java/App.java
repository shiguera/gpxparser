package com.mlab.tesis.java;

import java.util.Arrays;
import java.util.List;

import com.mlab.tesis.java.gpx.Util;
import com.mlab.tesis.java.gpx.data.GpxFactory;
import com.mlab.tesis.java.gpx.data.GpxFactory.Type;
import com.mlab.tesis.java.gpx.data.SimpleGpxDocument;
import com.mlab.tesis.java.gpx.data.SimpleWayPoint;
import com.mlab.tesis.java.gpx.data.Track;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
//    	GpxFactory factory = GpxFactory.getFactory(Type.SimpleGpxFactory);
//    	SimpleGpxDocument gpxdoc=(SimpleGpxDocument) factory.createGpxDocument();
//    	
//		Track track=new Track();
//		long t=System.currentTimeMillis();
//		SimpleWayPoint tp= new SimpleWayPoint("Pto1","Punto de pruebas",t,-3.8,42.5,900.0,23.7,123.2,-1.0);
//		SimpleWayPoint tp2= new SimpleWayPoint("Pto2","Punto de pruebas",t,-3.9,43.5,920.0,23.7,123.2,-1.0);
//		//TrackSegment ts=new TrackSegment();		
//		track.addWayPoint(tp,true);
//		track.addWayPoint(tp2, false);
//		//track.segments.add(ts);
//		
//		
//		gpxdoc.addTrack(track);
//		
//		System.out.println(gpxdoc.format());
//		
//		Util.write("prueba.gpx", gpxdoc.format());

		Double[] val = new Double[]{2.0,3.0,4.0};
		List listval = Arrays.asList(val);
		System.out.println(listval.size());
		
    }
}
