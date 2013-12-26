package com.mlab.gpx.api;


/**
 * Interface base de los elementos Gpx. Estos elementos disponen
 * de un método 'asGpx()' que devuelve una cadena Gpx del elemento 
 * @author shiguera
 *
 */
public interface GpxNode {
	
	String asGpx();	
	boolean add(GpxNode gpxNode);
	boolean remove(GpxNode gpxNode);
	GpxNode get(int index);	
	
}
