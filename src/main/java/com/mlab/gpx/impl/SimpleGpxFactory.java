package com.mlab.gpx.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.w3c.dom.Document;

import com.mlab.gpx.api.GpxFactory;
import com.mlab.gpx.api.WayPoint;

public class SimpleGpxFactory extends GpxFactory {

	private final Logger LOG = Logger.getLogger(getClass().getName());
	
 	public SimpleGpxFactory() {
		this.factoryType = GpxFactory.Type.SimpleGpxFactory;
	}

	// Abstract method	
	/**
	 * Construye un WayPoint a partir de un name, descrip, long con el time y un 
	 * List\<Double\> con los valores {lon,lat,alt}
	 * @return Devuelve el SimpleWayPoint creado o null si hay errores
	 */	
	@Override
	public WayPoint createWayPoint(String name, String description, long time,
			List<Double> values) {
		if(values==null) {
			LOG.warning("SimpleGpxFactory.createWayPoint(): values=null");
			return null;
		}
		WayPoint wp= new SimpleWayPoint(name,description,time, values);
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
	
	
}
