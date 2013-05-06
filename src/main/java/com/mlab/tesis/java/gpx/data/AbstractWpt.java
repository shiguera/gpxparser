package com.mlab.tesis.java.gpx.data;


public abstract class AbstractWpt extends GpxElement {
	
	/**
	 * nombre asignado al punto
	 */
	public abstract String getName();
	public abstract void setName(String name);
	
	/**
	 * Descripción del punto
	 */
	public abstract String getDescription();
	public abstract void setDescription(String description);

	/**
	 * Tiempo en milisegundos desde 1 Enero 1970 en horario UTC. 
	 * Cuando no se dispone del dato
	 * se fija el valor en '0L'
	 */
	public abstract long getTime();
	public abstract void setTime(long time);

	/**
	 * Longitud del punto medida en grados entre -180.0 y 180.0 en el
	 * sistema de referencia WGS84 
	 */
	public abstract double getLongitude();
	public abstract void setLongitude(double longitude);

	/**
	 * Latitud del punto, medida en grados entre -90.0 y 90.0 en el 
	 * sistema de referencia WGS84 
	 */
	public abstract double getLatitude();
	public abstract void setLatitude(double latitude);

	/**
	 * Altitud del punto. Cuando no se conoce se fija el valor en '-1.0'
	 */
	public abstract double getAltitude();	
	public abstract void setAltitude(double altitude);

	/**
	 * Velocidad del móvil medida en m/sg . Cuando no se conoce el dato
	 * se fija en '-1.0'
	 */
	public abstract double getSpeed() ;
	public abstract void setSpeed(double speed);

	/**
	 * Rumbo seguido por el móvil medido en grados desde el Norte hacia el Este. 
	 * Cuando no se conoce el dato se 
	 * fija en '-1.0'
	 */
	public abstract double getBearing();
	public abstract void setBearing(double bearing);
	
	/** Precisión de las medidas de posición en metros. Cuando no se conoce
	 * se fija en '-1.0'
	 */
	public abstract double getAccuracy();
	public abstract void setAccuracy(double accuracy);
	
	/**	
	 * Este campo se utiliza para diferenciar en la salida gpx los waypoint aislados del
	 * documento gpx, que llevan un marcado <pre> {@code '<wpt>...</wpt> } </pre> de los waypoint cuando están 
	 * dentro de una Route o un TrackSegment. En esos casos el marcado es 
	 * <pre> {@code <rtept> y <trkpt> } </pre>
	 * respectivamente.
	 */
	public abstract String getTAG();
	public abstract void setTAG(String tAG);	
	
		
	

	
	
}
