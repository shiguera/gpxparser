package com.mlab.gpx.impl.extensions;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.w3c.dom.Document;

import com.mlab.gpx.api.GpxFactory;
import com.mlab.gpx.api.WayPoint;

public class ClinometerGpxFactory extends GpxFactory {

 	public ClinometerGpxFactory() {
		this.factoryType = GpxFactory.Type.ClinometerGpxFactory;
	}

	private final Logger LOG = Logger.getLogger(getClass().getName());
		
	/**
	 * Método abstracto utilizado por la clase GpxFactory en su método 'parseWayPoint()'<br>
	 * 
	 * @return Devuelve  un ClinometerWayPoint o null
	 */
	@Override
	public WayPoint createWayPoint(String name, String description, long time,
			List<Double> values) {
		if(values==null) {
			//LOG.warning("ExtendedGpxFactory.createWayPoint(): values=null");
			return null;
		}
		WayPoint wp= new ClinometerWayPoint(name,description,time, values);
		return wp;	
	}

	/**
	 * Método abstracto utilizado por la clase GpxFactory en su método 'parseWayPoint()'<br>
	 * Recibe el Document Gpx completo y debe extraer a una List<Double> los valores
	 * de las extensiones: speed, bearing, accuracy, escora, cabeceo, guinada
	 */
	@Override
	public List<Double> parseWayPointExtensions(Document doc) {
		//System.out.println("ExtendedGpxFactory.parseWayPointExtension()");
		List<Double> list = new ArrayList<Double>();
		list.add(Double.valueOf(this.parseDoubleTag(doc, "mlab:speed")));
		list.add(Double.valueOf(this.parseDoubleTag(doc, "mlab:bearing")));
		list.add(Double.valueOf(this.parseDoubleTag(doc, "mlab:accuracy")));				
		list.add(Double.valueOf(this.parseDoubleTag(doc, "mlab:escora")));
		list.add(Double.valueOf(this.parseDoubleTag(doc, "mlab:cabeceo")));
		list.add(Double.valueOf(this.parseDoubleTag(doc, "mlab:guinada")));		
		return list;
	}
	

}
