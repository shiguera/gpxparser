package com.mlab.gpx.impl.extensions;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.w3c.dom.Document;

import com.mlab.gpx.api.GpxFactory;
import com.mlab.gpx.api.WayPoint;

public class ExtendedGpxFactory extends GpxFactory {

	private final Logger LOG = Logger.getLogger(getClass().getName());
	
 	public ExtendedGpxFactory() {
		this.factoryType = GpxFactory.Type.ExtendedGpxFactory;
	}

//	public static GpxDocument readGpxDocument(File gpxFile) {
//		String cad = Util.readFileToString(gpxFile);
//		GpxDocument gpxDoc = (GpxDocumentImpl) 
//				GpxFactory.getFactory(GpxFactory.Type.ExtendedGpxFactory).parseGpxDocument(cad);
//		if(gpxDoc==null) {
//			System.out.println("Error parsing GpxDocument "+gpxFile.getName());
//		}
//		return gpxDoc;
//	}
	
	/**
	 * Método abstracto utilizado por la clase GpxFactory en su método 'parseWayPoint()'<br>
	 * 
	 * @return Devuelve  un ExtendedWayPoint o null
	 */
	@Override
	public WayPoint createWayPoint(String name, String description, long time,
			List<Double> values) {
		if(values==null) {
			//LOG.warning("ExtendedGpxFactory.createWayPoint(): values=null");
			return null;
		}
		WayPoint wp= new ExtendedWayPoint(name,description,time, values);
		return wp;	
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
	
//	@Override
//	public String asCsv(WayPoint wp) {
//		return wp.asCsv(false);
//	}

}
