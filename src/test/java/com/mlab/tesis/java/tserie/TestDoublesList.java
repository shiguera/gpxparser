package com.mlab.tesis.java.tserie;

import junit.framework.TestCase;

public class TestDoublesList extends TestCase {
	
	public void test() {
		System.out.print("Testing DoublesList():... ");
		DoublesList list = new DoublesList();
		assertNotNull(list);
		System.out.println("OK");
	}
	public void testSize() {
		System.out.print("Testing DoublesList.size():... ");
		DoublesList list = new DoublesList();
		assertEquals(0,list.size());
		assertTrue(list.add(new double[]{1.0,2.0,3.0}));
		assertTrue(list.add(new double[]{1.0,2.0,3.0}));
		assertEquals(2,list.size());
		System.out.println("OK");
	}
	
	
	public void testAdd() {
		System.out.print("Testing DoublesList.add():... ");
		DoublesList list = new DoublesList();
		assertTrue(list.add(new double[]{1.0,2.0,3.0}));
		assertEquals(3,list.getDimension());
		assertFalse(list.add(new double[]{1.0,2.0}));
		assertTrue(list.add(new double[]{4.0,5.0,6.0}));
		assertEquals(2,list.size());
		System.out.println("OK");
	}

	public void testGet() {
		System.out.print("Testing DoublesList.get():... ");
		DoublesList list = new DoublesList();
		list.add(new double[]{1.0,2.0,3.0});
		list.add(new double[]{1.0,2.0,3.0});
		list.add(new double[]{1.0,2.0,3.0});
		list.add(new double[]{1.0,2.0,3.0});
		
		Throwable thro = null;
		try {
			double[] t = list.get(5);
		} catch (Throwable e) {
			thro = e;
		}
		assertNotNull(thro);
		assertTrue(IndexOutOfBoundsException.class.isAssignableFrom(thro.getClass()));

		double[] d = list.get(2);
		assertNotNull(d);
		assertEquals(list.dimension, d.length);
		assertEquals(1.0,d[0]);
		assertEquals(2.0,d[1]);
		assertEquals(3.0,d[2]);

		System.out.println("OK");
		
	}
	
	public void testCanAdd() {
		System.out.print("Testing DoublesList.canAdd():... ");
		DoublesList list = new DoublesList();
		// TODO Permite añadir double[] dimension cero
		assertTrue(list.canAdd(new double[]{}));
		assertTrue(list.canAdd(new double[]{1.0,2.0,3.0}));
		list.add(new double[]{1.0,2.0,3.0});
		list.add(new double[]{1.0,2.0,3.0});
		assertTrue(list.canAdd(new double[]{1.0,2.0,3.0}));
		assertFalse(list.canAdd(new double[]{1.0,2.0,3.0,4.0}));
		assertFalse(list.canAdd(new double[]{1.0,2.0}));
		
		System.out.println("OK");		
	}
	
	
	
}
