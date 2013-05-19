package com.mlab.tesis.java.gpx.data;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


// TODO Error! Las routes no tienen tiempo en los puntos, no 
// se pueden resolver a base de TSerie

public class Route extends AbstractGpxElement {

	private final String TAG_RTEPT = "rtept";

	public Route() {
		super();
		this.tagname = "rte";
	}
	
	/**
	 * Añade un WayPoint al final de la ArrayList que contiene
	 * la lista de WayPoint del Route. 
	 * @param wp WayPoint que se quiere añadir al Route
	 * @return boolean true si se añade 
	 */
	public boolean addWayPoint(WayPoint wp) {
		wp.setTag(TAG_RTEPT);
		return this.nodes.add(wp);
	}
	public WayPoint getWayPoint(int index) {
		WayPoint wp=null;
		if(index>=0 && index<=this.size()-1) {
			wp = (WayPoint)this.nodes.get(index);
		}
		return wp;
	}

//	@Override
//	public String asGpx() {
//		String cad="<rte>";
//		for(int i=0; i< this.pts.size(); i++) {
//			WayPoint wp = (WayPoint)pts.get(i);
//			if(wp!=null) {
//				wp.setTag("rtept");
//				cad+=wp.asGpx();
//			}
//		}
//		cad+="</rte>";
//		return cad;
//	}

	@Override
	public boolean add(GpxNode node) {
		if(WayPoint.class.isAssignableFrom(node.getClass())) {
			((WayPoint)node).setTag(TAG_RTEPT);
			return this.addWayPoint((WayPoint)node);
		}
		return false;
	}
	
}
