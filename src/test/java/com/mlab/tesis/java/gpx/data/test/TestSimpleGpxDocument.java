package com.mlab.tesis.java.gpx.data.test;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.mlab.tesis.java.gpx.data.BGpxDocument;
import com.mlab.tesis.java.gpx.data.BGpxFactory;
import com.mlab.tesis.java.gpx.data.BSimpleGpxDocument;
import com.mlab.tesis.java.gpx.data.BSimpleWayPoint;
import com.mlab.tesis.java.gpx.data.BTrack;
import com.mlab.tesis.java.gpx.data.BTrackSegment;

public class TestSimpleGpxDocument extends TestCase {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	private final String cadxml = "<?xml version=\"1.0\"  encoding=\"UTF-8\"?>"+
			"<company><staff id=\"1001\"><firstname>yong</firstname><lastname>mook kim</lastname>"+
			"<nickname>mkyong</nickname><salary>100000</salary></staff><staff id=\"2001\">"+
			"<firstname>low</firstname><lastname>yin fong</lastname><nickname>fong fong</nickname>"+
			"<salary>200000</salary></staff></company>";

	
	private BSimpleGpxDocument gpxdoc=null;
	private BGpxFactory factory;
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		factory = BGpxFactory.getFactory(BGpxFactory.Type.BSimpleGpxFactory);
		gpxdoc=buildGpxdoc();	
	}
	private BSimpleGpxDocument buildGpxdoc() {
		BGpxDocument gpxdoc=(BSimpleGpxDocument) factory.createGpxDocument();
		BTrack track=new BTrack();
		long t=System.currentTimeMillis();
		BSimpleWayPoint tp= new BSimpleWayPoint("Pto1","Punto de pruebas",t,-3.8,42.5,900.0);
		gpxdoc.addWayPoint(tp);
		BSimpleWayPoint tp2= new BSimpleWayPoint("Pto2","Punto de pruebas",t+1000,-3.9,43.5,920.0);
		gpxdoc.addWayPoint(tp2);
		BTrackSegment ts=new BTrackSegment();		
		ts.addWayPoint(tp);
		ts.addWayPoint(tp2);
		track.add(ts);		
		gpxdoc.addTrack(track);
		return (BSimpleGpxDocument) gpxdoc;		
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
