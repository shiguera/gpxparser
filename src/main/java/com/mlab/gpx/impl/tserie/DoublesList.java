package com.mlab.gpx.impl.tserie;

import java.util.ArrayList;

/**
 * Encapsula un List de double[] <br/>
 * El primer elemento que se añade marca la dimension del vector de doubles. 
 * Los siguientes double[] que se añadan deberán de ser de la misma dimensión
 * @author shiguera
 *
 */
public class DoublesList {

	/**
	 * ArrayList que da sentido a la clase
	 */
	protected ArrayList<double[]> dlist;
	/**
	 * Dimensión de los arrays de doubles que
	 * componen la lista. La dimensión queda
	 * establecida por el primer array que
	 * se añade a la lista.
	 * 
	 */
	int dimension;
	
	/**
	 * Crea una instancia de DoublesList vacía 
	 */
	public DoublesList() {
		dlist = new ArrayList<double[]>();
		dimension = 0;
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

	/**
	 * Añade un array de doubles a la lista. 
	 * El primer elemento de la lista establece la dimensión
	 * que tendrán que tener los subsiguientes arrays que se añadan
	 * 
	 * @param values
	 * @return
	 */
	public boolean add(double[] values) {
		boolean result = false;
		if(dlist.isEmpty() || values.length==dimension) {
			dimension = values.length;
			result = dlist.add(values);
		} 
		return result;
	}
	/**
	 * Devuelve el número de elementos en la lista. Cada elemento
	 * consta de un array de doubles
	 * @return
	 */
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
	
	/**
	 * Devuelve el array situado en la posición
	 * indicada por index
	 * 
	 * @param index
	 * @return
	 */
	public double[] get(int index) {
		return dlist.get(index);
	}
	public void empty() {
		dlist.clear();
	}
}
