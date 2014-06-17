package com.mlab.gpx.api;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.mlab.gpx.impl.AndroidGpxFactory;
import com.mlab.gpx.impl.GpxDocumentImpl;
import com.mlab.gpx.impl.Route;
import com.mlab.gpx.impl.SimpleGpxFactory;
import com.mlab.gpx.impl.Track;
import com.mlab.gpx.impl.TrackSegment;
import com.mlab.gpx.impl.extensions.ExtendedGpxFactory;
import com.mlab.gpx.impl.util.Util;
import com.mlab.gpx.impl.util.XmlFactory;

/**
 * Clase base abstracta para las implementaciones concretas de
 * clases factory para leer y escribir documentos gpx.<br/>
 * El método estático <em>getFactory()</em> nos proporciona la instancia 
 * de factory concreta en base al parámetro 'GpxFactory.Type'.<br/>
 * La diferencia en las factories está en el tipo de WayPoint que utilizan.
 * A la fecha hay tres tipos de GpxFactory:<br/>
 * <ul>
 * <li>SimpleGpxFactory: Lee y escribe SimpleWayPoints, esto es, sin extensiones</li>
 * <li>AndroidGpxFactory: Lee y escribe AndroidWayPoints</li>
 * <li>ExtendedWayPoint: Lee y escribe ExtendedWayPoints</li>
 * </ul>
 * La factories son compatibles en el sentido de que cualquier factory lee un documento
 * de cualquier tipo. Eso sí, un documento Gpx leido con una SimpleGpxFactory no será capaz
 * de leer las extensiones de los puntos, si existen.
 *  Dispone de los siguientes métodos:<br/>
 * - readGpxDocument(): Devuelve un gpxDocument a partir de un File<br/>
 * - parseGpxDocument() : Devuelve un GpxDocument a partir de un String xml <br/>
 * - nodeAsFormatedXmlString() : Devuelve un String formateado en xml a partir
 * de un org.w3c.dom.Node<br/>
 * - createWayPoint(): crea un WayPoint del tipo correspondiente a la factory<br/>
 *  - format(): Devuelve un String xml formateado con saltos de línea y 
 *  tabulaciones a partir de un String xml sin formatear<br/>
 *  El resto de métodos para parsear documentos son abstractos 
 *  y los deben implementar las subclases.<br/>
 * 
 * @author shiguera
 *
 */
public abstract class GpxFactory {
	private static final Logger LOG = Logger.getLogger(GpxFactory.class.getName());
	
	private final String WAYPOINT_NODENAME = "wpt";
	private final String ROUTE_NODENAME = "rte";
	private final String TRACK_NODENAME = "trk";
	
	
	/**
	 * Variable de tipo de Factory a instanciar a través 
	 * del método estático 'getFactory()' 	
	 */
	public enum Type {SimpleGpxFactory, AndroidGpxFactory, ExtendedGpxFactory};
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
			case AndroidGpxFactory:
				return new AndroidGpxFactory();
			case ExtendedGpxFactory:
				return new ExtendedGpxFactory();
		}
		return null;
	}

	/**	
	 * Parsea un String conteniendo un documento GPX y devuelve
	 * un objeto GpxDocument. Los WayPoint contenidos en el GpxDocument serán
	 * del tipo de los de la factory concreta que esté parseando el documento. Esto
	 * se realiza a través del método abstracto createWayPoint().
	 * 
	 * @param cadgpx String con un documento xml válido del tipo GPX
	 * 
	 * @return GpxDocument Instancia de la clase GpxDocument correspondiente
	 * al documento GPX parseado o null si hay errores. 
	 */
	public GpxDocument parseGpxDocument(String cadgpx) {
		Document doc= XmlFactory.parseXmlDocument(cadgpx);
		//System.out.println(doc.getTextContent());
		if(!isValidGpxDocument(doc)) {
			return null;
		}
		//Element gpx=doc.getDocumentElement();
		
		GpxDocument gpxDocument = createGpxDocument();
		
		// FIXME Procesar metadata
		
		// Procesar nodos WayPoint
		processWayPoints(gpxDocument, doc);
		
		// Procesar nodos Route
		processRoutes(gpxDocument, doc);
		
		// Procesar nodos TRK
		processTracks(gpxDocument, doc);
		
		// TODO Procesar nodos Extensions

		return gpxDocument;
	}
	private void processWayPoints(GpxDocument gpxdocument, Document doc) {
		NodeList nl=doc.getElementsByTagName(WAYPOINT_NODENAME);
		//System.out.println("----------------\n"+nl.getLength()+"---------------\n");
		for(int i=0; i< nl.getLength(); i++) {
			WayPoint wp= parseWayPoint(XmlFactory.nodeAsFormatedXmlString(nl.item(i),false));
			if(wp!=null) {
				//System.out.println("GpxFactory.parseGpxDocument(): adding way point to gpxdoc...");	
				gpxdocument.addWayPoint(wp);
			}
		}
	}
	private void processRoutes(GpxDocument gpxdocument, Document doc) {
		NodeList nl=doc.getElementsByTagName(ROUTE_NODENAME);
		for(int i=0; i< nl.getLength(); i++) {
			Route rte= parseRoute(XmlFactory.nodeAsFormatedXmlString(nl.item(i),false));
			if(rte!=null) {
				gpxdocument.addRoute(rte);
			}
		}
	}
	private void processTracks(GpxDocument gpxdocument, Document doc) {
		NodeList nl=doc.getElementsByTagName(TRACK_NODENAME);
		for(int i=0; i< nl.getLength(); i++) {
			Track trk= parseTrack(XmlFactory.nodeAsFormatedXmlString(nl.item(i),false));
			if(trk!=null) {
				gpxdocument.addTrack(trk);
			}
		}	
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
		GpxFactory factory = GpxFactory.getFactory(GpxFactory.Type.ExtendedGpxFactory);
		GpxDocument gpxDoc = (GpxDocumentImpl) factory.parseGpxDocument(cad);
		if(gpxDoc!=null) {
			gpxDoc.setGpxFile(gpxFile);
		} else {
			LOG.warning("GpxFactory.readGpxDocument() ERROR: can't parse GpxDocument "+gpxFile.getName());
		}
		return gpxDoc;
	}
	
	/**
	 * Crea una instancia de WayPoint a partir de la cadena
	 * gpx del mismo 
	 * @param cadgpx String <wpt lon=....></wpt>
	 * @return WayPoint o null si hay errores
	 */
	private WayPoint parseWayPoint(String cadgpx) {
		Document doc = XmlFactory.parseXmlDocument(cadgpx);
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
		Document doc = XmlFactory.parseXmlDocument(cadgpx);
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
					WayPoint wp= parseWayPoint(XmlFactory.nodeAsFormatedXmlString(nl.item(i),false));
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
		Document doc = XmlFactory.parseXmlDocument(cadgpx);
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
					WayPoint wp= parseWayPoint(XmlFactory.nodeAsFormatedXmlString(nl.item(i),false));
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
		Document doc = XmlFactory.parseXmlDocument(cadgpx);
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
					TrackSegment ts = this.parseTrackSegment(XmlFactory.nodeAsFormatedXmlString(nl.item(i),false));
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
				LOG.warning("GpxFactory.parseDoubleTag(): can't parse number\n"+e.getMessage());
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
	
	
	public GpxDocument createGpxDocument() {
		return new GpxDocumentImpl();
	}
	// Abstract methods
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
	
//	public String asCsv(WayPoint wp) {
//		return wp.asCsv(false);
//	}
	
}
