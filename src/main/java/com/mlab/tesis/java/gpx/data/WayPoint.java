package com.mlab.tesis.java.gpx.data;


/**
 * WayPoint's interface<br>
 * A WayPoint has name, description, time, longitude, latitude, altitude, speed, bearing and accuracy<br>
 * It also has a field named 'tag' that allows differentiate WayPoints from RoutePoints and TrackPoints 
 * inside xml GpxDocuments
 * 
 * @author shiguera
 *
 */
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
	
		
	/**
	 * Devuelve los valores de los campos en forma de Array de doubles. 
	 * En la versión de SimpleWayPoint será:<br>
	 * [longitude, latitude, altitude, speed, bearing, accuracy]<br>
	 * Los puntos con más extensiones añadirán sus valores al double[]
	 * @return
	 */
	public  double[] getValues();
	
	/**
	 * Build a CSV String for the WayPoint. If the withUtmCoords parameter
	 * is true, then projected coordinates of the point are added to the CSV String.
	 * @param withUtmCoords
	 * @return
	 */
	public String asCsv(boolean withUtmCoords);
	

	/**
	 * Build a WayPoint cloned from calling object. Every derived class
	 * have to implement the correct clone() method.
	 * @return
	 */
	public WayPoint clone();
	
}
