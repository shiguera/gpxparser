package com.mlab.tesis.java.gpx.data.test;

import java.io.File;
import java.lang.reflect.Method;

import junit.framework.TestCase;

import com.mlab.tesis.java.gpx.data.Util;

public class TestUtil extends TestCase {
	
	public void testStartTimeFromFileName() {
		System.out.print("Testing Util.startTimeFromFileName()...");
		long t1 = Util.startTimeFromFilename(new File("prueba.mp4"));
		assertEquals(-1l,t1);
		long t2 = Util.startTimeFromFilename(new File("20130405_220300.mp4"));
		assertFalse(-1l == t2);

		System.out.println("OK");		
	}
	
	public void testReadResourceFile() {
		System.out.print("Testing Util.readResourceFile()...");
		String filename = "textfile.txt";
		File file = Util.readResourceFile(filename);
		assertNotNull(file);
		System.out.println("OK");

	}
	
	public void testReadFileToString() {
		System.out.print("Testing Util.readFileToString()...");
		String filename = "textfile.txt";
		File file = Util.readResourceFile(filename);
		String cad = Util.readFileToString(file);
		assertNotNull(cad);
		assertTrue(cad.length()>0);
		//System.out.println(cad);
		assertEquals("Prueba de fichero de texto\nSegunda linea\nTercera\n", cad);
		System.out.println("OK");
	}
	public void testDoubleToString() {
		System.out.print("Testing Util.doubleToString()...");

		double d = 1.567;	
		// Prueba de redondeo a dos decimales
		assertEquals("1.57",Util.doubleToString(d, 10, 2));
		// Prueba redondeo a cero decimales
		assertEquals("2", Util.doubleToString(d, 10, 0));	
		// Prueba redondeo a más decimales de los del número
		assertEquals("1.567000", Util.doubleToString(d, 10, 6));	
		
		String cad = "";
		// Prueba NaN
		d = Double.NaN;
		Throwable t=null;
		try {
			cad = Util.doubleToString(d, 10, 2);	
		} catch (Exception e) {
			t = e;
		}
		//System.out.print(cad);
		assertNotNull(t);
		assertEquals(t.getClass(), IllegalArgumentException.class);

		// Prueba Negative infinity
		d = Double.NEGATIVE_INFINITY;
		t=null;
		try {
			cad = Util.doubleToString(d, 10, 2);	
		} catch (Exception e) {
			t = e;
		}
		assertNotNull(t);
		assertEquals(t.getClass(), IllegalArgumentException.class);

		// Prueba Positive infinity
		d = Double.POSITIVE_INFINITY;
		t=null;
		try {
			cad = Util.doubleToString(d, 10, 2);	
		} catch (Exception e) {
			t = e;
		}
		assertNotNull(t);
		assertEquals(t.getClass(), IllegalArgumentException.class);

		// prueba dígitos cero o negativos
		d = 1.5678;
		t=null;
		try {
			cad = Util.doubleToString(d, 0, 2);	
		} catch (Exception e) {
			t = e;
		}
		assertNotNull(t);
		assertEquals(t.getClass(), IllegalArgumentException.class);
		t=null;
		try {
			cad = Util.doubleToString(d, -2, 2);	
		} catch (Exception e) {
			t = e;
		}
		assertNotNull(t);
		assertEquals(t.getClass(), IllegalArgumentException.class);
		// Prueba decimales negativos
		t=null;
		try {
			cad = Util.doubleToString(d, 10, -1);	
		} catch (Exception e) {
			t = e;
		}
		assertNotNull(t);
		assertEquals(t.getClass(), IllegalArgumentException.class);
		

		
		System.out.println("OK");		
	}

	public void testGetFileExtension() {
		System.out.print("Testing Util.getFileExtension()...");
		assertEquals("mp4", Util.getFileExtension(new File("patata.mp4")));
		assertEquals("mp4", Util.getFileExtension(new File("patata.3.mp4")));
		assertEquals("", Util.getFileExtension(new File("patatamp4")));
		
		System.out.println("OK");		
	}
}
