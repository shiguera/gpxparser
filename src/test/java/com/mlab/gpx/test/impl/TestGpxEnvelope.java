package com.mlab.gpx.test.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.mlab.gpx.impl.GpxEnvelope;

public class TestGpxEnvelope {

	private GpxEnvelope env1, env2;
	
	@Before 
	public void before() {
		env1 = new GpxEnvelope();
		env2 = new GpxEnvelope(35.0,-9.0,42.0,3.0);
	}
	@Test
	public void test() {
		System.out.print("Testing GpxEnvelope.test()...");
		assertEquals(env1.getMinCorner()[0],0.0,0.01);
		
		
		System.out.println("OK");
	}
	@Test
	public void testGetCorners() {
		System.out.print("Testing GpxEnvelope.testGetCorners()...");
		assertEquals(env1.getMinCorner()[0],0.0,0.01);
		assertEquals(env1.getMinCorner()[1],0.0,0.01);
		assertEquals(env1.getMaxCorner()[0],0.0,0.01);
		assertEquals(env1.getMaxCorner()[1],0.0,0.01);

		assertEquals(env2.getMinCorner()[0],35.0,0.01);
		assertEquals(env2.getMinCorner()[1],-9.0,0.01);
		assertEquals(env2.getMaxCorner()[0],42.0,0.01);
		assertEquals(env2.getMaxCorner()[1],3.0,0.01);

		System.out.println("OK");
	}
	@Test
	public void testUpdate() {
		System.out.print("Testing GpxEnvelope.testUpdate()...");
		env1.update(10.0, 10.0);
		assertEquals(env1.getMinCorner()[0],10.0,0.01);
		assertEquals(env1.getMinCorner()[1],10.0,0.01);
		assertEquals(env1.getMaxCorner()[0],10.0,0.01);
		assertEquals(env1.getMaxCorner()[1],10.0,0.01);
		
		env2.update(47.0, -12.0);
		assertEquals(env2.getMinCorner()[0],35.0,0.01);
		assertEquals(env2.getMinCorner()[1],-12.0,0.01);
		assertEquals(env2.getMaxCorner()[0],47.0,0.01);
		assertEquals(env2.getMaxCorner()[1],3.0,0.01);

		env1.update(env2);
		assertEquals(env1.getMinCorner()[0],10.0,0.01);
		assertEquals(env1.getMinCorner()[1],-12.0,0.01);
		assertEquals(env1.getMaxCorner()[0],47.0,0.01);
		assertEquals(env1.getMaxCorner()[1],10.0,0.01);

		System.out.println("OK");
	}
	@Test
	public void testGetCenter() {
		System.out.print("Testing GpxEnvelope.testGetCenter()...");

		double[] center = env2.getCenter();
		assertEquals(center[0],38.5,0.01);
		assertEquals(center[1],-3.0,0.01);

		System.out.println("OK");
	}

}
