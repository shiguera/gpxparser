package com.mlab.gpx.test.impl.util;

import java.io.File;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;

import com.mlab.gpx.impl.util.Util;

public class TestUtil extends TestCase {

	public void testSecondsToHMSString() {
		System.out.print("Testing Util.secondsToHMSString()...");
		long t = (long)(2*60*60+33*60+17);
		assertEquals("2 h 33 m 17 s", Util.secondsToHMSString(t));
		t = (long)(2*60*60);
		assertEquals("2 h", Util.secondsToHMSString(t));
		t = (long)(2*60*60+17);
		assertEquals("2 h 17 s", Util.secondsToHMSString(t));
		t = (long)(0);
		assertEquals("0 s", Util.secondsToHMSString(t));
		System.out.println("OK");		
	}
	public void testSecondsToHMS() {
		System.out.print("Testing Util.secondsToHMS()...");
		// 16 segundos
		long t = 16l;
		assertEquals(0, (int)Util.secondsToHMS(t)[0]);
		assertEquals(0, (int)Util.secondsToHMS(t)[1]);
		assertEquals(16, (int)Util.secondsToHMS(t)[2]);
		// 42 minutos
		t = (long) (42*60.0);
		assertEquals(0, (int)Util.secondsToHMS(t)[0]);
		assertEquals(42, (int)Util.secondsToHMS(t)[1]);
		assertEquals(0, (int)Util.secondsToHMS(t)[2]);
		// 42 minutos y 18 segundos
		t = (long) (42.0*60.0+18);
		assertEquals(0, (int)Util.secondsToHMS(t)[0]);
		assertEquals(42, (int)Util.secondsToHMS(t)[1]);
		assertEquals(18, (int)Util.secondsToHMS(t)[2]);
		// 2 horas
		t = (long) (2.0*60.0*60.0);
		assertEquals(2, (int)Util.secondsToHMS(t)[0]);
		assertEquals(0, (int)Util.secondsToHMS(t)[1]);
		assertEquals(0, (int)Util.secondsToHMS(t)[2]);
		// 2 horas y 42 minutos
		t = (long) (2.7*60.0*60.0);
		assertEquals(2, (int)Util.secondsToHMS(t)[0]);
		assertEquals(42, (int)Util.secondsToHMS(t)[1]);
		assertEquals(0, (int)Util.secondsToHMS(t)[2]);
		// 2 horas y 18 segundos
		t = (long) (2.0*60.0*60.0+18);
		assertEquals(2, (int)Util.secondsToHMS(t)[0]);
		assertEquals(0, (int)Util.secondsToHMS(t)[1]);
		assertEquals(18, (int)Util.secondsToHMS(t)[2]);
		// 2 horas, 42 minutos y 18 segundos
		t = (long) (2.0*60.0*60.0+42.0*60+18);
		assertEquals(2, (int)Util.secondsToHMS(t)[0]);
		assertEquals(42, (int)Util.secondsToHMS(t)[1]);
		assertEquals(18, (int)Util.secondsToHMS(t)[2]);
		
		System.out.println("OK");		
	}

	public void testSecondsToHours() {
		System.out.print("Testing Util.secondsToHours()...");
		long t = (long) (2.7*60.0*60.0);
		assertEquals(2.7, Util.secondsToHours(t));
		t = (long) (2.3*60.0*60.0);
		assertEquals(2.3, Util.secondsToHours(t));
		t = (long) (-2.3*60.0*60.0);
		assertEquals(-2.3, Util.secondsToHours(t));
		t = (long) (0.3*60.0*60.0);
		assertEquals(0.3, Util.secondsToHours(t));
		System.out.println("OK");		
	}
	public void testSecondsToMinutes() {
		System.out.print("Testing Util.secondsToMinutes()...");
		long t = (long) (2.7*60.0);
		assertEquals(2.7, Util.secondsToMinutes(t));
		t = (long) (2.3*60.0);
		assertEquals(2.3, Util.secondsToMinutes(t));
		t = (long) (-2.3*60.0);
		assertEquals(-2.3, Util.secondsToMinutes(t));
		t = (long) (0.3*60.0);
		assertEquals(0.3, Util.secondsToMinutes(t));
		System.out.println("OK");		
	}
	
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

	@Test
	public void testDistLoxodromic() {
		System.out.print("TestUtil.testDistLoxodromic()... ");
		double lon1 = 0.0;
		double lat1 = 0.0;
		double lon2 = 1.0;
		double lat2 = 0.0;
		Assert.assertEquals(60.0*1852.0,  Util.distLoxodromic(lon1, lat1, lon2, lat2));
		
		lon2 = 0.0;
		lat2 = 1.0;
		Assert.assertEquals(60.0*1852.0,  Util.distLoxodromic(lon1, lat1, lon2, lat2));
		
		lon2 = 1.0;
		Assert.assertEquals(157144.4192, Util.distLoxodromic(lon1, lat1, lon2, lat2), 0.001);
		System.out.println("OK");
	}

	@Test
	public void testDist3D() {
		System.out.print("TestUtil.testDist3D()... ");
		double lon1 = 0.0;
		double lat1 = 0.0;
		double alt1 = 0.0;
		double lon2 = 1.0;
		double lat2 = 0.0;
		double alt2 = 1000.0;

		Assert.assertEquals( 111124.4995,  Util.dist3D(lon1, lat1, alt1, lon2, lat2, alt2), 0.001);
		//System.out.println("\ndist1 "+Util.dist3D(lon1, lat1, alt1, lon2, lat2, alt2));

		lon2 = 0.0;
		lat2 = 1.0;
		Assert.assertEquals( 111124.4995,  Util.dist3D(lon1, lat1, alt1, lon2, lat2, alt2), 0.001);
		//System.out.println("dist2 "+Util.dist3D(lon1, lat1, alt1, lon2, lat2, alt2));
		
		lon2 = 1.0;
		Assert.assertEquals( 157147.6009,  Util.dist3D(lon1, lat1, alt1, lon2, lat2, alt2), 0.001);
		//System.out.println("dist3 "+Util.dist3D(lon1, lat1, alt1, lon2, lat2, alt2));
		
		System.out.println("OK");
	}

}
