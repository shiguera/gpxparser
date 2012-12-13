package com.mlab.tesis.java.gpx.data;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.mlab.tesis.java.tserie.TSerie;

/**
 * Representa un elemento 'tracksegment' de un documento Gpx. La definición del elemento es:<p>
 * <pre>
 * {@code 
 * <xsd:complexType name="trksegType">
 *    <xsd:sequence>
 *       <-- elements must appear in this order --> 
 *       <xsd:element name="trkpt" type="wptType" minOccurs="0" maxOccurs="unbounded"/>  
 *       <xsd:element name="extensions" type="extensionsType" minOccurs="0"/>     
 *    </xsd:sequence>
 * </xsd:complexType>
 * }
 * </pre>
 * */
public class TrackSegment  extends GpxElement {
	
	private TSerie pts;
	
	public TrackSegment() {
		// (lon,lat,alt,vel,rumbo,accuracy)
		pts=new TSerie(6);
		
	}
	/**
	 * Añade un WayPoint al final de la TSerie que contiene
	 * la lista de WayPoint del TrackSegment. Si el tiempo del 
	 * WayPoint que se quiere añadir es menor o igual que el
	 * último tiempo de la TSerie no se añade y se devuelve false
	 * @param wp WayPoint que se quiere añadir al TrackSegment
	 * @return boolean true si se añade y false si no se añade
	 */
	public boolean addWayPoint(WayPoint wp) {
		int last=0;
		if(this.pts.size()>0) {
			last=this.pts.size()-1;
			if(wp.time <= this.pts.time(last)) {
				return false;
			}
		}
		this.pts.add(wp.time, wp.getValues());
		return true;
	}
	
	/**
	 * Devuelve el tiempo correspondiente al primer WayPoint del TrackSegment
	 * @return long Tiempo del primer WayPoint del TrackSegment
	 */
	public long getFirstTime() {
		long t=-1l;
		if(pts.size()>0) {
			t=pts.time(0);
		}
		return t;
	}
	
	/**
	 * Devuelve el tiempo correspondiente al último WayPoint del TrackSegment
	 * @return long Tiempo del último WayPoint del TrackSegment
	 */
	public long getLastTime() {
		long t=-1l;
		if(pts.size()>0) {
			t=pts.time(pts.size()-1);
		}
		return t;
	}
	public int wayPointCount() {
		return this.pts.size();
	}
	
	/**
	 * Devuelve una cadena Gpx del segmento TrackSegment
	 */
	@Override
	public String asGpx() {
		String cad="<trkseg>";
		// Comprobar que hay algún WayPoint
		if(pts.size()>0) {
			for(int i=0;i<this.pts.size(); i++) {
				long time=this.pts.time(i);
				double[] values=this.pts.values(i);
				WayPoint wp= WayPoint.fromValues(time, values);
				if(wp!=null) {
					wp.TAG = "trkpt";
					cad += wp.asGpx();					
				}
			}
		}
		cad += "</trkseg>";
		return cad;
	}

	public static TrackSegment parseGpx(String cadgpx) {
		TrackSegment ts= new TrackSegment();

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
		if(doc.getDocumentElement().getNodeName().equalsIgnoreCase("trkseg")==false) {
			return null;
		}
		NodeList nl=doc.getElementsByTagName("trkpt");
		if(nl.getLength()>0) {
			for (int i=0; i<nl.getLength(); i++) {
				try {
					WayPoint wp= WayPoint.parseGpx(GpxDocument.nodeAsString(nl.item(i)));
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
	public int size() {
		return this.pts.size();
	}
}
