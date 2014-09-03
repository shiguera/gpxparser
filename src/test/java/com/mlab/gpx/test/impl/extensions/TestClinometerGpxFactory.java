package com.mlab.gpx.test.impl.extensions;

import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.mlab.gpx.api.GpxDocument;
import com.mlab.gpx.api.GpxFactory;
import com.mlab.gpx.api.GpxFactory.Type;
import com.mlab.gpx.api.WayPoint;
import com.mlab.gpx.impl.extensions.ClinometerGpxFactory;
import com.mlab.gpx.impl.extensions.ClinometerWayPoint;

public class TestClinometerGpxFactory {

	final String gpxdoc = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><gpx version=\"1.1\" "+
			"creator=\"MercatorLab - http:mercatorlab.com\" "+
			"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "+
			"xmlns=\"http://www.topografix.com/GPX/1/1\" "+
			"xsi:schemaLocation=\"http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd\" "+
			"xmlns:mlab=\"http://mercatorlab.com/downloads/mlab.xsd\"><metadata></metadata>"+
			"<wpt  lat=\"43.500000\" lon=\"-3.900000\"><ele>900.00</ele><time>1970-01-01T00:00:01.01Z</time>"+
			"<name>Prueba point</name><desc>Punto de pruebas</desc><extensions>"+
			"<mlab:bearing>175.30</mlab:bearing><mlab:speed>35.00</mlab:speed>"+
			"<mlab:escora>1.000000</mlab:escora><mlab:cabeceo>1.000000</mlab:cabeceo>"+
			"<mlab:guinada>1.000000</mlab:guinada>"+
			"</extensions></wpt></gpx>";
	
	@Test
	public void test() {
		System.out.print("Testing ClinometerGpxFactory()...");
		GpxFactory factory = new ClinometerGpxFactory();
		Assert.assertNotNull(factory);
		
		GpxFactory factory2 = GpxFactory.getFactory(Type.ClinometerGpxFactory);
		Assert.assertNotNull(factory2);
		System.out.println("OK");

	}
	
	@Test
	public void testCreateWayPoint() {
		System.out.print("Testing ClinometerGpxFactory.createWayPoint()...");
		GpxFactory factory = GpxFactory.getFactory(Type.ClinometerGpxFactory);
		Assert.assertNotNull(factory);	
		List<Double> values = Arrays.asList(new Double[]{-3.9,43.5,900.0,35.0,175.3,-1.0,1.0,1.0,1.0});
		WayPoint wp = factory.createWayPoint("Prueba point", "Punto de pruebas", 1000l, values);
		Assert.assertNotNull(wp);
		
		//System.out.println(wp.asGpx());
		
		System.out.println("OK");

		
	}
	@Test
	public void testParseWayPointExtensions() {
		System.out.print("Testing ClinometerGpxFactory.ParseWayPointExtensions()...");
		GpxFactory factory = GpxFactory.getFactory(Type.ClinometerGpxFactory);
		GpxDocument doc = factory.createGpxDocument();
		List<Double> values = Arrays.asList(new Double[]{-3.9,43.5,900.0,35.0,175.3,-1.0,1.0,1.0,1.0});
		WayPoint wp = factory.createWayPoint("Prueba point", "Punto de pruebas", 1000l, values);
		doc.addWayPoint(wp);
		Assert.assertEquals(1,doc.getWayPoints().size());
		//System.out.println(doc.asGpx());
		System.out.println("OK");
	}
	
	@Test
	public void testParseGpxDocument() {
		System.out.print("Testing ClinometerGpxFactory.ParseGpxDocument()...");
		GpxFactory factory = GpxFactory.getFactory(Type.ClinometerGpxFactory);
		GpxDocument doc = factory.parseGpxDocument(gpxdoc);
		Assert.assertNotNull(doc);
		//System.out.println("NUMWAYPOINTS:"+doc.getWayPoints().size());
		Assert.assertEquals(1,doc.getWayPoints().size());
		Assert.assertEquals(1.0,((ClinometerWayPoint)(doc.getWayPoints().get(0))).getEscora());
		Assert.assertEquals(1.0,((ClinometerWayPoint)(doc.getWayPoints().get(0))).getCabeceo());
		Assert.assertEquals(1.0,((ClinometerWayPoint)(doc.getWayPoints().get(0))).getGuinada());
		System.out.println("OK");
	}
	
//	@Test
//	public void testAsCsv() {
//		System.out.print("Testing ClinometerGpxFactory.asCsv()...");
//		GpxFactory factory = GpxFactory.getFactory(Type.ClinometerGpxFactory);
//		List<Double> values = Arrays.asList(new Double[]{-3.9,43.5,900.0,35.0,175.3,-1.0,1.0,1.0,1.0,1000.0});
//		WayPoint wp = factory.createWayPoint("Prueba point", "Punto de pruebas", 1000l, values);
//		//System.out.println(factory.asCsv(wp));
//		Assert.assertEquals("1970-01-01T00:00:01.01Z,1000,-3.900000,43.500000,900.00,35.000000,175.3,-1.0,1.000000,1.000000,1.000000,1000.00", 
//			factory.asCsv(wp));
//		System.out.println("OK");
//	}
		
	

}
