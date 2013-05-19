package com.mlab.tesis.java.gpx.data.extensions;

import junit.framework.TestCase;

public class TestExtendedWayPoint extends TestCase {

	ExtendedWayPoint wp;
	
	@Override
	protected void setUp() throws Exception {
		wp = new ExtendedWayPoint();
		super.setUp();
	}

	public void test() {
		System.out.print("Testing ExtendedWayPoint()...");
		assertNotNull(wp);
		System.out.println("OK");
	}
	public void testExtensionsAsGpx() {
		System.out.print("Testing ExtendedWayPoint.extensionsAsGpx()...");
		assertEquals("<mlab:ax>0.000000</mlab:ax><mlab:ay>0.000000</mlab:ay><mlab:az>0.000000</mlab:az><mlab:pressure>0.00</mlab:pressure>", wp.extensionsAsGpx());
		System.out.println("OK");
	}

}
