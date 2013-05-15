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

public class Route  implements GpxElement {

	//TSerie pts=null;
	ArrayList<GpxNode> pts = null;
	
	public Route() {
		//pts=new TSerie(6);
		pts = new ArrayList<GpxNode>();
	}
	
	/**
	 * Añade un WayPoint al final de la ArrayList que contiene
	 * la lista de WayPoint del Route. 
	 * @param wp WayPoint que se quiere añadir al Route
	 * @return boolean true si se añade 
	 */
	public boolean addWayPoint(WayPoint wp) {
		return this.pts.add(wp);
	}
	public WayPoint getWayPoint(int index) {
		WayPoint wp=null;
		int last = this.pts.size()-1;
		if(index>=0 && index<=last) {
			wp = (WayPoint)this.pts.get(index);
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
			WayPoint wp = (WayPoint)pts.get(i);
			if(wp!=null) {
				wp.setTag("rtept");
				cad+=wp.asGpx();
			}
		}
		cad+="</rte>";
		return cad;
	}

	@Override
	public boolean add(GpxNode node) {
		if(WayPoint.class.isAssignableFrom(node.getClass())) {
			return this.addWayPoint((WayPoint)node);
		}
		return false;
	}

	@Override
	public GpxNodeList nodes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
