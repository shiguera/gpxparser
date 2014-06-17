package com.mlab.gpx.test.kml;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.mlab.gpx.impl.kml.Gpx2KmlFactory;

import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Point;

public class TestCreatePlacemark {

	@Test
	public void test() {
		System.out.println("TestCreatePlacemark.test()");
		Placemark placemark = Gpx2KmlFactory.createPlacemark("Prueba", 
				"description", -3.9, 40.9,900);
		Assert.assertNotNull(placemark);
		Assert.assertEquals("Prueba", placemark.getName());
		Assert.assertEquals("description", placemark.getDescription());
		
		Point p = (Point) placemark.getGeometry();
		List<Coordinate> coords = p.getCoordinates();
		Assert.assertEquals(-3.9, coords.get(0).getLongitude());
		Assert.assertEquals(40.9, coords.get(0).getLatitude());
		Assert.assertEquals(900.0, coords.get(0).getAltitude());
		
		System.out.println("OK");
	}

}
