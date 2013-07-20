package com.mlab.tesis.java.gpx.data;

import java.util.List;

import com.mlab.tesis.java.gpx.Util;

public class AndroidWayPoint extends BSimpleWayPoint {

	private final String namespace = "mlab";
	private final int SPEED_DECIMALS = 6;
	private final int BEARING_DECIMALS = 1;
	private final int ACCURACY_DECIMALS = 1;

	
	
	/**
	 * Velocidad del móvil medida en m/sg . Cuando no se conoce el dato
	 * se fija en '-1.0'
	 */
	protected double speed;	
	/**
	 * Rumbo seguido por el móvil medido en grados desde el Norte hacia el Este. 
	 * Cuando no se conoce el dato se 
	 * fija en '-1.0'
	 */
	protected double bearing;
	/** Precisión de las medidas de posición en metros. Cuando no se conoce
	 * se fija en '-1.0'
	 */
	protected double accuracy;


	public AndroidWayPoint() {
		super();
		this.speed = -1.0;
		this.bearing = -1.0;
		this.accuracy = -1.0;
	}

	public AndroidWayPoint(String name, String description, long time, 
			double longitude, double latitude, double altitude, 
			double speed, double bearing, double accuracy) {
		super(name,description,time,longitude,latitude,altitude);
		this.speed = speed;
		this.bearing = bearing;
		this.accuracy = accuracy;
	}
	
	public AndroidWayPoint(String name, String descrip, long time, List<Double> values) {
		super(name, descrip, time, values);
		this.speed = -1.0;
		this.bearing = -1.0;
		this.accuracy = -1.0;
		if(values!=null) {
			this.speed=(values.get(3)!=null?values.get(3):0.0);
			this.bearing=(values.get(4)!=null?values.get(4):0.0);
			this.accuracy=(values.get(5)!=null?values.get(5):0.0);
		}
	}
	
	/**
	 * Makes a copy of active object
	 */
	@Override
	public AndroidWayPoint clone() {
		AndroidWayPoint wp = new AndroidWayPoint();
		wp.name = this.getName();
		wp.description = this.getDescription();
		wp.time = this.getTime();
		wp.longitude = this.getLongitude();
		wp.latitude = this.getLatitude();
		wp.altitude = this.getAltitude();
		wp.tag = this.getTag();
		wp.speed = this.speed;
		wp.bearing = this.bearing;
		wp.accuracy = this.accuracy;
		return wp;
	}
	
	@Override
	protected String extensionsAsGpx() {
		StringBuilder builder = new StringBuilder();
		builder.append("<extensions>");
		builder.append(GpxFactory.createDoubleTag(namespace, "speed", speed, 12, SPEED_DECIMALS));
		builder.append(GpxFactory.createDoubleTag(namespace, "bearing", bearing, 12, BEARING_DECIMALS));
		builder.append(GpxFactory.createDoubleTag(namespace, "accuracy", accuracy, 12, ACCURACY_DECIMALS));
		builder.append("</extensions>");
		return builder.toString();
	}
	@Override
	protected String extensionsAsCsv() {
		StringBuilder builder = new StringBuilder();
		builder.append(Util.doubleToString(this.speed, 12, SPEED_DECIMALS));
		builder.append(",");
		builder.append(Util.doubleToString(this.bearing, 12, BEARING_DECIMALS));
		builder.append(",");
		builder.append(Util.doubleToString(this.accuracy, 12, ACCURACY_DECIMALS));
		return builder.toString();
	}

	
	public double getSpeed() {
		return speed;
	}


	public double getBearing() {
		return bearing;
	}


	public double getAccuracy() {
		return accuracy;
	}


	public void setSpeed(double speed) {
		this.speed = speed;
	}


	public void setBearing(double bearing) {
		this.bearing = bearing;
	}


	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

}
