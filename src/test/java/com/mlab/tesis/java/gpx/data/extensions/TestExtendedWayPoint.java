package com.mlab.tesis.java.gpx.data.extensions;

import java.util.Arrays;
import java.util.List;

import com.mlab.tesis.java.gpx.data.GpxFactory;
import com.mlab.tesis.java.gpx.data.WayPoint;
import com.mlab.tesis.java.gpx.data.GpxFactory.Type;

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
	
	public void testGetValues() {
		System.out.print("Testing ExtendedWayPoint.getValues()...");
		GpxFactory factory = GpxFactory.getFactory(Type.ExtendedGpxFactory);
		List<Double> values = Arrays.asList(new Double[]{-3.9,43.5,900.0,35.0,175.3,-1.0,1.0,1.0,1.0,1000.0});
		WayPoint wp = factory.createWayPoint("Prueba point", "Punto de pruebas", 1000l, values);
		assertEquals(10,wp.getValues().length);
		System.out.println("OK");
	}

}
