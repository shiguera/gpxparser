package com.mlab.tesis.java.gpx.data.extensions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;

import com.mlab.tesis.java.gpx.data.GpxDocument;
import com.mlab.tesis.java.gpx.data.GpxFactory;
import com.mlab.tesis.java.gpx.data.SimpleGpxDocument;
import com.mlab.tesis.java.gpx.data.Util;
import com.mlab.tesis.java.gpx.data.WayPoint;

public class ExtendedGpxFactory extends GpxFactory {


	public static GpxDocument readGpxDocument(File gpxFile) {
		String cad = Util.readFileToString(gpxFile);
		GpxDocument gpxDoc = (SimpleGpxDocument) 
				GpxFactory.getFactory(GpxFactory.Type.ExtendedGpxFactory).parseGpxDocument(cad);
		if(gpxDoc==null) {
			System.out.println("Error parsing GpxDocument "+gpxFile.getName());
		}
		return gpxDoc;
	}
	
	@Override
	public WayPoint createWayPoint(String name, String description, long time,
			List<Double> values) {
		if(!isValidSize(values)) {
			System.out.println("ExtendedGpxFactory.createWayPoint(): ERROR invalid values size "+values.size());
			for(Double d: values) {
				System.out.println(d);
			}
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
	public String asCsv(WayPoint wp) {
		StringBuilder builder = new StringBuilder();
		builder.append(super.asCsv(wp));
		builder.append(Util.doubleToString(((ExtendedWayPoint)wp).getAx(), 6, 3));
		builder.append(",");				
		builder.append(Util.doubleToString(((ExtendedWayPoint)wp).getAy(), 6, 3));
		builder.append(",");				
		builder.append(Util.doubleToString(((ExtendedWayPoint)wp).getAz(), 6, 3));
		builder.append(",");				
		builder.append(Util.doubleToString(((ExtendedWayPoint)wp).getPressure(), 8, 3));
		return builder.toString();
	}

}
