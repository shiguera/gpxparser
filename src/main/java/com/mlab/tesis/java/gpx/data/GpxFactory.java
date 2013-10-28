package com.mlab.tesis.java.gpx.data;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.mlab.tesis.java.gpx.Util;
import com.mlab.tesis.java.gpx.data.extensions.ExtendedGpxFactory;

/**
 * Clase base abstracta para las implementaciones concretas de
 * clases factory para parsear documentos gpx.<br/>
 * Dispone de algunos métodos estáticos utilitarios 
 * con caracter general para operar con documentos xml:<br/>
 * - getFactory(): nos proporciona la instancia 
 * de factory concreta en base al parámetro 'GpxFactory.Type'.<br/>
 * - parseXmlDocument() : Devuelve un Document a partir de un String xml<br/>
 * - parseGpxDocument() : Devuelve un GpxDocument a partirde un String xml <br/>
 * - nodeAsFormatedXmlString() : Devuelve un String formateado en xml a partir
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
	final static String DOUBLE_DEFAULTPRECISSION_FORMAT = "%12.6f";
	final static String DOUBLE_BIGPRECISSION_FORMAT = "%12.6f";
	final static String DOUBLE_SHORTPRECISSION_FORMAT = "%8.2f";
	
	
	/**
	 * Variable de tipo de Factory a instanciar a través 
	 * del método estático 'getFactory()' 	
	 */
	public enum Type {SimpleGpxFactory, ExtendedGpxFactory};
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
			case ExtendedGpxFactory:
				return new ExtendedGpxFactory();
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
	 * Parsea un documento de texto gpx a GpxDocument
	 * @param cadgpx
	 * @return GpxDocument 
	 */
	public GpxDocument parseGpxDocument(String cadgpx) {
		Document doc= parseXmlDocument(cadgpx);
		//System.out.println(doc.getTextContent());
		if(!isValidGpxDocument(doc)) {
			return null;
		}
		//Element gpx=doc.getDocumentElement();
		
		// Abstract method
		GpxDocument gpxDocument = createGpxDocument();
		
		// FIXME Procesar metadata
		
		// Procesar nodos WayPoint
		NodeList nl=doc.getElementsByTagName("wpt");
		//System.out.println("----------------\n"+nl.getLength()+"---------------\n");
		for(int i=0; i< nl.getLength(); i++) {
			WayPoint wp= parseWayPoint(GpxFactory.nodeAsFormatedXmlString(nl.item(i),false));
			if(wp!=null) {
				//System.out.println("GpxFactory.parseGpxDocument(): adding way point to gpxdoc...");	
				gpxDocument.addWayPoint(wp);
			}
		}
		// Procesar nodos RTE
		nl=doc.getElementsByTagName("rte");
		for(int i=0; i< nl.getLength(); i++) {
			Route rte= parseRoute(GpxFactory.nodeAsFormatedXmlString(nl.item(i),false));
			if(rte!=null) {
				gpxDocument.addRoute(rte);
			}
		}
		// Procesar nodos TRK
		nl=doc.getElementsByTagName("trk");
		for(int i=0; i< nl.getLength(); i++) {
			Track trk= parseTrack(GpxFactory.nodeAsFormatedXmlString(nl.item(i),false));
			if(trk!=null) {
				gpxDocument.addTrack(trk);
			}
		}
		
		// TODO Procesar nodos Extensions

		return gpxDocument;
	}
	private boolean isValidGpxDocument(Document doc) {
		if(doc == null || doc.getDocumentElement().getNodeName().equalsIgnoreCase("gpx")==false) {
			return false;
		}
		return true;
	}
	
	/**
	 * Read a GpxDocument from a file
	 * @param gpxFile
	 * @return GpxDocument or null
	 */
	public static GpxDocument readGpxDocument(File gpxFile) {
		String cad = Util.readFileToString(gpxFile);
		GpxDocument gpxDoc = (SimpleGpxDocument) GpxFactory.getFactory(GpxFactory.Type.SimpleGpxFactory).parseGpxDocument(cad);
		if(gpxDoc==null) {
			System.out.println("Error parsing GpxDocument "+gpxFile.getName());
		}
		return gpxDoc;
	}
	
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

	/**
	 * Formatea una cadena xml con cambios de linea y tabulaciones. 
	 * Añade la cabecera de documento
	 * @param cadxml cadena xml que se quiere formatear
	 * @return Cadena formateada
	 */
	public static String format(String cadxml) {
		String xmlString="";
		Document node = parseXmlDocument(cadxml);
		xmlString = nodeAsFormatedXmlString(node,false);
		return xmlString;
	}
	
		
	/**
	 * Crea una instancia de WayPoint a partir de la cadena
	 * gpx del mismo 
	 * @param cadgpx String <wpt lon=....></wpt>
	 * @return WayPoint o null si hay errores
	 * @throws ParserConfigurationException 
	 */
	private WayPoint parseWayPoint(String cadgpx) {
		Document doc = parseXmlDocument(cadgpx);
		if(doc==null) {
			System.out.println("GpxFactory.prseWayPoint(): ERROR doc=null");
			return null;
		}
		if(!isValidWayPointDocument(doc)) {
			System.out.println("GpxFactory.prseWayPoint(): ERROR not valid doc");
			return null;
		}
		Element wptElement=doc.getDocumentElement();

		String namee=parseStringTag(doc,"name");
		String descrip=parseStringTag(doc,"desc");
		long t=parseTimeTag(doc,"time");

		List<Double> values = new ArrayList<Double>();

		double lon, lat;
		try {
			lon=Double.parseDouble(wptElement.getAttribute("lon"));
			lat=Double.parseDouble(wptElement.getAttribute("lat"));			
		} catch (Exception e) {
            e.printStackTrace();
			return null;
		}
		
		values.add(new Double(lon));
		values.add(new Double(lat));
		
		double ele = parseDoubleTag(doc,"ele");
		values.add(new Double(ele));
				
		// abstract method: extensiones que añade la implementación
		// Solo admite doubles
		List<Double> extValues = parseWayPointExtensions(doc);
		values.addAll(extValues);
		
		// Abstract method
		WayPoint pt=createWayPoint(namee,descrip,t,values);
		return pt;
	}

	/**
	 * Build a Route object from a gpx String
	 * @param cadgpx
	 * @return
	 */
	private Route parseRoute(String cadgpx) {
		Route rte= new Route();
		Document doc = parseXmlDocument(cadgpx);
		if(doc==null) {
			return null;
		}
		if(!isValidRouteDocument(doc)) {
			return null;
		}
		NodeList nl=doc.getElementsByTagName("rtept");
		if(nl.getLength()>0) {
			for (int i=0; i<nl.getLength(); i++) {
				try {
					WayPoint wp= parseWayPoint(GpxFactory.nodeAsFormatedXmlString(nl.item(i),false));
					if(wp!=null) {
						rte.addWayPoint(wp);
					}
				} catch (Exception e) {
		            e.printStackTrace();
					return null;
				}
			}
		}
		return rte;
	}
	
	/**
	 * Build a TrackSegment from a gpx String
	 * @param cadgpx
	 * @return
	 */
	private TrackSegment parseTrackSegment(String cadgpx) {
		TrackSegment ts= new TrackSegment();
		Document doc = parseXmlDocument(cadgpx);
		if(doc==null) {
			return null;
		}
		if(!this.isValidTrackSegmentDocument(doc)) {
			return null;
		}
		NodeList nl=doc.getElementsByTagName("trkpt");
		//System.out.println(nl.getLength());
		if(nl.getLength()>0) {
			for (int i=0; i<nl.getLength(); i++) {
				try {
					WayPoint wp= parseWayPoint(GpxFactory.nodeAsFormatedXmlString(nl.item(i),false));
					if(wp!=null) {
						ts.addWayPoint(wp);
					}
				} catch (Exception e) {
		            e.printStackTrace();
					return null;
				}
			}
		}
		return ts;	
	}

	/**
	 * Build a Track from a gpx String
	 * @param cadgpx
	 * @return
	 */
	private Track parseTrack(String cadgpx) {
		Track t= new Track();
		Document doc = parseXmlDocument(cadgpx);
		if(doc==null) {
			return null;
		}
		if(!isValidTrackDocument(doc)) {
			return null;
		}
		// FIXME parse name
		
		// FIXME parse number
		
		// parse track segments
		NodeList nl=doc.getElementsByTagName("trkseg");
		if(nl.getLength()>0) {
			for (int i=0; i<nl.getLength(); i++) {
				try {
					TrackSegment ts = this.parseTrackSegment(GpxFactory.nodeAsFormatedXmlString(nl.item(i),false));
					if(ts!=null) {
						t.addTrackSegment(ts);
					}
				} catch (Exception e) {
		            e.printStackTrace();
					return null;
				}
			}
		}
		return t;
	}
	
	// parse tags
	/**
	 * Lee el contenido double de un tag determinado del Document xml
	 * @param doc XML Document
	 * @param tagname String Nobre de la etiqueta de la que extraer el valor double
	 * @return double contenido de la etiqueta o -1.0 si hay errores
	 */
	protected double parseDoubleTag(Document doc, String tagname) {
		double result=-1.0;
		NodeList nl=doc.getElementsByTagName(tagname);
		if(nl.getLength()>0) {
			try {
				result=Double.parseDouble(nl.item(0).getTextContent());
			} catch (Exception e) {
	            e.printStackTrace();
			}
		}
		return result;		
	}
	protected long parseTimeTag(Document doc, String tagname) {
		long t=-1L;
		NodeList nl=doc.getElementsByTagName(tagname);
		if(nl.getLength()>0) {
			String cad=nl.item(0).getTextContent();
			t = Util.parseGpxDate(cad);
		}		
		return t;
	}
	protected String parseStringTag(Document doc, String tagname) {
		String cad = "";
		NodeList nl=doc.getElementsByTagName(tagname);
		if(nl.getLength()>0) {
			cad=nl.item(0).getTextContent();
		}		
		return cad;
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
		builder.append(GpxFactory.createCloseTag(tagname));
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
		builder.append(GpxFactory.createCloseTag(tagname));
		return builder.toString();
	}
	static String createOpenTag(String name) {
		StringBuilder builder = new StringBuilder();
		builder.append("<");
		builder.append(name);
		builder.append(">");
		return builder.toString();
	}
	static String createCloseTag(String name) {
		StringBuilder builder = new StringBuilder();
		builder.append("</");
		builder.append(name);
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
	static String createTag(String namespace, String name, boolean isOpeningTag) {
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
	
	// validate documents
	protected boolean isValidWayPointDocument(Document doc) {
		if(doc.getDocumentElement().getNodeName().equalsIgnoreCase("wpt")==false &&
				doc.getDocumentElement().getNodeName().equalsIgnoreCase("rtept")==false &&
				doc.getDocumentElement().getNodeName().equalsIgnoreCase("trkpt")==false) {
			return false;
		}		
		return true;
	}
	protected boolean isValidTrackSegmentDocument(Document doc) {
		if(!doc.getDocumentElement().getNodeName().equalsIgnoreCase("trkseg")) {
			return false;
		}		
		return true;
	}
	protected boolean isValidRouteDocument(Document doc) {
		if(!doc.getDocumentElement().getNodeName().equalsIgnoreCase("rte")) {
			return false;
		}
		return true;
	}
	protected boolean isValidTrackDocument(Document doc) {
		if(!doc.getDocumentElement().getNodeName().equalsIgnoreCase("trk")) {
			return false;
		}
		return true;
	}
	
	// Abstract methods
	public GpxDocument createGpxDocument() {
		return new SimpleGpxDocument();
	}
	
	/**
	 *  Abstract method para creación de un WayPoint.
	 *  Cada implementación tendrá un tamaño del List\<Double\> diferente
	 * @param name String name of WayPoint
	 * @param description String description of WayPoint
	 * @param time long time for the WayPoint
	 * @param values List<Double> con los valores necesarios para el constructor de la clase WayPoint concreta.
	 * @return WayPoint de la clase concreta o null
	 */
	public abstract WayPoint createWayPoint(String name,
			String description, long time, List<Double> values);

	/** Abstract method utilizado por el método 'parseWayPoint()'
	 * Devuelve un List\<Double\> con los valores de las extensiones
	 * que añade la implementación concreta de WayPoint
	 * @param doc Recibe el DOM Document completo. La clase derivada deberá
	 * extraer sus extensiones y devolverlas como List<Double>
	 * @return
	 */
	public abstract List<Double> parseWayPointExtensions(Document doc);
	
	public String asCsv(WayPoint wp) {
		return wp.asCsv(false);
	}
	
}
