package com.mlab.gpx.test.impl.tserie;

import com.mlab.gpx.impl.tserie.TList;

import junit.framework.TestCase;

public class TestTList extends TestCase {
	
	public void test() {
		System.out.print("Testing TList()...");
		TList list = new TList();
		assertNotNull(list);
		System.out.println("OK");		
	}
	
	public void testAdd() {
		TList list = new TList();
		System.out.print("Testing TList.add(): negative time... ");
		assertFalse(list.add(-1l));
		assertEquals(0,list.getTlist().size());
		System.out.println("OK");
		
		System.out.print("Testing TList.add(): first value... ");
		assertTrue(list.add(1000l));
		assertEquals(1,list.getTlist().size());
		System.out.println("OK");
		
		System.out.print("Testing TList.add(): second value... ");
		assertTrue(list.add(2000l));
		assertEquals(2,list.getTlist().size());
		System.out.println("OK");
		
		System.out.print("Testing TList.add(): value minor than last... ");
		assertFalse(list.add(2000l));
		assertEquals(2,list.getTlist().size());
		System.out.println("OK");
		
		
	}

	public void testIsEmpty() {
		TList list = new TList();
		System.out.print("Testing TList.isEmpty():... ");
		assertEquals(0,list.getTlist().size());
		System.out.println("OK");
	}
	public void testContains() {
		TList list = new TList();
		list.add(new Long(1000l));
		list.add(new Long(2000l));
		System.out.print("Testing TList.contains():... ");
		assertEquals(false,list.contains(500l));
		assertEquals(true,list.contains(2000l));
		
		System.out.println("OK");
	}
	public void testSize() {
		System.out.print("Testing TList.size():... ");
		TList list = new TList();
		assertEquals(0,list.size());
		list.add(new Long(1000l));
		list.add(new Long(2000l));
		assertEquals(2,list.size());
		
		System.out.println("OK");
	}

	public void testFirstTime() {
		System.out.print("Testing TList.firtTime():... ");
		TList list = new TList();
		assertEquals(-1l, list.firstTime());
		list.add(new Long(1000l));
		list.add(new Long(2000l));
		assertEquals(1000l, list.firstTime());
		
		System.out.println("OK");		
		
	}
	public void testLastTime() {
		System.out.print("Testing TList.lastTime():... ");
		TList list = new TList();
		assertEquals(-1l, list.lastTime());
		list.add(new Long(1000l));
		list.add(new Long(2000l));
		assertEquals(2000l, list.lastTime());
		
		System.out.println("OK");		
		
	}
	public void testIndexOfFloor() {
		System.out.print("Testing TList.indexOfFloor():... ");
		TList list = new TList();
		list.add(new Long(1000l));
		list.add(new Long(2000l));
		list.add(new Long(3000l));
		list.add(new Long(4000l));
		
		// minor than first
		assertEquals(-1,list.indexOfFloor(500));
		// greater than last
		assertEquals(-1,list.indexOfFloor(5000));
		// first
		assertEquals(0,list.indexOfFloor(1000));
		// last
		assertEquals(3,list.indexOfFloor(4000));
		// between
		assertEquals(1,list.indexOfFloor(2500));
				
		System.out.println("OK");		
		
	}
	public void testIndexOfCeiling() {
		System.out.print("Testing TList.indexOfFloor():... ");
		TList list = new TList();
		list.add(new Long(1000l));
		list.add(new Long(2000l));
		list.add(new Long(3000l));
		list.add(new Long(4000l));
		
		// minor than first
		assertEquals(-1,list.indexOfCeiling(500));
		// greater than last
		assertEquals(-1,list.indexOfCeiling(5000));
		// first
		assertEquals(0,list.indexOfCeiling(1000));
		// last
		assertEquals(3,list.indexOfCeiling(4000));
		// between
		assertEquals(2,list.indexOfCeiling(2500));
				
		System.out.println("OK");		
		
	}

	public void testCanAdd() {
		System.out.print("Testing TList.canAdd():... ");
		TList list = new TList();
		assertTrue(list.canAdd(1000l));
		assertFalse(list.canAdd(-1000l));
		list.add(1000l);
		assertFalse(list.canAdd(500l));
		assertTrue(list.canAdd(1300l));
		
		System.out.println("OK");				
	}
	public void testIsInRange() {
		System.out.print("Testing TList.isInRange():... ");
		TList list = new TList();
		list.add(1000l);
		list.add(1300l);
		assertTrue(list.isInRange(1200l));
		assertFalse(list.isInRange(-1l));
		assertFalse(list.isInRange(2000l));
		
		System.out.println("OK");				
	}

}
