package com.mlab.gpx.test.impl.tserie;

import com.mlab.gpx.impl.tserie.TSerie;

import junit.framework.TestCase;

public class TestTSerie extends TestCase {
	
	public void test() {
		System.out.print("Testing TSerie():... ");
		TSerie tserie = new TSerie();
		assertNotNull(tserie);
		System.out.println("OK");
		
	}
	public void testSize() {
		System.out.print("Testing TSerie.size():... ");
		TSerie tserie = new TSerie();
		assertEquals(0,tserie.size());
		assertTrue(tserie.add(1000l, new double[]{1.0,2.0,3.0}));
		assertTrue(tserie.add(2000l, new double[]{2.0,3.0,4.0}));
		assertEquals(2,tserie.size());
		System.out.println("OK");
		
	}
	public void testAdd() {
		System.out.print("Testing TSerie.add():... ");
		TSerie tserie = new TSerie();
		assertFalse(tserie.add(1000l, null));
		assertTrue(tserie.add(1000l, new double[]{1.0,2.0,3.0}));
		assertTrue(tserie.add(2000l, new double[]{2.0,3.0,4.0}));
		assertFalse(tserie.add(1000l, new double[]{1.0,2.0,3.0}));
		assertFalse(tserie.add(3000l, new double[]{1.0,2.0}));
				
		System.out.println("OK");
		
	}
	public void testGetValues() {
		System.out.print("Testing TSerie.getValues(long):... ");
		TSerie tserie = new TSerie();
		assertTrue(tserie.add(1000l, new double[]{1.0,2.0,3.0}));
		assertTrue(tserie.add(2000l, new double[]{2.0,3.0,4.0}));
		assertTrue(tserie.add(3000l, new double[]{3.0,4.0,5.0}));
		assertNull(tserie.getValues(-1l));
		assertNull(tserie.getValues(4000l));
		
		// t exacto indice 0
		double[] d = tserie.getValues(1000l);
		assertEquals(1.0,d[0]);
		assertEquals(2.0,d[1]);
		assertEquals(3.0,d[2]);
		// t exacto indice intermedio
		d = tserie.getValues(2000l);
		assertEquals(2.0,d[0]);
		assertEquals(3.0,d[1]);
		assertEquals(4.0,d[2]);
		// t exacto indice último
		d = tserie.getValues(3000l);
		assertEquals(3.0,d[0]);
		assertEquals(4.0,d[1]);
		assertEquals(5.0,d[2]);
		// t no exacto-> interpolación
		d = tserie.getValues(1500l);
		assertEquals(1.5,d[0]);
		assertEquals(2.5,d[1]);
		assertEquals(3.5,d[2]);
		
		System.out.println("OK");
		
	}

	
}
