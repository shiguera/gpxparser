package com.mlab.tesis.java.gpx.data;


/**
 * Clase base de los elementos Gpx. Estos elementos disponen
 * de un método 'asGpx()' que devuelve una cadena Gpx del elemento y
 * un método estático parseGpx() que crea una instancia del elemento
 * a partir de la cadena gpx del mismo
 * @author shiguera
 *
 */
public abstract class GpxElement {

	public abstract String asGpx();
	
	public static GpxElement parseGpxString(String cadgpx) {
		return null;
	}
	
}
