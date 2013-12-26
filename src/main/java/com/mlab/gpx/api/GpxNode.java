package com.mlab.gpx.api;


/**
 * Interface base de los elementos Gpx. Un documento Gpx es un árbol 
 * de nodos tipo GpxNode. Cada nodo puede ser un elemento Gpx individual
 * o un elemento Composite, formado por una colección de nodos.
 * Todos los GpxNode disponen de un 'Namespace' y un 'Tagname'. Tienen además
 * un método 'asGpx()' que devuelve una cadena Gpx del elemento.
 * Además dispone de métodos add(), remove(), get() y size() para implementación
 * del patrón de diseño Composite. Estos métodos solo tendrán sentido en el caso 
 * de los elementos tipo Composite.
 * @author shiguera
 *
 */
public interface GpxNode {
	
	String getNamespace();
	void setNamespace(String namespace);
	String getTagName();
	void setTagName(String tagname);
	String asGpx();	
	boolean add(GpxNode gpxNode);
	boolean remove(GpxNode gpxNode);
	GpxNode get(int index);	
	int size();
	
}
