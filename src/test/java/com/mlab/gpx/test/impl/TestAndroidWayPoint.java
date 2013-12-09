package com.mlab.gpx.test.impl;

import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mlab.gpx.impl.AndroidWayPoint;

public class TestAndroidWayPoint {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() {
		System.out.print("Testing AndroidWayPoint...");
		AndroidWayPoint wp = new AndroidWayPoint();
		Assert.assertNotNull(wp);
		Assert.assertEquals(-1.0, wp.getSpeed());
		Assert.assertEquals(-1.0, wp.getBearing());
		Assert.assertEquals(-1.0, wp.getAccuracy());
		
		AndroidWayPoint wp2 = new AndroidWayPoint("P1", "Pto de pruebas", 1000l, -3.5, 42.9, 960.0,
				30.0,120.0,10.0);
		Assert.assertNotNull(wp2);
		Assert.assertEquals(30.0, wp2.getSpeed());
		Assert.assertEquals(120.0, wp2.getBearing());
		Assert.assertEquals(10.0, wp2.getAccuracy());
		
		
		List<Double> values = Arrays.asList(new Double[]{-3.5, 42.9, 960.0, 30.0, 120.0, 10.0});
		AndroidWayPoint wp3 = new AndroidWayPoint("P1", "Pto de pruebas", 1000l, values);
		Assert.assertNotNull(wp3);
		Assert.assertEquals(30.0, wp3.getSpeed());
		Assert.assertEquals(120.0, wp3.getBearing());
		Assert.assertEquals(10.0, wp3.getAccuracy());
				
		System.out.println("OK");
	}

	@Test
	public void testClone() {
		System.out.print("Testing AndroidWayPoint.clone()...");
		AndroidWayPoint wp = new AndroidWayPoint("P1", "Pto de pruebas", 1000l, -3.5, 42.9, 960.0,
				30.0,120.0,10.0);
		Assert.assertNotNull(wp);
		AndroidWayPoint wp2 = wp.clone();
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

		System.out.println("OK");
		
	}
	@Test
	public void testAsCsv() {
		System.out.print("Testing AndroidWayPoint.asCsv()...");
		AndroidWayPoint wp = new AndroidWayPoint("P1", "Pto de pruebas", 1000l, -3.5, 42.9, 960.0,
				30.0,120.0,10.0);
		Assert.assertNotNull(wp);
		//System.out.println(wp.asCsv(true));
		Assert.assertEquals("1970-01-01T00:00:01.01Z,1000,-3.500000,42.900000,960.00,459179.76,4749831.30,30.000000,120.0,10.0", 
				wp.asCsv(true));
		Assert.assertEquals("1970-01-01T00:00:01.01Z,1000,-3.500000,42.900000,960.00,30.000000,120.0,10.0", 
				wp.asCsv(false));
		System.out.println("OK");
	}
	
	@Test
	public void testAsGpx() {
		System.out.print("Testing AndroidWayPoint.asGpx()...");
		AndroidWayPoint wp = new AndroidWayPoint("P1", "Pto de pruebas", 1000l, -3.5, 42.9, 960.0,
				30.0,120.0,10.0);
		Assert.assertNotNull(wp);
		//System.out.println(wp.asGpx());
		Assert.assertEquals("<wpt  lat=\"42.900000\" lon=\"-3.500000\"><ele>960.00</ele><time>1970-01-01T00:00:01.01Z</time><name>P1</name><desc>Pto de pruebas</desc><extensions><mlab:speed>30.000000</mlab:speed><mlab:bearing>120.0</mlab:bearing><mlab:accuracy>10.0</mlab:accuracy></extensions></wpt>", 
				wp.asGpx());
		System.out.println("OK");
	}
	
	
	

}
