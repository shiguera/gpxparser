package mlab.tesis.java.gpx.data;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mlab.tesis.java.gpx.data.Link;

import junit.framework.TestCase;


public class LinkTest extends TestCase {

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	public void testLink() {
		logger.warn("TESTING Link.Link()");
		Link link1= new Link("Texto","Tipo de enlace","http://mercatorlab.com");
		assertNotNull(link1);
		Link link2= new Link("","","http://mercatorlab.com");		
		assertNotNull(link2);
//		Link link3= new Link("","","hqtp://mercatorlab.com");		
//		assertNotNull(link3);
	}

	public void testAsGpx() {
		logger.warn("TESTING Link.asGpx()");
		Link link1= new Link("Texto","Tipo de enlace","http://mercatorlab.com");
		logger.warn(link1.asGpx());		
		Link link2= new Link("","","http://mercatorlab.com");		
		logger.info(link2.asGpx());
//		Link link3= new Link("","","hqtp://mercatorlab.com");		
//		logger.warn("\n"+link3.asGpx());

	}

}
