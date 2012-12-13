package mlab.tesis.java.gpx.data;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mlab.tesis.java.gpx.data.GpxDocument;
import com.mlab.tesis.java.gpx.data.Track;
import com.mlab.tesis.java.gpx.data.TrackSegment;
import com.mlab.tesis.java.gpx.data.WayPoint;

public class GpxDocumentTest extends TestCase {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	private GpxDocument gpxdoc=null;
	public void test() {
		this.logger.info("TESTING GpxDocument Constructor");
		
		// Constructor
		gpxdoc=new GpxDocument();
		assertNotNull(gpxdoc);

		
		Track track=new Track();
		long t=System.currentTimeMillis();
		WayPoint tp= new WayPoint("Pto1","Punto de pruebas",t,-3.8,42.5,900.0,23.7,123.2,-1.0);
		WayPoint tp2= new WayPoint("Pto2","Punto de pruebas",t,-3.9,43.5,920.0,23.7,123.2,-1.0);
		TrackSegment ts=new TrackSegment();		
		ts.addWayPoint(tp);
		ts.addWayPoint(tp2);
		track.segments.add(ts);
		
		
		gpxdoc.addTrack(track);
		assertEquals(1,this.gpxdoc.trackCount());
		
		String cadxml=this.gpxdoc.asGpx();
		logger.info("\n"+GpxDocument.format(cadxml));			
		assertNotNull(cadxml);
	}
	public void testAsGpx() {
//		this.logger.info("TESTING GpxDocument.asGpx()");
//		String cadxml=this.gpxdoc.asGpx();
//		logger.info("\n"+GpxDocument.format(cadxml));			
//		assertNotNull(cadxml);
	}
	
//	public void testParseGpx() {
//		this.logger.info("TESTING GpxDocument.parseGpx()");
//		// Construir el documento GPX
//		gpxdoc=new GpxDocument();
//		long t=System.currentTimeMillis();
//		WayPoint tp= new WayPoint("Pto1","Punto de pruebas",t,-3.8,42.5,900.0,23.7,123.2,-1.0);
//		gpxdoc.addWayPoint(tp);
//		WayPoint tp2= new WayPoint("Pto2","Punto de pruebas",t+1000,-3.9,43.5,920.0,23.7,123.2,-1.0);
//		gpxdoc.addWayPoint(tp2);
//		
//		String cadgpx=gpxdoc.asGpx();
//		GpxDocument parsed= GpxDocument.parseGpxDocument(cadgpx);
//		
//		//assertEquals(2,parsed.wayPointCount());
//	}
}
