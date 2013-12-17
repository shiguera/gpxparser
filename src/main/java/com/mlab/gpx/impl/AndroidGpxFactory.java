package com.mlab.gpx.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.w3c.dom.Document;

import com.mlab.gpx.api.GpxFactory;
import com.mlab.gpx.api.WayPoint;

public class AndroidGpxFactory extends GpxFactory {
	
	private final Logger LOG = Logger.getLogger(getClass().getName());
	
 	public AndroidGpxFactory() {
		this.factoryType = GpxFactory.Type.AndroidGpxFactory;
	}

 	/**
	 * Método abstracto utilizado por la clase GpxFactory en su método 'parseWayPoint()'<br>
	 * 
	 * @return Devuelve un AndroidWayPoint o null
	 */
	@Override
	public WayPoint createWayPoint(String name, String description, long time,
			List<Double> values) {
		if(values==null) {
			LOG.warning("AndroidGpxFactory.createWayPoint(): values=null");
			return null;
		}
		WayPoint wp= new AndroidWayPoint(name,description,time, values);
		return wp;	
	}

	/**
	 * Método abstracto utilizado por la clase GpxFactory en su método 'parseWayPoint()'<br>
	 * Recibe el Document Gpx completo y debe extraer a una List<Double> los valores
	 * de las extensiones: speed, bearing, accuracy
	 */
	@Override
	public List<Double> parseWayPointExtensions(Document doc) {
		//System.out.println("AndroidGpxFactory.parseWayPointExtension()");
		List<Double> list = new ArrayList<Double>();
		list.add(Double.valueOf(parseDoubleTag(doc, "mlab:speed")));
		list.add(Double.valueOf(parseDoubleTag(doc, "mlab:bearing")));
		list.add(Double.valueOf(parseDoubleTag(doc, "mlab:accuracy")));				
		return list;
	}
	

}
