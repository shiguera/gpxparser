package com.mlab.gpx.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.mlab.gpx.api.GpxFactory;

public class SimpleGpxFactory extends GpxFactory {

	private final Logger LOG = Logger.getLogger(getClass().getName());
	
 	public SimpleGpxFactory() {
		this.factoryType = GpxFactory.Type.SimpleGpxFactory;
	}

	// Abstract method	
	/**
	 * Construye un WayPoint a partir de un name, descrip, long con el time y un 
	 * List\<Double\> con los valores {lon,lat,alt,speed,bearing,accuracy}
	 * @return Devuelve el SimpleWayPoint creado o null si hay errores
	 */	
	@Override
	public SimpleWayPoint createWayPoint(String name, String description, long time,
			List<Double> values) {
		if(values==null) {
			LOG.warning("SimpleGpxFactory.createWayPoint(): values=null");
			return null;
		}
		SimpleWayPoint wp= new SimpleWayPoint(name,description,time, values);
		return wp;
	}

	/**
	 * This method is abstract in the abstract base class GpxFactory.
	 * It's used in the method parseGpxPoint as factory method
	 */
	@Override
	public List<Double> parseWayPointExtensions(Document doc) {
		//System.out.println("SimpleGpxFactory.parseWayPointExtensions()");
		List<Double> res = new ArrayList<Double>();
		return res;
	}
	
	private double parseSpeed(Document doc) {
		double speed = -1.0;
		NodeList ll = doc.getElementsByTagName("mlab:speed");
		if(ll.getLength()>0) {
			try {
				speed = Double.parseDouble(ll.item(0).getTextContent());				
			} catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return speed;
	}
	private double parseBearing(Document doc) {
		double bearing = -1.0;
		NodeList ll = doc.getElementsByTagName("mlab:bearing");
		if(ll.getLength()>0) {
			try {
				bearing = Double.parseDouble(ll.item(0).getTextContent());				
			} catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return bearing;
	}
	private double parseAccuracy(Document doc) {
		double acc = -1.0;
		NodeList ll = doc.getElementsByTagName("mlab:accuracy");
		if(ll.getLength()>0) {
			try {
				acc = Double.parseDouble(ll.item(0).getTextContent());				
			} catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return acc;
	}

}
