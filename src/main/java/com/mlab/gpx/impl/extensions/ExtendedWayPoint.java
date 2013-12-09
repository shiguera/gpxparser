package com.mlab.gpx.impl.extensions;

import java.util.List;

import com.mlab.gpx.api.GpxFactory;
import com.mlab.gpx.impl.AndroidWayPoint;
import com.mlab.gpx.impl.util.Util;

/**
 * 
 * @author shiguera
 *
 */
public class ExtendedWayPoint extends AndroidWayPoint {

	protected final int ACCELERATION_DECIMALS = 6;
	protected final int PRESSURE_DECIMALS = 2;
	
	// Valores de las aceleraciones x,y,z y de la presión
	private double ax,ay,az,pressure;

	public ExtendedWayPoint() {
		super();
		this.ax=0.0;
		this.ay=0.0;
		this.az=0.0;
		this.pressure=0.0;
	}
	public ExtendedWayPoint(String name, String description, long time, 
			double longitude, double latitude, double altitude, 
			double speed, double bearing, double accuracy, double ax, double ay, double az, double pressure) {
		super(name,description,time,longitude,latitude,altitude,
				speed,bearing, accuracy);
		this.ax=ax;
		this.ay=ay;
		this.az=az;
		this.pressure=pressure;
	}
	
	public ExtendedWayPoint(String name, String descrip, long time, List<Double> values) {
		super(name, descrip, time, values);
		this.ax = 0.0;
		this.ay = 0.0;
		this.az = 0.0;
		this.pressure = 0.0;
		if(values!=null) {
			this.ax=(values.get(6)!=null?values.get(6):0.0);
			this.ay=(values.get(7)!=null?values.get(7):0.0);
			this.az=(values.get(8)!=null?values.get(8):0.0);
			this.pressure=(values.get(9)!=null?values.get(9):0.0);
		}
	}


	@Override
	public ExtendedWayPoint clone() {
		ExtendedWayPoint wp = new ExtendedWayPoint();
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
		wp.ax = this.ax;
		wp.ay = this.ay;
		wp.az = this.az;
		wp.pressure = this.pressure;
		return wp;
	}
	
	@Override
	public double[] getValues() {		
		return new double[] {this.longitude, this.latitude, this.altitude,
				this.speed, this.bearing, this.accuracy, this.ax, this.ay, this.az, this.pressure};
	}

	@Override
	public String extensionsAsGpx() {
		StringBuilder builder = new StringBuilder();
		builder.append("<extensions>");
		builder.append(GpxFactory.createDoubleTag(namespace, "speed", speed, 12, SPEED_DECIMALS));
		builder.append(GpxFactory.createDoubleTag(namespace, "bearing", bearing, 12, BEARING_DECIMALS));
		builder.append(GpxFactory.createDoubleTag(namespace, "accuracy", accuracy, 12, ACCURACY_DECIMALS));
		builder.append(GpxFactory.createDoubleTag(namespace, "ax", ax, 12, ACCELERATION_DECIMALS));
		builder.append(GpxFactory.createDoubleTag(namespace, "ay", ay, 12, ACCELERATION_DECIMALS));
		builder.append(GpxFactory.createDoubleTag(namespace, "az", az, 12, ACCELERATION_DECIMALS));
		builder.append(GpxFactory.createDoubleTag(namespace, "pressure", pressure, 12, PRESSURE_DECIMALS));
		builder.append("</extensions>");
		return builder.toString();
	}
	
	@Override
	public String asCsv(boolean withUtmCoords) {
		StringBuilder builder = new StringBuilder();
		builder.append(super.asCsv(withUtmCoords));
		//System.out.println(builder.toString());
		builder.append(",");
		builder.append(Util.doubleToString(this.ax, 12, ACCELERATION_DECIMALS));
		builder.append(",");
		builder.append(Util.doubleToString(this.ay, 12, ACCELERATION_DECIMALS));
		builder.append(",");
		builder.append(Util.doubleToString(this.az, 12, ACCELERATION_DECIMALS));
		builder.append(",");
		builder.append(Util.doubleToString(this.pressure, 12, PRESSURE_DECIMALS));
		return builder.toString();
	}

	
	public double getAx() {
		return ax;
	}

	public double getAy() {
		return ay;
	}

	public double getAz() {
		return az;
	}

	public double getPressure() {
		return pressure;
	}

	public void setAx(double ax) {
		this.ax = ax;
	}

	public void setAy(double ay) {
		this.ay = ay;
	}

	public void setAz(double az) {
		this.az = az;
	}

	public void setPressure(double pressure) {
		this.pressure = pressure;
	}

}
