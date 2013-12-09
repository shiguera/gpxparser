package com.mlab.gpx.test.impl;

import junit.framework.TestCase;

import com.mlab.gpx.impl.SimpleAttribute;

public class TestSimpleGpxAttribute extends TestCase {
	
	public void test() {
		System.out.print("Testing SimpleGpxAttribute():... ");
		SimpleAttribute att = new SimpleAttribute();
		assertNotNull(att);
		att = new SimpleAttribute("lon","3.7508");
		assertNotNull(att);
		assertEquals("lon", att.getName());
		assertEquals("3.7508", att.getValue());
		
		
		System.out.println("OK");
	}
	public void testAsGpx() {
		System.out.print("Testing SimpleGpxAttribute.asGpx():... ");
		SimpleAttribute att = new SimpleAttribute("lon","3.7508");
		assertEquals("lon='3.7508'", att.asGpx());
		
		System.out.println("OK");
	}
	

}
