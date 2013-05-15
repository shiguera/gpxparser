package com.mlab.tesis.java.gpx.data.test;

import junit.framework.TestCase;

import com.mlab.tesis.java.gpx.data.SimpleGpxAttribute;

public class TestSimpleGpxAttribute extends TestCase {
	
	public void test() {
		System.out.print("Testing SimpleGpxAttribute():... ");
		SimpleGpxAttribute att = new SimpleGpxAttribute();
		assertNotNull(att);
		att = new SimpleGpxAttribute("lon","3.7508");
		assertNotNull(att);
		assertEquals("lon", att.getName());
		assertEquals("3.7508", att.getValue());
		
		
		System.out.println("OK");
	}
	public void testAsGpx() {
		System.out.print("Testing SimpleGpxAttribute.asGpx():... ");
		SimpleGpxAttribute att = new SimpleGpxAttribute("lon","3.7508");
		assertEquals("lon='3.7508'", att.asGpx());
		
		System.out.println("OK");
	}
	

}
