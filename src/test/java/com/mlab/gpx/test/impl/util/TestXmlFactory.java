package com.mlab.gpx.test.impl.util;

import static org.junit.Assert.*;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.mlab.gpx.impl.util.XmlFactory;

public class TestXmlFactory {
	private final String cadxml = "<?xml version=\"1.0\"  encoding=\"UTF-8\"?>"+
			"<company><staff id=\"1001\"><firstname>yong</firstname><lastname>mook kim</lastname>"+
			"<nickname>mkyong</nickname><salary>100000</salary></staff><staff id=\"2001\">"+
			"<firstname>low</firstname><lastname>yin fong</lastname><nickname>fong fong</nickname>"+
			"<salary>200000</salary></staff></company>";

	@Test
	public void test() {
	
	}
	public void testParseXmlDocument() {
		System.out.print("Testing XmlFactory.parseXmlDocument()...");
		Document doc = XmlFactory.parseXmlDocument(cadxml);
		assertNotNull(doc);
		Element ele = doc.getDocumentElement();
		assertNotNull(ele);
		assertEquals("company", ele.getNodeName());
		System.out.println("OK");
	}
	public void testNodeAsFormatedXmlString() {
		System.out.print("Testing XmlFactory.nodeAsFormatedXmlString()...");
		Document doc = XmlFactory.parseXmlDocument(cadxml);
		Element ele = doc.getDocumentElement();
		NodeList list = ele.getElementsByTagName("salary");
		String cad = XmlFactory.nodeAsFormatedXmlString(list.item(0),false);
		//System.out.println(cad);
		assertEquals("<salary>100000</salary>\n", cad);
		System.out.println("OK");
	}
	public void testFormat() {
		System.out.print("Testing XmlFactory.format()...");
		String cad = XmlFactory.format(cadxml);
		//System.out.println(cad);
		assertTrue(cad.length()>0);
		System.out.println("OK");
		
	}
	
}
