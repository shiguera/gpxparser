package com.mlab.gpx.test.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import junit.framework.TestCase;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.mlab.gpx.api.GpxDocument;
import com.mlab.gpx.api.GpxFactory;
import com.mlab.gpx.api.WayPoint;
import com.mlab.gpx.api.GpxFactory.Type;
import com.mlab.gpx.impl.Track;
import com.mlab.gpx.impl.TrackSegment;

public class TestGpxFactory extends TestCase {
	private final String cadxml = "<?xml version=\"1.0\"  encoding=\"UTF-8\"?>"+
			"<company><staff id=\"1001\"><firstname>yong</firstname><lastname>mook kim</lastname>"+
			"<nickname>mkyong</nickname><salary>100000</salary></staff><staff id=\"2001\">"+
			"<firstname>low</firstname><lastname>yin fong</lastname><nickname>fong fong</nickname>"+
			"<salary>200000</salary></staff></company>";

	private final String wpt = "<wpt lat=\"46.57638889\" lon=\"8.89263889\">"+
									"<ele>2372</ele>"+
									"<name>LAGORETICO</name>"+
								"</wpt>";
	private final String trk = "<trk><name>Example gpx</name><number>1</number><trkseg>"+
		"<trkpt lat=\"46.57608333\" lon=\"8.89241667\"><ele>2376</ele><time>2007-10-14T10:09:57Z</time></trkpt>"+
		"<trkpt lat=\"46.57619444\" lon=\"8.89252778\"><ele>2375</ele><time>2007-10-14T10:10:52Z</time></trkpt>"+
		"<trkpt lat=\"46.57641667\" lon=\"8.89266667\"><ele>2372</ele><time>2007-10-14T10:12:39Z</time></trkpt>"+
		"<trkpt lat=\"46.57650000\" lon=\"8.89280556\"><ele>2373</ele><time>2007-10-14T10:13:12Z</time></trkpt>"+
		"</trkseg></trk>"; 
	private final String docgpx = 	
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
				"<gpx version=\"1.0\">"+
					"<name>Example gpx</name>"+
					"<wpt lat=\"46.57638889\" lon=\"8.89263889\">"+
						"<ele>2372</ele>"+
						"<name>LAGORETICO</name>"+
					"</wpt>"+
					"<trk><name>Example gpx</name><number>1</number><trkseg>"+
						"<trkpt lat=\"46.57638889\" lon=\"8.89302778\"><ele>2374</ele><time>2007-10-14T10:13:20Z</time></trkpt>"+
						"<trkpt lat=\"46.57652778\" lon=\"8.89322222\"><ele>2375</ele><time>2007-10-14T10:13:48Z</time></trkpt>"+
						"<trkpt lat=\"46.57661111\" lon=\"8.89344444\"><ele>2376</ele><time>2007-10-14T10:14:08Z</time></trkpt>"+
					"</trkseg></trk>"+
				"</gpx>";
	GpxFactory factory ;
	
	@Override
	protected void setUp() throws Exception {
		factory = GpxFactory.getFactory(Type.SimpleGpxFactory);
		super.setUp();
	}
	public void test() {
		System.out.print("Testing GpxFactory()...");
		assertNotNull(factory);
		System.out.println("OK");
	}
	public void testParseXmlDocument() {
		System.out.print("Testing GpxFactory.parseXmlDocument()...");
		Document doc = GpxFactory.parseXmlDocument(cadxml);
		assertNotNull(doc);
		Element ele = doc.getDocumentElement();
		assertNotNull(ele);
		assertEquals("company", ele.getNodeName());
		System.out.println("OK");
	}
	public void testParseGpxDocument() {
		System.out.print("Testing GpxFactory.parseGpxDocument()...");
		GpxDocument doc = factory.parseGpxDocument(docgpx);
		assertNotNull(doc);
		// WayPoint
		assertEquals(1,doc.getWayPoints().size());
		// Tracks
		assertEquals(1,doc.getTracks().size());
		// Segments
		assertEquals(1,doc.getTracks().get(0).size());
		// trkpt
		assertEquals(3,doc.getTracks().get(0).getTrackSegment(0).size());

		System.out.println("OK");
	}
	public void testNodeAsFormatedXmlString() {
		System.out.print("Testing GpxFactory.nodeAsFormatedXmlString()...");
		Document doc = GpxFactory.parseXmlDocument(cadxml);
		Element ele = doc.getDocumentElement();
		NodeList list = ele.getElementsByTagName("salary");
		String cad = GpxFactory.nodeAsFormatedXmlString(list.item(0),false);
		//System.out.println(cad);
		assertEquals("<salary>100000</salary>\n", cad);
		System.out.println("OK");
	}
	public void testFormat() {
		System.out.print("Testing GpxFactory.format()...");
		String cad = GpxFactory.format(cadxml);
		//System.out.println(cad);
		assertTrue(cad.length()>0);
		System.out.println("OK");
		
	}
	public void testParseWayPoint() {
		System.out.print("Testing GpxFactory.parseWayPoint()...");
		Method method = null;
		try {
			method = GpxFactory.class.getDeclaredMethod("parseWayPoint", String.class);
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		method.setAccessible(true);
		WayPoint wp = null;
		try {
			wp = (WayPoint) method.invoke(factory, wpt);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(wp);
		assertEquals(46.57638889, wp.getLatitude());
		assertEquals(8.89263889, wp.getLongitude());
		assertEquals(2372.0, wp.getAltitude());
		assertEquals("LAGORETICO", wp.getName());		

		System.out.println("OK");
		
	}
	public void testParseTrack() {
		System.out.print("Testing GpxFactory.parseTrack()...");
		Method method = null;
		try {
			method = GpxFactory.class.getDeclaredMethod("parseTrack", String.class);
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		method.setAccessible(true);
		Track track = null;
		try {
			track = (Track) method.invoke(factory, trk);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(track);
		// numero de segmentos
		assertEquals(1,track.size());
		// numero de puntos en el segmento
		assertEquals(4,((TrackSegment)track.nodes().get(0)).size());
		System.out.println("OK");
		
	}
	public void testCreateDoubleTag() {
		System.out.print("Testing GpxFactory.createDoubleTag()....");
		String namespace = "";
		String name ="";
		String tag = null;
		// Metodo createDoubleTag(): Exception tag vacía
		Throwable t=null;
		try {
			tag = GpxFactory.createDoubleTag(namespace, name, 0.0);
		} catch(Exception e) {
			t = e;
		}
		assertNull(tag);
		assertEquals(IllegalArgumentException.class, t.getClass());

		// Etiqueta sin namespace, precisión por defecto
		name = "Ax";
		assertEquals("<Ax>0.000000</Ax>",GpxFactory.createDoubleTag(namespace,name,0.0));
		// Etiqueta completa
		namespace = "mlab";
		assertEquals("<mlab:Ax>0.000000</mlab:Ax>",GpxFactory.createDoubleTag(namespace,name,0.0));
		
		// Método doubleToString(..., digits, decimals)
		assertEquals("<mlab:Ax>0.000000</mlab:Ax>",GpxFactory.createDoubleTag(namespace,name,0.0,12,6));
		assertEquals("<mlab:Ax>0.000</mlab:Ax>",GpxFactory.createDoubleTag(namespace,name,0.0,12,3));
		assertEquals("<mlab:Ax>1.236</mlab:Ax>",GpxFactory.createDoubleTag(namespace,name,1.2356,12,3));
			
		System.out.println("OK");
		
	}

}
