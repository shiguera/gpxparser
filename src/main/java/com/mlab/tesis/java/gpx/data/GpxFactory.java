package com.mlab.tesis.java.gpx.data;

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
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Clase base abstracta para las implementaciones concretas de
 * clases factory para parsear documentos gpx.<br/>
 * Dispone de algunos métodos estáticos utilitarios 
 * con caracter general para operar con documentos xml:<br/>
 * - getFactory(): nos proporciona la instancia 
 * de factory concreta en base al parámetro 'GpxFactory.Type'.<br/>
 * - parseXmlDocument() : Devuelve un Document a partir de un String xml<br/>
 * - nodeAsString() : Devuelve un String formateado en xml a partir
 * de un org.w3c.dom.Node<br/>
 *  - format(): Devuelve una String xml formateada con saltos de línea y 
 *  tabulaciones a partir de un String xml sin formatear<br/>
 *  El resto de métodos para parsear documentos son abstractos 
 *  y los deben implementar las subclases.<br/>
 * 
 * @author shiguera
 *
 */
public abstract class GpxFactory {
	
	final static int XMLFORMAT_INDENT = 3;
	
	/**
	 * Variable de tipo de Factory a instanciar a través 
	 * del método estático 'getFactory()' 	
	 */
	public enum Type {SimpleGpxFactory};
	 
	protected GpxFactory.Type factoryType; 
	
	/**
	 * Permite instanciar una factory concreta, adaptada al
	 * tipo concreto de docs gpx 
	 * @param factoryType
	 * @return
	 */
	public static GpxFactory getFactory(GpxFactory.Type factoryType) {
		switch(factoryType) {
		case SimpleGpxFactory:
			return new SimpleGpxFactory();
		}
		return null;
	}

	/**
	 * Lee un String XML y lo devuelve en un objeto 
	 * org.w3c.dom.Document
	 * @param cadxml
	 * @return
	 */
	public static Document parseXmlDocument(String cadxml) {
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
	 * Convierte un Node xml en una cadena de texto
	 * @param node org.w3c.dom.Node con el xml
	 * @param withDeclaration si true incluye la declaración de 
	 * documento xml
	 * @return String con la cadena xml del Node con o sin cabecera xml
	 * en función del valor del parámetro withDeclaration
	 */
	public static String nodeAsString(Node node, boolean withDeclaration) {
		String xmlString="";
		try	{
			// Set up the output transformer
			TransformerFactory transfac = TransformerFactory.newInstance();
			Transformer trans = transfac.newTransformer();
			trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, Boolean.toString(withDeclaration));
			trans.setOutputProperty(OutputKeys.INDENT, "yes");
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
	 * Formatea una cadena xml con cambios de linea y tabulaciones. 
	 * Añade la cabecera de documento
	 * @param cadxml cadena xml que se quiere formatear
	 * @return Cadena formateada
	 */
	public static String format(String cadxml) {
		String xmlString="";
		Document node = parseXmlDocument(cadxml);
		try	{
			// Set up the output transformer
			TransformerFactory transfac = TransformerFactory.newInstance();
			Transformer trans = transfac.newTransformer();
			trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			trans.setOutputProperty(OutputKeys.INDENT, "yes");
			trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount",
					String.format("%d", XMLFORMAT_INDENT));
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
	
	public abstract SimpleGpxDocument parseGpxDocument(String cadgpx);
	public abstract WayPoint createWayPoint(long time, double[] values);
	public abstract WayPoint parseWpt(String xmlcad);
	public abstract TrackSegment parseSegment(String cadgpx);
	public abstract Route parseRoute(String xmlcad);
	public abstract Track parseTrack(String cadgpx);

}
