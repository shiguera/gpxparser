package com.mlab.tesis.java.gpx.data;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;

public class SimpleGpxFactory extends GpxFactory {

	
 	protected SimpleGpxFactory() {
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
		if(values==null || values.size() != 6) {
			return null;
		}
		SimpleWayPoint wp= new SimpleWayPoint(name,description,time, values);
		return wp;
	}

	@Override
	public List<Double> parseWayPointExtensions(Document doc) {
		return new ArrayList<Double>();
	}


}
