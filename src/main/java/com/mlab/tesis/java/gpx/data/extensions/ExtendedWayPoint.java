package com.mlab.tesis.java.gpx.data.extensions;

import com.mlab.tesis.java.gpx.data.AbstractWayPoint;
import com.mlab.tesis.java.gpx.data.GpxFactory;

public class ExtendedWayPoint extends AbstractWayPoint {

	private final String namespace = "mlab";
	private final int ACCELERATION_DECIMALS = 6;
	private final int PRESSURE_DECIMALS = 2;
	
	// Valores de las aceleraciones x,y,z y de la presión
	private double ax,ay,az,pressure;

	public ExtendedWayPoint() {
		super();
		this.ax=0.0;
		this.ay=0.0;
		this.az=0.0;
		this.pressure=0.0;
	}

	
	@Override
	public String extensionsAsGpx() {
		StringBuilder builder = new StringBuilder();
		builder.append(GpxFactory.createDoubleTag(namespace, "ax", ax, 12, ACCELERATION_DECIMALS));
		builder.append(GpxFactory.createDoubleTag(namespace, "ay", ay, 12, ACCELERATION_DECIMALS));
		builder.append(GpxFactory.createDoubleTag(namespace, "az", az, 12, ACCELERATION_DECIMALS));
		builder.append(GpxFactory.createDoubleTag(namespace, "pressure", pressure, 12, PRESSURE_DECIMALS));
		return builder.toString();
	}
	

}
