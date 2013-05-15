package com.mlab.tesis.java.tserie;

import java.util.ArrayList;

/**
 * Encapsula un List de double[] <br/>
 * El primer elemento que se añade marca la dimension del vector de doubles. 
 * Los siguientes double[] que se añadan deberán de ser de la misma dimensión
 * @author shiguera
 *
 */
public class DoublesList {

	ArrayList<double[]> dlist;
	int dimension;
	
	public DoublesList() {
		dlist = new ArrayList<double[]>();
	}
	public boolean isEmpty() {
		return dlist.isEmpty();
	}
	
	/**
	 * Comprueba si un double[] se podría añadir a la lista,
	 * esto es si no nulo y length == dimension o lista vacía
	 * @param double[]
	 * @return
	 */
	public boolean canAdd(double[] values) {
		// TODO Permite añadir double[] de dimensión cero, meditar
		if(values==null) {
			return false;
		}
		if (dlist.isEmpty() || dimension==values.length) {
			return true;
		}
		return false;
	}

	public boolean add(double[] values) {
		boolean result = false;
		if(dlist.isEmpty() || values.length==dimension) {
			dimension = values.length;
			result = dlist.add(values);
		} 
		return result;
	}
	public int size() {
		return dlist.size();
	}
	/**
	 * Devuelve la dimensión de los double[] de la lista, que 
	 * queda definida por el primer double[] que se añade
	 * @return
	 */
	public int getDimension() {
		return dimension;
	}
	
	public double[] get(int index) {
		return dlist.get(index);
	}

}
