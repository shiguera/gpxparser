package com.mlab.gpx.test.impl;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.mlab.gpx.api.GpxDocument;
import com.mlab.gpx.api.GpxFactory;
import com.mlab.gpx.impl.SimpleGpxDocument;
import com.mlab.gpx.impl.SimpleWayPoint;
import com.mlab.gpx.impl.Track;
import com.mlab.gpx.impl.TrackSegment;

public class TestSimpleGpxDocument extends TestCase {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	private final String cadxml = "<?xml version=\"1.0\"  encoding=\"UTF-8\"?>"+
			"<company><staff id=\"1001\"><firstname>yong</firstname><lastname>mook kim</lastname>"+
			"<nickname>mkyong</nickname><salary>100000</salary></staff><staff id=\"2001\">"+
			"<firstname>low</firstname><lastname>yin fong</lastname><nickname>fong fong</nickname>"+
			"<salary>200000</salary></staff></company>";

	
	private SimpleGpxDocument gpxdoc=null;
	private GpxFactory factory;
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		factory = GpxFactory.getFactory(GpxFactory.Type.SimpleGpxFactory);
		gpxdoc=buildGpxdoc();	
	}
	private SimpleGpxDocument buildGpxdoc() {
		GpxDocument gpxdoc=(SimpleGpxDocument) factory.createGpxDocument();
		Track track=new Track();
		long t=System.currentTimeMillis();
		SimpleWayPoint tp= new SimpleWayPoint("Pto1","Punto de pruebas",t,-3.8,42.5,900.0);
		gpxdoc.addWayPoint(tp);
		SimpleWayPoint tp2= new SimpleWayPoint("Pto2","Punto de pruebas",t+1000,-3.9,43.5,920.0);
		gpxdoc.addWayPoint(tp2);
		TrackSegment ts=new TrackSegment();		
		ts.addWayPoint(tp);
		ts.addWayPoint(tp2);
		track.add(ts);		
		gpxdoc.addTrack(track);
		return (SimpleGpxDocument) gpxdoc;		
	}
	
	public void test() {
		this.logger.info("TESTING GpxDocument Constructor");
		// Constructor
		assertNotNull(gpxdoc);
		assertEquals(2,this.gpxdoc.wayPointCount());
		assertEquals(1,this.gpxdoc.trackCount());		
		
		String cadxml=this.gpxdoc.asGpx();
		assertNotNull(cadxml);		
	}
	public void testAsGpx() {
		this.logger.info("TESTING GpxDocument.asGpx()");
		gpxdoc = buildGpxdoc();
		String cadxml=this.gpxdoc.asGpx();
		assertNotNull(cadxml);
	}

	public void testGetDomDocument() {
		this.logger.info("TESTING GpxDocument.getDomDocument()");
		Document domdoc = gpxdoc.getDomDocument();
		assertNotNull(domdoc);
		Element ele = domdoc.getDocumentElement();
		assertNotNull(ele);
		assertEquals("gpx",ele.getNodeName());
	}
	
	

	
}
