package com.mlab.tesis.java.gpx.data;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

/**
 * Clase utilitaria para manipulación de documentos Gpx
 * Básicamente se compone de un elemento Metadata y unas colecciones
 * de WayPoint's, Route's y Track's
 * El esquema xsd es:<p>
 * <pre>
 * {@code
 * <xsd:complexType name="gpxType">
 *    <xsd:sequence>
 *        <xsd:element name="metadata" type="metadataType" minOccurs="0"/>
 *        <xsd:element name="wpt" type="wptType" minOccurs="0" maxOccurs="unbounded"/>
 *        <xsd:element name="rte" type="rteType" minOccurs="0" maxOccurs="unbounded"/>
 *        <xsd:element name="trk" type="trkType" minOccurs="0" maxOccurs="unbounded"/>
 *        <xsd:element name="extensions" type="extensionsType" minOccurs="0"/>
 *    </xsd:sequence>
 *    <xsd:attribute name="version" type="xsd:string" use="required" fixed="1.1"/>
 *    <xsd:attribute name="creator" type="xsd:string" use="required"/>
 * </xsd:complexType>
 * }
 * </pre>
 * 
 * @author shiguera
 */
public class GpxDocument  extends GpxElement {	
	
	final String HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"+
		"<gpx version=\"1.1\" creator=\"MercatorLab - http:mercatorlab.com\" "+
		"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "+
		"xmlns=\"http://www.topografix.com/GPX/1/1\" "+
		"xsi:schemaLocation=\"http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd\" "+
		"xmlns:mlab=\"http://mercatorlab.com/downloads/mlab.xsd\">";
	final String FOOTER = "</gpx>";
	final static int XMLFORMAT_INDENT = 3;
	
	/**
	 * DOM Document tipo gpx
	 */
	protected Document doc;

	/**
	 * Metadata del GpxDocument
	 */
	protected Metadata metadata;
	/**
	 * Colección de WayPoint's del GpxDocument
	 */
	protected ArrayList<WayPoint> wpts; 
	/**
	 * Colección de Route's del GpxDocument
	 */
	protected ArrayList<Route> routes; 

	/**
	 * Colección de Track's del GpxDocument
	 */
	protected ArrayList<Track> tracks;
	
	// FIXME Falta el elemento <extensions>

	/**
	 * Constructor de clase. Inicializa las colecciones.
	 */
	public GpxDocument() {		
		this.metadata= new Metadata();
		this.routes = new ArrayList<Route>();
		this.tracks = new ArrayList<Track>();
		this.wpts = new ArrayList<WayPoint>();
	}

	public Document getDoc() {
		doc = GpxDocument.parseXmlDocument(this.asGpx());
		return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	public ArrayList<WayPoint> getWpts() {
		return wpts;
	}

	public void setWpts(ArrayList<WayPoint> wpts) {
		this.wpts = wpts;
	}

	public ArrayList<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(ArrayList<Route> routes) {
		this.routes = routes;
	}

	public ArrayList<Track> getTracks() {
		return tracks;
	}

	public void setTracks(ArrayList<Track> tracks) {
		this.tracks = tracks;
	}

	/**
	 * Añade un track a la colección de tracks del GpxDocument
	 * @param track Track que se quiere añadir
	 */
	public void addTrack(Track track) {
		// Añadir el track a la colección de tracks
		this.tracks.add(track);
		
	}
	
	/**
	 * Devuelve el número de tracks en el documento
	 * @return Devuelve el número de tracks en el documento
	 */
	public int trackCount() {
		return this.tracks.size();
	}
	
	/**
	 * Añade un WayPoint a la colección de WayPoints
	 * @param wp WayPoint que se quiere añadir
	 */
	public void addWayPoint(WayPoint wp) {
		this.wpts.add(wp);
	}
	public int wayPointCount() {
		return this.wpts.size();
	}
	/**
	 * Añade una Route a la colección de rutas del GpxDocument
	 * @param rte Route que se quiere añadir
	 */
	public void addRoute(Route rte) {
		this.routes.add(rte);
	}
	public int routeCount() {
		return this.routes.size();
	}
	/**
	 * Devuelve una cadena con el documento xml
	 * @return Cadena gpx del documento, incluso cabecera xml
	 */
	@Override
	public String asGpx() {
		String cad = "";
		cad += HEAD;
		
		cad += this.metadata.asGpx();
		
		if(this.wpts.size()>0) {
			for(int i=0; i<this.wpts.size(); i++) {
				cad += this.wpts.get(i).asGpx();
			}
		}

		if(this.routes.size()>0) {
			for(int i=0; i<this.routes.size(); i++) {
				cad += this.routes.get(i).asGpx();
			}
		}
		System.out.println(cad);

		if(this.tracks.size()>0) {
			for(int i=0; i<this.tracks.size(); i++) {
				cad += this.tracks.get(i).asGpx();
			}
		}
		
		// FIXME Falta resolver el elemento <extensions>

		cad += FOOTER;
		return cad;
	}
	
//	/**
//	 * Formatea una cadena xml con cambios de linea y tabulaciones. 
//	 * Añade la cabecera de documento
//	 * @param cadxml cadena xml que se quiere formatear
//	 * @return Cadena formateada
//	 */
//	public static String format-old(String cadxml) {
//		try {
//			if(true) {
//        		return cadxml;
//        	}
//        	final InputSource src = new InputSource(new StringReader(cadxml));
//        	final Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src);        	
//        	DOMImplementation impl = document.getImplementation();
//        	DOMImplementationLS implLS = (DOMImplementationLS) impl.getFeature("LS", "3.0");
//        	LSSerializer lsSerializer = implLS.createLSSerializer();
//        	lsSerializer.getDomConfig().setParameter("format-pretty-print", true);
//			
//        	LSOutput lsOutput = implLS.createLSOutput();
//        	
//        	lsOutput.setEncoding("UTF-8");
//        	Writer stringWriter = new StringWriter();
//        	lsOutput.setCharacterStream(stringWriter);
//        	lsSerializer.write(document, lsOutput);
//        	String result = stringWriter.toString(); 
//            return result;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//	}

	
	
	/**
	 * Formatea una cadena xml con cambios de linea y tabulaciones. 
	 * Añade la cabecera de documento
	 * @param cadxml cadena xml que se quiere formatear
	 * @return Cadena formateada
	 */
	public static String format(String cadxml) {
		String xmlString="";
		Document node = GpxDocument.parseXmlDocument(cadxml);
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
	
	public String format() {
		String xml=this.asGpx();
        return GpxDocument.format(xml);
    }
	
	/**
	 * Escribe una cadena de texto en un fichero
	 * 
	 * @param filename
	 *            String Nombre del fichero
	 * @param cad
	 *            String Cadena de texto a escribir
	 * @return 1 si todo va bien, negativo o cero en caso contrario
	 */
	public static int write(String filename, String cad) {
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(filename));
			writer.write(cad + "\n");
			writer.close();
		} catch (FileNotFoundException fe) {
			System.out.println("File " + filename + " not found.\n"
					+ fe.getMessage());
			return -1;
		} catch (NumberFormatException ne) {
			System.out.println("Number format error. " + ne.getMessage());
			return -2;
		} catch (Exception e) {
			System.out.println("Unidentified error. " + e.getMessage());
			return -3;
		}
    
		return 1;
	}
	
	/**
	 * 
	 */
	public static GpxElement parseGpxString(String cadgpx) {
		GpxDocument gpxdoc = parseGpxDocument(cadgpx);		
		return gpxdoc;
	}
	
	/**
	 * Parsea un documento de texto gpx a GpxDocument
	 * @param cadgpx
	 * @return GpxDocument 
	 */
	public static GpxDocument parseGpxDocument(String cadgpx) {
		Document doc= GpxDocument.parseXmlDocument(cadgpx);
		if(doc.getDocumentElement().getNodeName().equalsIgnoreCase("gpx")==false) {
			return null;
		}
		//Element gpx=doc.getDocumentElement();
		
		GpxDocument gpxDocument = new GpxDocument();
		
		// FIXME Procesar metadata
		
		// Procesar nodos WayPoint
		NodeList nl=doc.getElementsByTagName("wpt");
		for(int i=0; i< nl.getLength(); i++) {
			WayPoint wp= WayPoint.parseGpxString(GpxDocument.nodeAsString(nl.item(i),false));
			if(wp!=null) {
				gpxDocument.addWayPoint(wp);
			}
		}
		// Procesar nodos RTE
		nl=doc.getElementsByTagName("rte");
		for(int i=0; i< nl.getLength(); i++) {
			Route rte= Route.parseGpxString(GpxDocument.nodeAsString(nl.item(i),false));
			if(rte!=null) {
				gpxDocument.addRoute(rte);
			}
		}
		// Procesar nodos TRK
		nl=doc.getElementsByTagName("trk");
		for(int i=0; i< nl.getLength(); i++) {
			Track trk= Track.parseGpxString(GpxDocument.nodeAsString(nl.item(i),false));
			if(trk!=null) {
				gpxDocument.addTrack(trk);
			}
		}
		
		// TODO Procesar nodos Extensions

		return gpxDocument;
	}
	
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
	 * @return String con la cadena xml del Node sin cabecera xml
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

}
