package com.mlab.tesis.java.gpx.data.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.mlab.tesis.java.gpx.data.SimpleGpxDocument;
import com.mlab.tesis.java.gpx.data.GpxFactory;
import com.mlab.tesis.java.gpx.data.Track;
import com.mlab.tesis.java.gpx.data.TrackSegment;
import com.mlab.tesis.java.gpx.data.SimpleWayPoint;

public class TestSimpleGpxDocument extends TestCase {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	private final String cadxml = "<?xml version=\"1.0\"  encoding=\"UTF-8\"?>"+
			"<company><staff id=\"1001\"><firstname>yong</firstname><lastname>mook kim</lastname>"+
			"<nickname>mkyong</nickname><salary>100000</salary></staff><staff id=\"2001\">"+
			"<firstname>low</firstname><lastname>yin fong</lastname><nickname>fong fong</nickname>"+
			"<salary>200000</salary></staff></company>";

	
	private SimpleGpxDocument gpxdoc=null;
	
	public void test() {
		this.logger.info("TESTING GpxDocument Constructor");
		
		// Constructor
		gpxdoc=buildGpxdoc();
		
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

	public void testNodeAsString() {
		this.logger.info("TESTING GpxDocument.nodeAsString()");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder=null;
		Document doc = null;
		try {
			builder = factory.newDocumentBuilder();
			InputSource inputSource = new InputSource();
			inputSource.setCharacterStream(new StringReader(cadxml));
			doc = builder.parse(inputSource);
		} catch (ParserConfigurationException e) {
			this.logger.error("testNodeAsString() ERROR : Can't create DocumentBuilder");
			e.printStackTrace();
		} catch (SAXException e) {
			this.logger.error("testNodeAsString() ERROR : Can't parse xml String");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(doc);
	}

	public void testParseGpxString() {
		this.logger.info("TESTING GpxDocument.parseGpxString()");
		gpxdoc = buildGpxdoc();
		//System.out.println(GpxFactory.format(gpxdoc.asGpx()));
		assertNotNull(gpxdoc);
		assertEquals(2,gpxdoc.wayPointCount());
		assertEquals(1,gpxdoc.trackCount());		
	
		if(gpxdoc != null) {
			String cadgpx=gpxdoc.asGpx();
			GpxFactory factory = GpxFactory.getFactory(GpxFactory.Type.SimpleGpxFactory);
			SimpleGpxDocument parsed= factory.parseGpxDocument(cadgpx);
			assertNotNull(parsed);
			assertEquals(2,parsed.wayPointCount());
			assertEquals(1,gpxdoc.trackCount());		
			logger.info("\n"+GpxFactory.format(cadgpx));			

		} else {
			// Error en el constructor, no aquí
			this.logger.info("parseGpxString(): Error en el constructor, no aquí");
			assertEquals(1,2);
		}
	}
	
	private SimpleGpxDocument buildGpxdoc() {
		SimpleGpxDocument gpxdoc=new SimpleGpxDocument(GpxFactory.Type.SimpleGpxFactory);
		Track track=new Track();
		long t=System.currentTimeMillis();
		SimpleWayPoint tp= new SimpleWayPoint("Pto1","Punto de pruebas",t,-3.8,42.5,900.0,23.7,123.2,-1.0);
		gpxdoc.addWayPoint(tp);
		SimpleWayPoint tp2= new SimpleWayPoint("Pto2","Punto de pruebas",t+1000,-3.9,43.5,920.0,23.7,123.2,-1.0);
		gpxdoc.addWayPoint(tp2);
		TrackSegment ts=new TrackSegment();		
		ts.addWayPoint(tp);
		ts.addWayPoint(tp2);
		track.segments.add(ts);		
		gpxdoc.addTrack(track);
		return gpxdoc;		
	}
}
