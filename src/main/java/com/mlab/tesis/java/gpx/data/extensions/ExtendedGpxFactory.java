package com.mlab.tesis.java.gpx.data.extensions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;

import com.mlab.tesis.java.gpx.Util;
import com.mlab.tesis.java.gpx.data.BGpxDocument;
import com.mlab.tesis.java.gpx.data.BGpxFactory;
import com.mlab.tesis.java.gpx.data.BSimpleGpxDocument;
import com.mlab.tesis.java.gpx.data.BWayPoint;

public class ExtendedGpxFactory extends BGpxFactory {


	public static BGpxDocument readGpxDocument(File gpxFile) {
		String cad = Util.readFileToString(gpxFile);
		BGpxDocument gpxDoc = (BSimpleGpxDocument) 
				BGpxFactory.getFactory(BGpxFactory.Type.ExtendedGpxFactory).parseGpxDocument(cad);
		if(gpxDoc==null) {
			System.out.println("Error parsing GpxDocument "+gpxFile.getName());
		}
		return gpxDoc;
	}
	
	@Override
	public BWayPoint createWayPoint(String name, String description, long time,
			List<Double> values) {
		if(!isValidSize(values)) {
			System.out.println("ExtendedGpxFactory.createWayPoint(): ERROR invalid values size "+values.size());
			for(Double d: values) {
				System.out.println(d);
			}
			return null;
		}
		return new BExtendedWayPoint(name,description,time,values.get(0),
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
	 * de las extensiones: speed, bearing, accuracy, ax,ay,az,pressure
	 */
	@Override
	public List<Double> parseWayPointExtensions(Document doc) {
		//System.out.println("ExtendedGpxFactory.parseWayPointExtension()");
		List<Double> list = new ArrayList<Double>();
		list.add(Double.valueOf(this.parseDoubleTag(doc, "mlab:speed")));
		list.add(Double.valueOf(this.parseDoubleTag(doc, "mlab:bearing")));
		list.add(Double.valueOf(this.parseDoubleTag(doc, "mlab:accuracy")));				
		list.add(Double.valueOf(this.parseDoubleTag(doc, "mlab:ax")));
		list.add(Double.valueOf(this.parseDoubleTag(doc, "mlab:ay")));
		list.add(Double.valueOf(this.parseDoubleTag(doc, "mlab:az")));
		list.add(Double.valueOf(this.parseDoubleTag(doc, "mlab:pressure")));
		
		return list;
	}
	
	@Override
	public String asCsv(BWayPoint wp) {
		return wp.asCsv(false);
	}

}
