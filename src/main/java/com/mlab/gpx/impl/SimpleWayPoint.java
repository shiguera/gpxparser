package com.mlab.gpx.impl;

import java.util.List;

import com.mlab.gpx.api.AbstractWayPoint;


/**
 * Implementación simple de AbstractWayPoint
 * Representa un WayPoint con name, description, time, long, lat, alt, speed, bearing, accuracy
 * @author shiguera
 *
 */
public class SimpleWayPoint  extends AbstractWayPoint {
	

	// Constructors
	/**
	 * Basic constructor
	 */
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
			double longitude, double latitude, double altitude) {
		super(name,description,time,longitude,latitude,altitude);
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
		if(values!=null) {
			this.longitude=(values.get(0)!=null?values.get(0):0.0);
			this.latitude=(values.get(1)!=null?values.get(1):0.0);
			this.altitude=(values.get(2)!=null?values.get(2):0.0);
		}
	}
	@Override
	public double[] getValues()	{
		return new double[] {longitude, latitude, altitude};
	}

	@Override
	protected String extensionsAsGpx() {
		return "";
	}
	@Override
	protected String extensionsAsCsv() {
		return "";
	}

	/**
	 * Makes a copy of active object
	 */
	@Override
	public SimpleWayPoint clone() {
		SimpleWayPoint wp = new SimpleWayPoint();
		wp.name = this.getName();
		wp.description = this.getDescription();
		wp.time = this.getTime();
		wp.longitude = this.getLongitude();
		wp.latitude = this.getLatitude();
		wp.altitude = this.getAltitude();
		wp.tagname = this.getTag();
		return wp;
	}

}
