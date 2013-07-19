package com.mlab.tesis.java.gpx.data;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;

/**
 * Clase utilitaria para manipulación de documentos Gpx
 * Básicamente se compone de un elemento Metadata, uno Extensions y unas colecciones
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
public class SimpleGpxDocument  implements GpxDocument {	
	private final String TAG_WAYPOINT = "wpt";
	
	final String HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"+
		"<gpx version=\"1.1\" creator=\"MercatorLab - http:mercatorlab.com\" "+
		"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "+
		"xmlns=\"http://www.topografix.com/GPX/1/1\" "+
		"xsi:schemaLocation=\"http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd\" "+
		"xmlns:mlab=\"http://mercatorlab.com/downloads/mlab.xsd\">";
	final String FOOTER = "</gpx>";
	
	protected GpxFactory gpxFactory;
	
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
	protected List<WayPoint> wpts; 
	/**
	 * Colección de Route's del GpxDocument
	 */
	protected List<Route> routes; 

	/**
	 * Colección de Track's del GpxDocument
	 */
	protected List<Track> tracks;
	
	// FIXME Falta el elemento <extensions>

	/**
	 * Constructor de clase. Inicializa las colecciones.
	 * He añadido el parámetro para el factory,
	 * lo que rompe la compatibilidad con programas ya hechos
	 * 
	 */
	public SimpleGpxDocument() {
		this.gpxFactory = GpxFactory.getFactory(GpxFactory.Type.SimpleGpxFactory);
		this.doc = null;
		this.metadata= new Metadata();
		this.routes = new ArrayList<Route>();
		this.tracks = new ArrayList<Track>();
		this.wpts = new ArrayList<WayPoint>();
	}

	@Override
	public Document getDomDocument() {
		doc = GpxFactory.parseXmlDocument(this.asGpx());
		return doc;
	}

//	public void setDoc(Document doc) {
//		this.doc = doc;
//	}

	@Override
	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	@Override
	public List<WayPoint> getWayPoints() {
		return wpts;
	}

	public void setWpts(ArrayList<WayPoint> wpts) {
		this.wpts = wpts;
	}

	@Override
	public List<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(ArrayList<Route> routes) {
		this.routes = routes;
	}
	
	@Override
	public List<Track> getTracks() {
		return tracks;
	}

	public void setTracks(ArrayList<Track> tracks) {
		this.tracks = tracks;
	}

	/**
	 * Añade un track a la colección de tracks del GpxDocument
	 * @param track Track que se quiere añadir
	 */
	@Override
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
	@Override
	public void addWayPoint(WayPoint wp) {
		wp.setTag(TAG_WAYPOINT);
		this.wpts.add(wp);
	}
	public int wayPointCount() {
		return this.wpts.size();
	}
	/**
	 * Añade una Route a la colección de rutas del GpxDocument
	 * @param rte Route que se quiere añadir
	 */
	@Override
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
				//System.out.println(this.wpts.get(i).asGpx());
				cad += this.wpts.get(i).asGpx();
			}
		}

		if(this.routes.size()>0) {
			for(int i=0; i<this.routes.size(); i++) {
				cad += this.routes.get(i).asGpx();
			}
		}
		//System.out.println(cad);

		if(this.tracks.size()>0) {
			for(int i=0; i<this.tracks.size(); i++) {
				cad += this.tracks.get(i).asGpx();
			}
		}
		
		// FIXME Falta resolver el elemento <extensions>
		
		
		cad += FOOTER;
		return cad;
	}
	
	public String format() {
		String xml=this.asGpx();
        return GpxFactory.format(xml);
    }

	@Override
	public Extensions getExtensions() {
		// TODO Auto-generated method stub
		return null;
	}
		
}
