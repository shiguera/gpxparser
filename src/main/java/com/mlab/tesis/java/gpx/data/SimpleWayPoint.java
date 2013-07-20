package com.mlab.tesis.java.gpx.data;

import java.util.List;


/**
 * Implementación simple de AbstractWayPoint
 * Representa un WayPoint con name, description, time, long, lat, alt, speed, bearing, accuracy
 * @author shiguera
 *
 */
public class SimpleWayPoint  extends AbstractWayPoint {
	
	private final String namespace = "mlab";
	private final int SPEED_DECIMALS = 6;
	private final int BEARING_DECIMALS = 2;
	private final int ACCURACY_DECIMALS = 2;
	


	public SimpleWayPoint() {
		super();
	}
	/**
	 * Constructor público para un Simple WayPoint
	 * @param name
	 * @param description
	 * @param time
	 * @param longitude
	 * @param latitude
	 * @param altitude
	 * @param speed
	 * @param bearing
	 * @param accuracy
	 */
	public SimpleWayPoint(String name, String description, long time, 
			double longitude, double latitude, double altitude, 
			double speed, double bearing, double accuracy) {
		super(name,description,time,longitude,latitude,altitude,
				speed,bearing, accuracy);
	}

	/**
	 * Public constructor for SimpleWayPoint's. It takes a name, description, time and 
	 * a List of doubles that can be null or it can have any size.<br>
	 *  It is supposed that values is a double[]= {long, lat, alt, speed, bearing, accuracy}<br>
	 *  It assign elements by order. If someone isn't there, constructor assign default values. 
	 * @param name
	 * @param descrip
	 * @param time
	 * @param values
	 */
	public SimpleWayPoint(String name, String descrip, long time, List<Double> values) {
		this.name = name;
		this.description = descrip;
		this.time = time;
		this.longitude=0.0;
		this.latitude=0.0;
		this.altitude=0.0;
		this.speed=-1.0;
		this.bearing=-1.0;
		this.accuracy=-1.0;
		if(values!=null) {
			this.longitude=(values.get(0)!=null?values.get(0):0.0);
			this.latitude=(values.get(1)!=null?values.get(1):0.0);
			this.altitude=(values.get(2)!=null?values.get(2):0.0);
			this.speed=(values.get(3)!=null?values.get(3):-1.0);
			this.bearing=(values.get(4)!=null?values.get(4):-1.0);
			this.accuracy=(values.get(5)!=null?values.get(5):-1.0);			
		}
	}
	
	@Override
	public SimpleWayPoint clone() {
		SimpleWayPoint wp = new SimpleWayPoint();
		wp.name = this.getName();
		wp.description = this.getDescription();
		wp.time = this.getTime();
		wp.longitude = this.getLongitude();
		wp.latitude = this.getLatitude();
		wp.altitude = this.getAltitude();
		wp.speed = this.getSpeed();
		wp.bearing = this.getBearing();
		wp.accuracy = this.getAccuracy();
		wp.tag = this.getTag();
		return wp;
	}
	
	@Override
	public String extensionsAsGpx() {
		StringBuilder builder = new StringBuilder();
		builder.append(GpxFactory.createDoubleTag(namespace, "speed", speed, 12, SPEED_DECIMALS));
		builder.append(GpxFactory.createDoubleTag(namespace, "bearing", bearing, 12, BEARING_DECIMALS));
		builder.append(GpxFactory.createDoubleTag(namespace, "accuracy", accuracy, 12, ACCURACY_DECIMALS));
		return builder.toString();
	}

}
