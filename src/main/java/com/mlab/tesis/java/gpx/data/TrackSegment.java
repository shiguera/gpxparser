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
	
	private TSerie tSerie;
	
	public TrackSegment() {
		// (lon,lat,alt,vel,rumbo,accuracy)
		tSerie=new TSerie(6);
		
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
		if(this.tSerie.size()>0) {
			last=this.tSerie.size()-1;
			if(wp.time <= this.tSerie.time(last)) {
				return false;
			}
		}
		this.tSerie.add(wp.time, wp.getValues());
		return true;
	}
	
	/**
	 * Devuelve el tiempo correspondiente al primer WayPoint del TrackSegment
	 * @return long Tiempo del primer WayPoint del TrackSegment
	 */
	public long getFirstTime() {
		long t=-1l;
		if(tSerie.size()>0) {
			t=tSerie.time(0);
		}
		return t;
	}
	
	/**
	 * Devuelve el tiempo correspondiente al último WayPoint del TrackSegment
	 * @return long Tiempo del último WayPoint del TrackSegment
	 */
	public long getLastTime() {
		long t=-1l;
		if(tSerie.size()>0) {
			t=tSerie.time(tSerie.size()-1);
		}
		return t;
	}
	public int wayPointCount() {
		return this.tSerie.size();
	}
	/**
	 * Devuelve un double con los valores de 
	 * [lon,lat,alt,vel,rumbo,acc] interpolados para ese tiempo
	 * @param time tiempo en milisegundos UTC de los valores solicitados
	 * @return double[] [lon,lt,alt,vel,rumbo,acc] con los valores interpolados.<p>
	 * Si el tiempo es menor que el primero del trkseg o mayor
	 * que el último del trkseg devuelve null
	 */
	public double[] getValues(long time) {
		return this.tSerie.values(time);
	}
	
	/**
	 * Devuelve una cadena Gpx del segmento TrackSegment
	 */
	@Override
	public String asGpx() {
		String cad="<trkseg>";
		// Comprobar que hay algún WayPoint
		if(tSerie.size()>0) {
			for(int i=0;i<this.tSerie.size(); i++) {
				long time=this.tSerie.time(i);
				double[] values=this.tSerie.values(i);
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

	public static TrackSegment parseGpxString(String cadgpx) {
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
					WayPoint wp= WayPoint.parseGpxString(GpxDocument.nodeAsString(nl.item(i),false));
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
		return this.tSerie.size();
	}
}
