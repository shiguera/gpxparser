package com.mlab.tesis.java.gpx.data.extensions;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.mlab.gpx.api.GpxFactory;
import com.mlab.gpx.api.WayPoint;
import com.mlab.gpx.api.GpxFactory.Type;

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
		//System.out.println(wp.asGpx());
		assertEquals("<extensions><mlab:speed>-1.000000</mlab:speed><mlab:bearing>-1.0</mlab:bearing><mlab:accuracy>-1.0</mlab:accuracy><mlab:ax>0.000000</mlab:ax><mlab:ay>0.000000</mlab:ay><mlab:az>0.000000</mlab:az><mlab:pressure>0.00</mlab:pressure></extensions>", 
			wp.extensionsAsGpx());
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
