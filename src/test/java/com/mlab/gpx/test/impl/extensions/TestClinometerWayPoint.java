package com.mlab.gpx.test.impl.extensions;

import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;

import com.mlab.gpx.api.GpxFactory;
import com.mlab.gpx.api.GpxFactory.Type;
import com.mlab.gpx.api.WayPoint;
import com.mlab.gpx.impl.extensions.ClinometerWayPoint;
import com.mlab.gpx.impl.extensions.ClinometerWayPoint;

public class TestClinometerWayPoint extends TestCase {

	ClinometerWayPoint wp;
	
	@Override
	protected void setUp() throws Exception {
		wp = new ClinometerWayPoint();
		super.setUp();
	}

	public void test() {
		System.out.print("Testing ClinometerWayPoint()...");
		assertNotNull(wp);
		System.out.println("OK");
	}
	public void testExtensionsAsGpx() {
		System.out.print("Testing ClinometerWayPoint.extensionsAsGpx()...");
		//System.out.println(wp.asGpx());
		assertEquals("<extensions><mlab:speed>-1.000000</mlab:speed><mlab:bearing>-1.0</mlab:bearing>"+
				"<mlab:accuracy>-1.0</mlab:accuracy><mlab:escora>0.000000</mlab:escora>"+
				"<mlab:cabeceo>0.000000</mlab:cabeceo><mlab:guinada>0.000000</mlab:guinada>"+
				"</extensions>", 
			wp.extensionsAsGpx());
		System.out.println("OK");
	}
	
	@Test
	public void testGetValues() {
		System.out.print("Testing ClinometerWayPoint.getValues()...");
		GpxFactory factory = GpxFactory.getFactory(Type.ClinometerGpxFactory);
		List<Double> values = Arrays.asList(new Double[]{-3.9,43.5,900.0,35.0,175.3,-1.0,1.0,1.0,1.0});
		WayPoint wp = factory.createWayPoint("Prueba point", "Punto de pruebas", 1000l, values);
		assertEquals(9,wp.getValues().length);
		System.out.println("OK");
	}
	@Test
	public void testClinometerWayPoint() {
		System.out.print("Testing ClinometerWayPoint...");

		// Basic constructor
		ClinometerWayPoint wp = new ClinometerWayPoint();
		Assert.assertNotNull(wp);
		Assert.assertEquals("", wp.getName());
		Assert.assertEquals("", wp.getDescription());
		Assert.assertEquals(-1l, wp.getTime());
		Assert.assertEquals(0.0, wp.getLongitude());
		Assert.assertEquals(0.0, wp.getLatitude());
		Assert.assertEquals(0.0, wp.getAltitude());
		Assert.assertEquals(-1.0, wp.getSpeed());
		Assert.assertEquals(-1.0, wp.getBearing());
		Assert.assertEquals(-1.0, wp.getAccuracy());
		Assert.assertEquals(0.0, wp.getEscora());
		Assert.assertEquals(0.0, wp.getCabeceo());
		Assert.assertEquals(0.0, wp.getGuinada());

		// Constructor with parameters
		ClinometerWayPoint wp2 = new ClinometerWayPoint("P1", "Pto de pruebas", 1000l, -3.5, 42.9, 960.0,
				30.0,120.0,10.0, 1.0, 2.0, 3.0);
		Assert.assertNotNull(wp2);
		Assert.assertEquals("P1", wp2.getName());
		Assert.assertEquals("Pto de pruebas", wp2.getDescription());
		Assert.assertEquals(1000l, wp2.getTime());
		Assert.assertEquals(-3.5, wp2.getLongitude());
		Assert.assertEquals(42.9, wp2.getLatitude());
		Assert.assertEquals(960.0, wp2.getAltitude());
		Assert.assertEquals(30.0, wp2.getSpeed());
		Assert.assertEquals(120.0, wp2.getBearing());
		Assert.assertEquals(10.0, wp2.getAccuracy());
		Assert.assertEquals(1.0, wp2.getEscora());
		Assert.assertEquals(2.0, wp2.getCabeceo());
		Assert.assertEquals(3.0, wp2.getGuinada());
		
		// Constructor from values
		List<Double> values = Arrays.asList(new Double[]{-3.5, 42.9, 960.0, 30.0, 120.0, 10.0, 1.0, 2.0, 3.0});
		ClinometerWayPoint wp3 = new ClinometerWayPoint("P1", "Pto de pruebas", 1000l, values);
		Assert.assertEquals("P1", wp3.getName());
		Assert.assertEquals("Pto de pruebas", wp3.getDescription());
		Assert.assertEquals(1000l, wp3.getTime());
		Assert.assertEquals(-3.5, wp3.getLongitude());
		Assert.assertEquals(42.9, wp3.getLatitude());
		Assert.assertEquals(960.0, wp3.getAltitude());
		Assert.assertEquals(30.0, wp3.getSpeed());
		Assert.assertEquals(120.0, wp3.getBearing());
		Assert.assertEquals(10.0, wp3.getAccuracy());
		Assert.assertEquals(1.0, wp3.getEscora());
		Assert.assertEquals(2.0, wp3.getCabeceo());
		Assert.assertEquals(3.0, wp3.getGuinada());
		
		System.out.println("OK");
		
	}

	@Test
	public void testClone() {
		System.out.print("Testing BClinometerWayPoint.clone()...");
		ClinometerWayPoint wp = new ClinometerWayPoint("P1", "Pto de pruebas", 1000l, -3.5, 42.9, 960.0,
				30.0,120.0,10.0, 1.0, 2.0, 3.0);
		ClinometerWayPoint wp2 = wp.clone();
		Assert.assertNotNull(wp2);
		Assert.assertEquals("P1", wp2.getName());
		Assert.assertEquals("Pto de pruebas", wp2.getDescription());
		Assert.assertEquals(1000l, wp2.getTime());
		Assert.assertEquals(-3.5, wp2.getLongitude());
		Assert.assertEquals(42.9, wp2.getLatitude());
		Assert.assertEquals(960.0, wp2.getAltitude());
		Assert.assertEquals(30.0, wp2.getSpeed());
		Assert.assertEquals(120.0, wp2.getBearing());
		Assert.assertEquals(10.0, wp2.getAccuracy());
		Assert.assertEquals(1.0, wp2.getEscora());
		Assert.assertEquals(2.0, wp2.getCabeceo());
		Assert.assertEquals(3.0, wp2.getGuinada());

		System.out.println("OK");
	}
	
	@Test
	public void testAsCsv() {
		System.out.print("Testing BClinometerWayPoint.asCsv()...");
		ClinometerWayPoint wp = new ClinometerWayPoint("P1", "Pto de pruebas", 1000l, -3.5, 42.9, 960.0,
				30.0,120.0,10.0, 1.0, 2.0, 3.0);
		
		//System.out.println("\n"+wp.asCsv(false));		
		//System.out.println("\n"+wp.asCsv(true));
		Assert.assertEquals("1970-01-01T00:00:01.01Z,1000,-3.500000,42.900000,960.00,30.000000,120.0,10.0,1.000000,2.000000,3.000000", 
			wp.asCsv(false));
		Assert.assertEquals("1970-01-01T00:00:01.01Z,1000,-3.500000,42.900000,960.00,459179.76,4749831.30,30.000000,120.0,10.0,1.000000,2.000000,3.000000", 
			wp.asCsv(true));
			
		System.out.println("OK");
	}

	@Test
	public void testAsGpx() {
		System.out.print("Testing BClinometerWayPoint.asGpx()...");
		ClinometerWayPoint wp = new ClinometerWayPoint("P1", "Pto de pruebas", 1000l, -3.5, 42.9, 960.0,
				30.0,120.0,10.0, 1.0, 2.0, 3.0);
		
		//System.out.println("\n"+wp.asGpx());		
		Assert.assertEquals("<wpt  lat=\"42.900000\" lon=\"-3.500000\"><ele>960.00</ele>"+
				"<time>1970-01-01T00:00:01.01Z</time><name>P1</name><desc>Pto de pruebas</desc>"+
				"<extensions><mlab:speed>30.000000</mlab:speed><mlab:bearing>120.0</mlab:bearing>"+
				"<mlab:accuracy>10.0</mlab:accuracy><mlab:escora>1.000000</mlab:escora>"+
				"<mlab:cabeceo>2.000000</mlab:cabeceo><mlab:guinada>3.000000</mlab:guinada>"+
				"</extensions></wpt>", 
			wp.asGpx());
		System.out.println("OK");
	}


}
