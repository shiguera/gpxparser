package com.mlab.gpx.test.impl;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mlab.gpx.impl.Link;

import junit.framework.TestCase;


public class TestLink extends TestCase {

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	public void testLink() {
		System.out.print("Testing Link...");
		//logger.warn("TESTING Link.Link()");
		Link link1= new Link("Texto","Tipo de enlace","http://mercatorlab.com");
		assertNotNull(link1);
		Link link2= new Link("","","http://mercatorlab.com");		
		assertNotNull(link2);
//		Link link3= new Link("","","hqtp://mercatorlab.com");		
//		assertNotNull(link3);
		System.out.println("OK");

	}

	public void testAsGpx() {
		System.out.print("Testing Link.asGpx()...");
		Link link1= new Link("Texto","Tipo de enlace","http://mercatorlab.com");
		//logger.warn(link1.asGpx());		
		Link link2= new Link("","","http://mercatorlab.com");		
		//logger.info(link2.asGpx());
//		Link link3= new Link("","","hqtp://mercatorlab.com");		
//		logger.warn("\n"+link3.asGpx());
		System.out.println("OK");

	}

}
