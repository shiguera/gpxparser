package mlab.tesis.java.gpx.data;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mlab.tesis.java.gpx.data.WayPoint;

import junit.framework.TestCase;

public class WayPointTest extends TestCase {

	Logger logger=LoggerFactory.getLogger(getClass());
	
	
	public void testConstructor() {
		logger.warn("TESTING WayPoint constructor");

		long t=System.currentTimeMillis();
		WayPoint tp= new WayPoint("Pto1","Punto de pruebas",t,-3.8,42.5,900.0,23.7,123.2,-1.0);
		assertNotNull(tp);
		//logger.info("\n"+tp.asGpx());		
		
		t=System.currentTimeMillis();
		WayPoint tp2= new WayPoint("Pto1","Punto de pruebas",t,-3.8,42.5,900.0,23.7,123.2,10.0);
		assertNotNull(tp2);
		//logger.info("\n"+tp2.asGpx());		

	}
	
	public void testParseGpx() {
		logger.warn("TESTING WayPoint.parseGpx()");
		
		// Crear una instancia de TrackPoint para generar una cadena gpx con la que probar el parse
		long t=System.currentTimeMillis();
		WayPoint tp= new WayPoint("Pto1","Punto de pruebas",t,-3.8,42.5,900.0,23.7,123.2,10.0);
		String cadgpx=tp.asGpx();
		
		// Generar un TrackPoint parseando una cadena gpx y comprobar que no es nulo
		WayPoint parsedPoint=WayPoint.parseGpx(cadgpx);
		assertNotNull(parsedPoint);
		//logger.info("\n"+parsedPoint.asGpx());		

		// Comprobar que el TrackPoint del constructor tp y el parsedPoint tienen la misma cadena gpx 
		assertEquals(tp.asGpx(), parsedPoint.asGpx());
		
	}
}
