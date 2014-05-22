package com.mlab.gpx.impl.util;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import com.mlab.gpx.api.GpxFactory;

public class XmlFactory {
	private final static int XMLFORMAT_INDENT = 3;
	final static String DOUBLE_DEFAULTPRECISSION_FORMAT = "%12.6f";
	final static String DOUBLE_BIGPRECISSION_FORMAT = "%12.6f";
	final static String DOUBLE_SHORTPRECISSION_FORMAT = "%8.2f";
	/**
	 * Convierte un Node xml en una cadena de texto
	 * @param node org.w3c.dom.Node con el xml
	 * @param withDeclaration si true incluye la declaración de 
	 * documento xml
	 * @return String con la cadena xml del Node con o sin cabecera xml
	 * en función del valor del parámetro withDeclaration
	 */
	public static String nodeAsFormatedXmlString(Node node, boolean withDeclaration) {
		String xmlString="";
		try	{
			// Set up the output transformer
			TransformerFactory transfac = TransformerFactory.newInstance();
			Transformer trans = transfac.newTransformer();
			trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, (withDeclaration?"no":"yes"));
			trans.setOutputProperty(OutputKeys.INDENT, "yes");
			trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount",
					String.format("%d", XmlFactory.XMLFORMAT_INDENT));
	
			// Print the DOM node
			StringWriter sw = new StringWriter();
			StreamResult result = new StreamResult(sw);
			DOMSource source = new DOMSource(node);
			trans.transform(source, result);
			xmlString = sw.toString();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return xmlString;
	}
	/**
	 * Lee un String XML y lo devuelve en un objeto 
	 * org.w3c.dom.Document
	 * @param cadxml
	 * @return
	 */
	public static Document parseXmlDocument(String cadxml) {
		if(cadxml==null || cadxml.length()==0) {
			return null;
		}
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder=null;
		InputSource is = null;
		Document doc = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		    is = new InputSource();
			is.setCharacterStream(new StringReader(cadxml));
		    doc = dBuilder.parse(is);	
			doc.getDocumentElement().normalize();
		} catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
		
		return doc;
	}
	/**
	 * Formatea una cadena xml con cambios de linea y tabulaciones. 
	 * En la salida no muestra la declaración '<xml...>'
	 * @param cadxml cadena xml que se quiere formatear
	 * @return Cadena formateada
	 */
	public static String format(String cadxml) {
		return format(cadxml, false);
	}
	/**
	 * Formatea una cadena xml con cambios de linea y tabulaciones. 
	 * En la salida muestra o no la declaración '<xml...>' según 
	 * el valor de withDeclaration.
	 * 
	 * @param cadxml cadena xml que se quiere formatear
	 * @return Cadena formateada
	 */
	public static String format(String cadxml, boolean withDeclaration) {
		String xmlString="";
		Document node = parseXmlDocument(cadxml);
		xmlString = nodeAsFormatedXmlString(node,withDeclaration);
		return xmlString;
	}
	
	public static String createTextNodeString(String nodename, String content) {
			StringBuilder builder = new StringBuilder();
			builder.append("<");
			builder.append(nodename);	
			builder.append(">");
			builder.append(content);
			builder.append("</");
			builder.append(nodename);	
			builder.append(">");		
			return builder.toString();		
	}
	/**
	 * Build a String with an xml tag. The tag can be openning tag or closing tag
	 * according on isOpeningTag parameter
	 * @param namespace
	 * @param name
	 * @param isOpeningTag
	 * @return
	 */
	public static String createTag(String namespace, String name, boolean isOpeningTag) {
		StringBuilder builder = new StringBuilder();
		builder.append("<");
		if(!isOpeningTag) {
			builder.append("/");
		}
		if(namespace.length() != 0) {
			builder.append(namespace);
			builder.append(":");
		}
		builder.append(name);
		builder.append(">");
		return builder.toString();
	}
	public static String createCloseTag(String name) {
		StringBuilder builder = new StringBuilder();
		builder.append("</");
		builder.append(name);
		builder.append(">");
		return builder.toString();
	}
	public static String createOpenTag(String name) {
		StringBuilder builder = new StringBuilder();
		builder.append("<");
		builder.append(name);
		builder.append(">");
		return builder.toString();
	}
	public static String createDoubleTag(String namespace, String name, double value) {
		if(namespace=="" && name == "") {
			throw new IllegalArgumentException("ERROR empty String for tagname");
		}
		String tagname = "";
		if(namespace!="") {
			tagname = namespace+":";
		}
		tagname += name;
		StringBuilder builder = new StringBuilder();
		builder.append(createOpenTag(tagname));
		builder.append(String.format(DOUBLE_DEFAULTPRECISSION_FORMAT, value).replace(',','.').trim());
		builder.append(createCloseTag(tagname));
		return builder.toString();
	}
	public static String createDoubleTag(String namespace, String name, double value, int digits, int decimals) {
		if(namespace=="" && name == "") {
			throw new IllegalArgumentException("ERROR empty String for tagname");
		}
		String tagname = "";
		if(namespace!="") {
			tagname = namespace+":";
		}
		tagname += name;
		StringBuilder builder = new StringBuilder();
		builder.append(createOpenTag(tagname));
		builder.append(Util.doubleToString(value, digits, decimals));
		builder.append(createCloseTag(tagname));
		return builder.toString();
	}

}
