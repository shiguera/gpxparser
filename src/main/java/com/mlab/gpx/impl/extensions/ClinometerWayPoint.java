package com.mlab.gpx.impl.extensions;

import java.util.List;

import com.mlab.gpx.impl.AndroidWayPoint;
import com.mlab.gpx.impl.util.Util;
import com.mlab.gpx.impl.util.XmlFactory;

/**
 * 
 * @author shiguera
 *
 */
public class ClinometerWayPoint extends AndroidWayPoint {

	protected final int ANGLE_DECIMALS = 6;
	
	// Valores de las aceleraciones x,y,z y de la presión
	private double escora, cabeceo, guinada;

	public ClinometerWayPoint() {
		super();
		this.escora=0.0;
		this.cabeceo=0.0;
		this.guinada=0.0;
	}
	public ClinometerWayPoint(String name, String description, long time, 
			double longitude, double latitude, double altitude, 
			double speed, double bearing, double accuracy, double escora, double cabeceo, double guinada) {
		super(name,description,time,longitude,latitude,altitude,
				speed,bearing, accuracy);
		this.escora=escora;
		this.cabeceo=cabeceo;
		this.guinada=guinada;
	}
	
	public ClinometerWayPoint(String name, String descrip, long time, List<Double> values) {
		super(name, descrip, time, values);
		this.escora=0.0;
		this.cabeceo=0.0;
		this.guinada=0.0;
		if(values!=null && values.size()>=9) {
			this.escora=(values.get(6)!=null?values.get(6):0.0);
			this.cabeceo=(values.get(7)!=null?values.get(7):0.0);
			this.guinada=(values.get(8)!=null?values.get(8):0.0);
		}
	}


	@Override
	public ClinometerWayPoint clone() {
		ClinometerWayPoint wp = new ClinometerWayPoint();
		wp.name = this.getName();
		wp.description = this.getDescription();
		wp.time = this.getTime();
		wp.longitude = this.getLongitude();
		wp.latitude = this.getLatitude();
		wp.altitude = this.getAltitude();
		wp.speed = this.getSpeed();
		wp.bearing = this.getBearing();
		wp.accuracy = this.getAccuracy();
		wp.tagname = this.getTag();
		wp.escora = this.escora;
		wp.cabeceo = this.cabeceo;
		wp.guinada = this.guinada;
		return wp;
	}
	
	@Override
	public double[] getValues() {		
		return new double[] {this.longitude, this.latitude, this.altitude,
				this.speed, this.bearing, this.accuracy, this.escora, this.cabeceo, this.guinada};
	}

	@Override
	public String extensionsAsGpx() {
		StringBuilder builder = new StringBuilder();
		builder.append("<extensions>");
		builder.append(XmlFactory.createDoubleTag(namespace, "speed", speed, 12, SPEED_DECIMALS));
		builder.append(XmlFactory.createDoubleTag(namespace, "bearing", bearing, 12, BEARING_DECIMALS));
		builder.append(XmlFactory.createDoubleTag(namespace, "accuracy", accuracy, 12, ACCURACY_DECIMALS));
		builder.append(XmlFactory.createDoubleTag(namespace, "escora", escora, 12, ANGLE_DECIMALS));
		builder.append(XmlFactory.createDoubleTag(namespace, "cabeceo", cabeceo, 12, ANGLE_DECIMALS));
		builder.append(XmlFactory.createDoubleTag(namespace, "guinada", guinada, 12, ANGLE_DECIMALS));
		builder.append("</extensions>");
		return builder.toString();
	}
	
	@Override
	public String asCsv(boolean withUtmCoords) {
		StringBuilder builder = new StringBuilder();
		builder.append(super.asCsv(withUtmCoords));
		//System.out.println(builder.toString());
		builder.append(",");
		builder.append(Util.doubleToString(this.escora, 12, ANGLE_DECIMALS));
		builder.append(",");
		builder.append(Util.doubleToString(this.cabeceo, 12, ANGLE_DECIMALS));
		builder.append(",");
		builder.append(Util.doubleToString(this.guinada, 12, ANGLE_DECIMALS));
		return builder.toString();
	}
	public double getEscora() {
		return escora;
	}
	public void setEscora(double escora) {
		this.escora = escora;
	}
	public double getCabeceo() {
		return cabeceo;
	}
	public void setCabeceo(double cabeceo) {
		this.cabeceo = cabeceo;
	}
	public double getGuinada() {
		return guinada;
	}
	public void setGuinada(double guinada) {
		this.guinada = guinada;
	}
	public int getANGLE_DECIMALS() {
		return ANGLE_DECIMALS;
	}

	
}
