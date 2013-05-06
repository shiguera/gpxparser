package com.mlab.tesis.java.gpx.data;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


// TODO Error! Las routes no tienen tiempo en los puntos, no 
// se pueden resolver a base de TSerie

public class Route  extends GpxElement {

	//TSerie pts=null;
	ArrayList<WayPoint> pts = null;
	
	public Route() {
		//pts=new TSerie(6);
		pts = new ArrayList<WayPoint>();
	}
	
	/**
	 * Añade un WayPoint al final de la ArrayList que contiene
	 * la lista de WayPoint del Route. 
	 * @param wp WayPoint que se quiere añadir al Route
	 * @return int Indice en la TSerie del WayPoint añadido 
	 */
	public int addWayPoint(WayPoint wp) {
		this.pts.add(wp);
		return this.pts.size()-1;
	}
	public WayPoint getWayPoint(int index) {
		WayPoint wp=null;
		int last = this.pts.size()-1;
		if(index>=0 && index<=last) {
			wp = this.pts.get(index);
		}
		return wp;
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
			WayPoint wp = pts.get(i);
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
