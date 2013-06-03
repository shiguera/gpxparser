package com.mlab.tesis.java.gpx.data.extensions;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;

import com.mlab.tesis.java.gpx.data.GpxFactory;
import com.mlab.tesis.java.gpx.data.WayPoint;

public class ExtendedGpxFactory extends GpxFactory {


	@Override
	public WayPoint createWayPoint(String name, String description, long time,
			List<Double> values) {
		if(!isValidSize(values)) {
			return null;
		}
		return new ExtendedWayPoint(name,description,time,values.get(0),
			values.get(1),values.get(2),values.get(3),values.get(4),values.get(5),
			values.get(6),values.get(7),values.get(8),values.get(9));
	}

	private boolean isValidSize(List<Double> valueslist) {
		if(valueslist.size()==10) {
			return true;
		}
		return false;
	}
	/**
	 * Método abstracto utilizado por la clase GpxFactory en su método 'parseWayPoint()'<br>
	 * Recibe el Document Gpx completo y debe extraer a una List<Double> los valores
	 * de las extensiones que añaden los ExtendedWayPoint : ax,ay,az,pressure
	 */
	@Override
	public List<Double> parseWayPointExtensions(Document doc) {
		List<Double> list = new ArrayList<Double>();
		list.add(Double.valueOf(this.parseDoubleTag(doc, "mlab:ax")));
		list.add(Double.valueOf(this.parseDoubleTag(doc, "mlab:ay")));
		list.add(Double.valueOf(this.parseDoubleTag(doc, "mlab:az")));
		list.add(Double.valueOf(this.parseDoubleTag(doc, "mlab:pressure")));		
		return list;
	}

}
