package com.mlab.tesis.java.tserie;

import java.util.ArrayList;

/**
 * Representa una serie temporal de datos. Cada fila de datos consta de un tiempo en
 * milisegundos y un array de doubles que se corresponde con los datos para ese tiempo.
 * Dispone de un método 'values(long tt)' que devuelve el array de valores correspondientes
 * a un tiempo t. Se interpolan los valores entre los tiempos anterior
 * y posterior al solicitado.
 * @author shiguera
 *
 */
public class TSerie {
	
	/**
	 * Serie de tiempos en milisegundos. A cada tiempo le correspondera 
	 * un array de doubles en la serie de datos
	 */
	protected ArrayList<Long> t;
	
	/**
	 * Serie de datos en correspondencia con la serie de tiempos.
	 */
	protected ArrayList<double[]> _values;
	
	/**
	 * Número de datos de cada fila de datos de la serie. Es la dimension 
	 * del array de doubles que constituye cada fila de datos.
	 */
	protected int dimension;
	
	/**
	 * Constructor. Inicializa los ArrayList de los tiempos y los valores
	 * @param dimension Número de datos en cada fila de datos de un tiempo
	 */
	public TSerie(int dimension) {
		this.t= new ArrayList<Long>();
		this._values= new ArrayList<double[]>();
		this.dimension=dimension;
	}
	/**
	 * Añade un tiempo y una fila de datos al final de la serie. Si el tiempo no es mayor
	 * que el último de la serie no añade los datos y devuelve -1
	 * @param tt Tiempo en milisegundos de la nueva fila de datos
	 * @param val Array de doubles con los datos
	 * @return índice en la serie de la fila añadida o -1 si hay error
	 */
	public int add(long tt, double[] val) {
		int index=-1;
		int size=this.size();
		// Si la serie esta vacia añade directamente
		if(size==0) {
			this.t.add(Long.valueOf(tt));
			this._values.add(val);
			index=this.size()-1;
			return index;
		}
		// Comprueba que el tiempo es mayor que el ultimo de la serie
		if(tt>this.t.get(size-1)) {
			this.t.add(Long.valueOf(tt));
			this._values.add(val);
			index=this.size()-1;
		}
		return index;
	}

	/**
	 * Devuelve el número de tiempos o filas de datos de la serie
	 * @return Número de filas de datos de la serie
	 */
	public int size() {
		return this.t.size();
	}
	
	public long time(int index) {
		return this.t.get(index).longValue();
	}
	
	/**
	 * Devuelve el array de doubles de la posición de index
	 * @param index posición en la TSerie de los valores solicitados
	 * @return double[] valores asignados a esa posición de la TSerie
	 */
	public double[] values(int index) {
		return this._values.get(index);
	}
	
	/**
	 * Devuelve un array de doubles con los datos que corresponden a ese t.
	 * Los valores se interpolan entre los dos tiempos más cercanos.  
	 * @param tt Tiempo en milisegundos del dato que se solicita
	 * @return Array de doubles con los valores de los datos interpolados
	 * entre los dos tiempos anterior y siguiente. Si el tiempo solicitado
	 * es mayor o menor que los extremos de la serie se devuelve null.
	 */
	public double[] values(long tt) {
		double[] result= new double[dimension];
		int ianterior=-1;
		int isig=-1;
		int last=this.t.size()-1;
		// Comprobar que la serie temporal tiene datos
		if(this.size()==0) {
			return null;
		}
		// Comprobar que tt está en el intervalo de la serie
		if(tt<this.t.get(0).longValue() || tt>this.t.get(last).longValue()) {
			return null;
		}
		
		// Buscar índice anterior
		for(int i=0; i<=last; i++) {
			ianterior=i;
			isig=i+1;
			// Comprobar si coincide con el anterior
			if(tt==t.get(ianterior).longValue()) {
				return this._values.get(ianterior);
			}
			// Comprobar si el siguiente es mayor o igual
			if(tt<this.t.get(isig).longValue()) {
				// Devolver interpolación
				for(int j=0; j<this.dimension; j++) {
					double num=new Long(tt-this.t.get(ianterior).longValue()).doubleValue();
					double denom=new Long(this.t.get(isig).longValue()-this.t.get(ianterior).longValue()).doubleValue();
					double prop=num/denom;
					double incv=this._values.get(isig)[j]-this._values.get(i)[j];
					result[j]= this._values.get(i)[j] + prop * incv;
				}
				return result;
			}	
		}
		return result;
	}
	
	public int getDimension() {
		return dimension;
	}

}
