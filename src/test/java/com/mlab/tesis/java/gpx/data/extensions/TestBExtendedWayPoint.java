package com.mlab.tesis.java.gpx.data.extensions;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.mlab.tesis.java.gpx.data.AndroidWayPoint;
import com.mlab.tesis.java.gpx.data.SimpleWayPoint;

public class TestBExtendedWayPoint {

	@Test
	public void test() {
		System.out.print("Testing BExtendedWayPoint...");

		// Basic constructor
		ExtendedWayPoint wp = new ExtendedWayPoint();
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
		Assert.assertEquals(0.0, wp.getAx());
		Assert.assertEquals(0.0, wp.getAy());
		Assert.assertEquals(0.0, wp.getAz());
		Assert.assertEquals(0.0, wp.getPressure());

		// Constructor with parameters
		ExtendedWayPoint wp2 = new ExtendedWayPoint("P1", "Pto de pruebas", 1000l, -3.5, 42.9, 960.0,
				30.0,120.0,10.0, 1.0, 2.0, 3.0, 1013.0);
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
		Assert.assertEquals(1.0, wp2.getAx());
		Assert.assertEquals(2.0, wp2.getAy());
		Assert.assertEquals(3.0, wp2.getAz());
		Assert.assertEquals(1013.0, wp2.getPressure());
		
		// Constructor from values
		List<Double> values = Arrays.asList(new Double[]{-3.5, 42.9, 960.0, 30.0, 120.0, 10.0, 1.0, 2.0, 3.0, 1013.0});
		ExtendedWayPoint wp3 = new ExtendedWayPoint("P1", "Pto de pruebas", 1000l, values);
		Assert.assertEquals("P1", wp3.getName());
		Assert.assertEquals("Pto de pruebas", wp3.getDescription());
		Assert.assertEquals(1000l, wp3.getTime());
		Assert.assertEquals(-3.5, wp3.getLongitude());
		Assert.assertEquals(42.9, wp3.getLatitude());
		Assert.assertEquals(960.0, wp3.getAltitude());
		Assert.assertEquals(30.0, wp3.getSpeed());
		Assert.assertEquals(120.0, wp3.getBearing());
		Assert.assertEquals(10.0, wp3.getAccuracy());
		Assert.assertEquals(1.0, wp3.getAx());
		Assert.assertEquals(2.0, wp3.getAy());
		Assert.assertEquals(3.0, wp3.getAz());
		Assert.assertEquals(1013.0, wp3.getPressure());
		
		System.out.println("OK");
		
	}

	@Test
	public void testClone() {
		System.out.print("Testing BExtendedWayPoint.clone()...");
		ExtendedWayPoint wp = new ExtendedWayPoint("P1", "Pto de pruebas", 1000l, -3.5, 42.9, 960.0,
				30.0,120.0,10.0, 1.0, 2.0, 3.0, 1013.0);
		ExtendedWayPoint wp2 = wp.clone();
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
		Assert.assertEquals(1.0, wp2.getAx());
		Assert.assertEquals(2.0, wp2.getAy());
		Assert.assertEquals(3.0, wp2.getAz());
		Assert.assertEquals(1013.0, wp2.getPressure());

		System.out.println("OK");
	}
	
	@Test
	public void testAsCsv() {
		System.out.print("Testing BExtendedWayPoint.asCsv()...");
		ExtendedWayPoint wp = new ExtendedWayPoint("P1", "Pto de pruebas", 1000l, -3.5, 42.9, 960.0,
				30.0,120.0,10.0, 1.0, 2.0, 3.0, 1013.0);
		
		//System.out.println("\n"+wp.asCsv(false));		
		//System.out.println("\n"+wp.asCsv(true));
		Assert.assertEquals("1970-01-01T00:00:01.01Z,1000,-3.500000,42.900000,960.00,30.000000,120.0,10.0,1.000000,2.000000,3.000000,1013.00", 
			wp.asCsv(false));
		Assert.assertEquals("1970-01-01T00:00:01.01Z,1000,-3.500000,42.900000,960.00,459179.76,4749831.30,30.000000,120.0,10.0,1.000000,2.000000,3.000000,1013.00", 
			wp.asCsv(true));
			
		System.out.println("OK");
	}

	@Test
	public void testAsGpx() {
		System.out.print("Testing BExtendedWayPoint.asGpx()...");
		ExtendedWayPoint wp = new ExtendedWayPoint("P1", "Pto de pruebas", 1000l, -3.5, 42.9, 960.0,
				30.0,120.0,10.0, 1.0, 2.0, 3.0, 1013.0);
		
		//System.out.println("\n"+wp.asGpx());		
		Assert.assertEquals("<wpt  lat=\"42.900000\" lon=\"-3.500000\"><ele>960.00</ele><time>1970-01-01T00:00:01.01Z</time><name>P1</name><desc>Pto de pruebas</desc><extensions><mlab:speed>30.000000</mlab:speed><mlab:bearing>120.0</mlab:bearing><mlab:accuracy>10.0</mlab:accuracy><mlab:ax>1.000000</mlab:ax><mlab:ay>2.000000</mlab:ay><mlab:az>3.000000</mlab:az><mlab:pressure>1013.00</mlab:pressure></extensions></wpt>", 
			wp.asGpx());
		System.out.println("OK");
	}

}
