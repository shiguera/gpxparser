package com.mlab.tesis.java.gpx.data;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * La clase Track guarda un track de un documento GpxDocument. Básicamente consiste
 * en una serie de metadatos y una colección de TrackSegment<p> 
 * El esquema xsd<p>
 * <pre>
 * {@code
 * <xsd:complexType name="trkType">
 *     <xsd:sequence>
 *        <xsd:element name="name" type="xsd:string" minOccurs="0"/>
 *        <xsd:element name="cmt" type="xsd:string" minOccurs="0"/>
 *        <xsd:element name="desc" type="xsd:string" minOccurs="0"/>
 *        <xsd:element name="src" type="xsd:string" minOccurs="0"/>
 *        <xsd:element name="link" type="linkType" minOccurs="0" maxOccurs="unbounded"/>
 *        <xsd:element name="number" type="xsd:nonNegativeInteger" minOccurs="0"/>
 *        <xsd:element name="type" type="xsd:string" minOccurs="0"/>
 *        <xsd:element name="extensions" type="extensionsType" minOccurs="0"/>
 *        <xsd:element name="trkseg" type="trksegType" minOccurs="0" maxOccurs="unbounded"/>
 *    </xsd:sequence>
 * </xsd:complexType>
 * }
 * </pre>
 */
public class Track  extends GpxElement {
	private String name="";
	private String cmt="";
	private String desc="";
	private String src="";
	// FIXME El esquema xsd especifica una colección de links
	// FIXME Utilizar la clase Link en lugar de una cadena de texto
	private String link="";
	private int number=0;
	private String type="";
	
	/**
	 * Colección de TrackSegment del Track
	 */
	public ArrayList<TrackSegment> segments;
	
	/**
	 * Constructor. Crea un Track vacío e inicializa la colección de TrackSegment
	 */
	public Track() {
		segments=new ArrayList<TrackSegment>();
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the cmt
	 */
	public String getCmt() {
		return cmt;
	}

	/**
	 * @param cmt the cmt to set
	 */
	public void setCmt(String cmt) {
		this.cmt = cmt;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the src
	 */
	public String getSrc() {
		return src;
	}

	/**
	 * @param src the src to set
	 */
	public void setSrc(String src) {
		this.src = src;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String asGpx() {
		String cad="<trk>";
		if(!this.name.equals("")) {
			cad += "<name>"+this.name+"</name>";
		}
		if(!this.cmt.equals("")) {
			cad += "<cmt>"+this.cmt+"</cmt>";
		}
		if(!this.desc.equals("")) {
			cad += "<desc>"+this.desc+"</desc>";
		}
		if(!this.src.equals("")) {
			cad += "<src>"+this.src+"</src>";
		}
		if(!this.link.equals("")) {
			cad += "<link>"+this.link+"</link>";
		}
		if(this.number!=-1) {
			cad += "<number>"+String.format("%d", this.number)+"</number>";
		}
		if(!this.type.equals("")) {
			cad += "<type>"+this.type+"</type>";
		}
		if(this.segments.size()>0) {
			for(int i=0; i<this.segments.size(); i++){
				cad += this.segments.get(i).asGpx();				
			}
		}
		cad += "</trk>";
		return cad;		
	}
	
	// FIXME Implementar addWayPoint()
	/**
	 * Añade un WayPoint al último TrackSegment del Track o 
	 * a un nuevo segmento que se crea al efecto. <br/>
	 * Si se crea un nuevo TrackSegment, (parámetro newTrackSegment=true),
	 * la fecha del WayPoint deberá ser posterior a la del último punto
	 * del último TrackSegment del Track.<br/>
	 * Si se añade el WayPoint con <em>newTrackSegment=false</em>, se añadirá
	 * al último TrackSegment existente y la fecha del WayPoint también
	 * deberá ser posterior a la del último punto de ese TrackSegment.<br/>
	 * Si no existieran aun TrackSegment's en el Track se creará uno
	 * nuevo, independientemente del valor del parámetro <em>'newTrackSegment'</em>
	 * @param wp WayPoint que se quiere añadir
	 * @param newTrackSegment boolean true si se quiere crear un nuevo 
	 * TrackSegment para alojar el punto. Si false, el punto se añade
	 * al último TrackSegment del Track
	 * @return boolean true si se añade el punto, false si no se pudo añadir
	 */
	public boolean addWayPoint(WayPoint wp, boolean newTrackSegment) {
		boolean result=false;
		TrackSegment ts=null;
		if (newTrackSegment==true || segments.size()==0) {
			this.segments.add(new TrackSegment());
		} 
		ts=this.segments.get(segments.size()-1);
		result=ts.addWayPoint(wp);
		return result;
	}
	
	/**
	 * Añade un TrackSegments a la lista segments.<br/>
	 * El primer tiempo del TrackSegment que se añade tiene que ser
	 * mayor que el ,ultimo tiempo del último TrackSegment del Track.
	 * @param ts TrackSegment 
	 * @return boolean true si se añade y false si no se añade; 
	 */
	public boolean addTrackSegment(TrackSegment ts) {
		if(segments.size()>0) {
			long last = segments.get(segments.size()-1).getEndTime();
			if(ts.getEndTime() <= last) {
				return false;
			}
		}
		segments.add(ts);
		return true;
	}
	
	
	/**
	 * Devuelve un double con los valores de 
	 * [lon,lat,alt,vel,rumbo,acc] interpolados para ese tiempo
	 * @param time tiempo en milisegundos UTC de los valores solicitados
	 * @return double[] [lon,lt,alt,vel,rumbo,acc] con los valores interpolados.<p>
	 * Si el tiempo es menor que el primer WayPoint del trk o mayor
	 * que el último devuelve null
	 */
	public double[] getValues(long time) {
		double[] result=null;
		if(this.segments.size()>0) {
			for(int i=0; i<segments.size(); i++) {
				if(time>=segments.get(i).getStartTime() && time<=segments.get(i).getEndTime()) {
					result=segments.get(i).getValues(time);
				}
			}
		}
		return result;
	}
	
	public long getStartTime() {
		long startTime = -1l;
		if(this.segments.size()>0) {
			startTime = this.segments.get(0).getStartTime();
		}
		return startTime;
	}

	public long getEndTime() {
		long lastTime = -1l;
		if(this.segments.size()>0) {
			int ultimo = this.segments.size()-1;
			lastTime = this.segments.get(ultimo).getEndTime();
		}
		return lastTime;
	}

	public double[] getStartValues() {
		return getValues(getStartTime());
	}
	public double[] getEndValues() {
		return getValues(getEndTime());
	}
	
	public WayPoint getStartWayPoint() {
		WayPoint waypoint = null;
		if(this.segments.size()>0) {
			waypoint = this.segments.get(0).getStartWayPoint();
		}
		return waypoint;
	}
	
	public WayPoint getEndWayPoint() {
		WayPoint waypoint = null;
		if(this.segments.size()>0) {
			int ultimo = this.segments.size()-1;
			waypoint = this.segments.get(ultimo).getEndWayPoint();
		}
		return waypoint;
	}
	/**
	 * Devuelve [minx, miny, maxx, maxy]
	 * @return
	 */
	public double[] getBounds() {
		double[] result = null;
		if(this.segments.size()>0) {
			WayPoint p = getStartWayPoint();
			double minx = p.longitude;
			double maxx = minx;
			double miny = p.latitude;
			double maxy = miny;
			for (int i=0; i<segments.size(); i++) {
				for(int j=0; j<segments.get(i).size(); j++) {
					p = segments.get(i).getWpts().get(j);
					double x = p.getLongitude();
					double y = p.getLatitude();
					if(x<minx) {
						minx=x;
					}
					if(x>maxx) {
						maxx=x;
					}
					if(y<miny) {
						miny=y;
					}
					if(y>maxy) {
						maxy=y;
					}
				}
			}
			result = new double[]{minx,miny,maxx,maxy};
		}
		return result;
	}


	public Element asElement() {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder=null;
		InputStream is = null;
		Document doc = null;
		Element el=null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		    is = new ByteArrayInputStream(this.asGpx().getBytes("UTF-8"));
		    doc = dBuilder.parse(is);	
			doc.getDocumentElement().normalize();
			el=doc.getDocumentElement();
		} catch (Exception e) {
            e.printStackTrace();
            return null;
        }
		return el;
	}

	// FIXME implementar método static parseGpx() 
	public static Track parseGpxString(String cadgpx) {
		Track t= new Track();

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder=null;
		InputStream is = null;
		Document doc = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		    is = new ByteArrayInputStream(cadgpx.getBytes("UTF-8"));
		    doc = dBuilder.parse(is);	
			doc.getDocumentElement().normalize();
		} catch (Exception e) {
            e.printStackTrace();
            return null;
        }
		if(doc.getDocumentElement().getNodeName().equalsIgnoreCase("trk")==false) {
			return null;
		}
		NodeList nl=doc.getElementsByTagName("trkseg");
		if(nl.getLength()>0) {
			for (int i=0; i<nl.getLength(); i++) {
				try {
					TrackSegment ts = TrackSegment.parseGpxString(GpxDocument.nodeAsString(nl.item(i),false));
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

}
