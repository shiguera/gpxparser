package com.mlab.gpx.test.impl.srs;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

import com.mlab.gpx.impl.srs.EllipsoidHayford;

public class TestEllipsoid {

	@Test
	public void test() {
		System.out.print("Testing Ellipsoid()...");
		double longitude = -3.801873306;
    	double latitude = 43.4884075;
    	EllipsoidHayford ell = new EllipsoidHayford();
    	double[] xy = ell.proyUTM(longitude, latitude);
    	Assert.assertEquals(Double.parseDouble("435157.5871524121"), xy[0]);
    	Assert.assertEquals(Double.parseDouble("4815453.626807942"), xy[1]);
    	 
    	//System.out.println(xy[0]+" "+xy[1]);
        
		System.out.println("OK");
	}

}
