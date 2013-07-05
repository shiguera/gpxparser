package com.mlab.tesis.java.gpx.data;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
public class Track  extends AbstractGpxElement {
	private final String TAGNAME = "trk";
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
	 * Constructor. Crea un Track vacío e inicializa la colección de TrackSegment
	 */
	public Track() {
		super();
		this.tagname = TAGNAME;
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
		if(this.size()>0) {
			for(int i=0; i<this.size(); i++){
				cad += this.nodes.get(i).asGpx();				
			}
		}
		cad += "</trk>";
		return cad;		
	}
	
	public String asCsv(boolean withUtmCoords) {
		StringBuilder builder = new StringBuilder();
		if(this.size()>0) {
			for(int i=0; i<this.nodes.size(); i++) {
				if(((TrackSegment)this.nodes.get(i)).size()>0) {
					builder.append(((TrackSegment)this.nodes.get(i)).asCsv(withUtmCoords));
					if(!isLastSegment(i)) {
						builder.append("\n");
					}
				}
			}
		}
		return builder.toString();
	}
	private boolean isLastSegment(int index) {
		if(index==this.nodes.size()-1) {
			return true;
		}
		return false;
	}
	
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
		if (newTrackSegment==true || size()==0) {
			this.nodes.add(new TrackSegment());
		} 
		ts=(TrackSegment)this.nodes.get(size()-1);
		result=ts.addWayPoint(wp);
		return result;
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
		if(size()>0) {
			for(int i=0; i<size(); i++) {
				if(time>=((TrackSegment)nodes.get(i)).getStartTime() && 
						time<=((TrackSegment)nodes.get(i)).getEndTime()) {
					result=((TrackSegment)nodes.get(i)).getValues(time);
				}
			}
		}
		return result;
	}
	
	public long getStartTime() {
		long startTime = -1l;
		if(size()>0) {
			startTime = ((TrackSegment)this.nodes.get(0)).getStartTime();
		}
		return startTime;
	}

	public long getEndTime() {
		long lastTime = -1l;
		if(size()>0) {
			int ultimo = size()-1;
			lastTime = ((TrackSegment)this.nodes.get(ultimo)).getEndTime();
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
		if(size()>0) {
			waypoint = ((TrackSegment)nodes.get(0)).getStartWayPoint();
		}
		return waypoint;
	}
	
	public WayPoint getEndWayPoint() {
		WayPoint waypoint = null;
		if(size()>0) {
			int ultimo = size()-1;
			waypoint = ((TrackSegment)nodes.get(ultimo)).getEndWayPoint();
		}
		return waypoint;
	}
	/**
	 * Devuelve [minx, miny, maxx, maxy]
	 * @return
	 */
	public double[] getBounds() {
		double[] result = null;
		if(size()>0) {
			WayPoint p = getStartWayPoint();
			double minx = p.getLongitude();
			double maxx = minx;
			double miny = p.getLatitude();
			double maxy = miny;
			for (int i=0; i<size(); i++) {
				for(int j=0; j<((TrackSegment)nodes.get(i)).size(); j++) {
					p = ((TrackSegment)nodes.get(i)).getWayPoint(j);
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

	/**
	 * Permite recuperar un TrackSegment del Trac
	 * @param index índice del TrackSegment
	 * @return Un TrackSegment o lanza IndexOutOfBoundException
	 * @exception IndexOutOfBoundException si el índice no existe en la lista
	 */
	public TrackSegment getTrackSegment(int index) {
		if(index>=0 && index<size()) {
			return (TrackSegment) this.nodes().get(index);
		} else {
			throw new IndexOutOfBoundsException();
		}
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

	@Override
	public boolean add(GpxNode node) {
		if(TrackSegment.class.isAssignableFrom(node.getClass())) {
			return this.addTrackSegment((TrackSegment)node);
		}
		return false;
	}
	/**
	 * Añade un TrackSegments a la lista segments.<br/>
	 * El primer tiempo del TrackSegment que se añade tiene que ser
	 * mayor que el ,ultimo tiempo del último TrackSegment del Track.
	 * @param ts TrackSegment 
	 * @return boolean true si se añade y false si no se añade; 
	 */
	public boolean addTrackSegment(TrackSegment ts) {
		if(size()>0) {
			long last = ((TrackSegment)nodes.get(size()-1)).getEndTime();
			if(ts.getStartTime() <= last) {
				return false;
			}
		}
		return nodes.add(ts);
	}

	/**
	 * Devuelve el número de WayPoints totales del track
	 */
	public int wayPointCount() {
		int wpc = 0;
		if(this.size()>0) {
			for(GpxNode segment: this.nodes()) {
				wpc += ((TrackSegment)segment).size();
			}
		}
		return wpc;
	}
	
	public double length() {
		double length = 0.0;
		if(this.nodes.size()>0) {
			for(int i=0; i< this.nodes.size(); i++) {
				length += ((TrackSegment)this.nodes().get(i)).length();
			}
				
		}
		return length;
	}
}
