package com.mlab.tesis.java.gpx.data;


public interface WayPoint extends GpxNode {
	
	/**
	 * nombre asignado al punto
	 */
	public  String getName();
	
	/**
	 * Descripción del punto
	 */
	public  String getDescription();

	/**
	 * Tiempo en milisegundos desde 1 Enero 1970 en horario UTC. 
	 * Cuando no se dispone del dato
	 * se fija el valor en '0L'
	 */
	public  long getTime();

	/**
	 * Longitud del punto medida en grados entre -180.0 y 180.0 en el
	 * sistema de referencia WGS84 
	 */
	public  double getLongitude();

	/**
	 * Latitud del punto, medida en grados entre -90.0 y 90.0 en el 
	 * sistema de referencia WGS84 
	 */
	public  double getLatitude();

	/**
	 * Altitud del punto. Cuando no se conoce se fija el valor en '-1.0'
	 */
	public  double getAltitude();	

	/**
	 * Velocidad del móvil medida en m/sg . Cuando no se conoce el dato
	 * se fija en '-1.0'
	 */
	public  double getSpeed() ;

	/**
	 * Rumbo seguido por el móvil medido en grados desde el Norte hacia el Este. 
	 * Cuando no se conoce el dato se 
	 * fija en '-1.0'
	 */
	public  double getBearing();
	
	/** Precisión de las medidas de posición en metros. Cuando no se conoce
	 * se fija en '-1.0'
	 */
	public  double getAccuracy();
	
	/**	
	 * Este campo se utiliza para diferenciar en la salida gpx los waypoint aislados del
	 * documento gpx, que llevan un marcado <pre> {@code '<wpt>...</wpt> } </pre> de los waypoint cuando están 
	 * dentro de una Route o un TrackSegment. En esos casos el marcado es 
	 * <pre> {@code <rtept> y <trkpt> } </pre>
	 * respectivamente.
	 */
	public  String getTag();
	public void setTag(String tag); 
	
		
	public  double[] getValues();
	
}
