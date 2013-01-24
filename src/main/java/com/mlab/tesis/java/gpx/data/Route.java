package com.mlab.tesis.java.gpx.data;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.mlab.tesis.java.tserie.TSerie;

public class Route  extends GpxElement {

	TSerie pts=null;
	
	public Route() {
		pts=new TSerie(6);
		
	}
	
	/**
	 * Añade un WayPoint al final de la TSerie que contiene
	 * la lista de WayPoint del Route. Si el tiempo del 
	 * WayPoint que se quiere añadir es menor o igual que el
	 * último tiempo de la TSerie no se añade y se devuelve -1
	 * @param wp WayPoint que se quiere añadir al Route
	 * @return int Indice en la TSerie del WayPoint añadido o 
	 * -1 si hay errores 
	 */
	public int addWayPoint(WayPoint wp) {
		int last=0;
		if(this.pts.size()>0) {
			last=this.pts.size()-1;
			if(wp.time <= this.pts.time(last)) {
				return -1;
			}
		}
		this.pts.add(wp.time, wp.getValues());
		return this.pts.size()-1;
	}

	/**
	 * Numero de puntos del Route.
	 * @return int número de WayPoint's en el Route
	 */
	public int size() {
		return this.pts.size();
	}
	
	@Override
	public String asGpx() {
		String cad="<rte>";
		for(int i=0; i< this.pts.size(); i++) {
			WayPoint wp=WayPoint.fromValues(this.pts.time(i), this.pts.values(i));
			if(wp!=null) {
				wp.TAG="rtept";
				cad+=wp.asGpx();
			}
		}
		cad+="</rte>";
		return cad;
	}
	
	public static Route parseGpxString(String cadgpx) {
		Route rte= new Route();

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
		if(doc.getDocumentElement().getNodeName().equalsIgnoreCase("rte")==false) {
			return null;
		}
		NodeList nl=doc.getElementsByTagName("rtept");
		if(nl.getLength()>0) {
			for (int i=0; i<nl.getLength(); i++) {
				try {
					WayPoint wp= WayPoint.parseGpxString(GpxDocument.nodeAsString(nl.item(i),false));
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
}
