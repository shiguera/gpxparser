package com.mlab.tesis.java.gpx.data.test;

import java.io.File;

import junit.framework.TestCase;

import com.mlab.tesis.java.gpx.data.Util;

public class TestUtil extends TestCase {
	
	public void testReadResourceFile() {
		String filename = "textfile.txt";
		File file = Util.readResourceFile(filename);
		assertNotNull(file);
	}
	
	public void testReadFileToString() {
		String filename = "textfile.txt";
		File file = Util.readResourceFile(filename);
		String cad = Util.readFileToString(file);
		assertNotNull(cad);
		assertTrue(cad.length()>0);
		System.out.println(cad);
		assertEquals("Prueba de fichero de texto\nSegunda linea\nTercera\n", cad);
	}

}
