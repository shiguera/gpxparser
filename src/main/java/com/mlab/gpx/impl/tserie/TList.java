package com.mlab.gpx.impl.tserie;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Es un ArrayList de tiempos Long. Cada elemento es mayor que el anterior.
 * Tiene métodos para encontrar el floor.
 * @author shiguera
 *
 */
public class TList {
	
	ArrayList<Long> tlist;
	
	public TList() {
		tlist = new ArrayList<Long>();
	}

	public int size() {
		return tlist.size();
	}

	/**
	 * Comprueba si un valor de t se podría añadir a la lista,
	 * esto es si t>0 && t>last
	 * @param t
	 * @return
	 */
	public boolean canAdd(long t) {
		//System.out.print("TList.canAdd(): t="+t);
		//System.out.println("  lastTime="+lastTime());
		if(t<0) {
			return false;
		}
		if (tlist.isEmpty() || t>this.lastTime()) {
			return true;
		}
		return false;
	}
	
	public boolean add(long t) {
		if(canAdd(t)) {
			return tlist.add(Long.valueOf(t));
		}
		return false;
	}
	
	public boolean isEmpty() {
		return this.tlist.isEmpty();
	}
	
	/**
	 * Devuelve el primer tiempo de la serie
	 * @return long first time in the ArrayList
	 * Throws IndexOutOfBoundsException if size()==0	 
	 */
	public long firstTime() {
		if(this.size()>0) {
			return (Long)this.tlist.get(0).longValue();
		}
		return -1l;
	}
	/**
	 * Devuelve el último tiempo de la serie
	 * 
	 * @return long first time in the ArrayList.
	 * Throws IndexOutOfBoundsException if size()==0
	 */
	public long lastTime() {
		if(this.size()>0) {
			return (Long)this.tlist.get(this.tlist.size()-1).longValue();
		}
		return -1l;
	}
	/**
	 * Devuelve el tiempo cuyo índice es index
	 * 
	 * @return long time in the ArrayList positon for index.
	 * Throws IndexOutOfBoundsException if index<0 || index>size()
	 */	
	public long get(int index) {
		return (Long)this.tlist.get(index).longValue();
	}
	
	/**
	 * Devuelve el índice del elemento cuyo tiempo es igual o 
	 * inmediátamanete menor que que el t pasado como parámetro.<br/>
	 * 
	 * @param t long tiempo cuyo floor buscamos
	 * @return el índice del tiempo floor o -1
	 * puede arrojar IndexOutOfBoundsException si la TList está vacía
	 */
	public int indexOfFloor(long t) {
		int index = -1;
		if(!isInRange(t)) {
			return index;
		}
		if(this.tlist.contains(Long.valueOf(t))) {
			return tlist.indexOf(Long.valueOf(t));
		}
		int minorindex = 0;
		for(ListIterator<Long> it=this.tlist.listIterator(); it.hasNext();) {
			int idx = it.nextIndex();
			if(it.next()<=t) {
				minorindex = idx;
			} else {
				index = minorindex;
				break;
			}
		}
		return index;
	}
	
	public boolean contains(long t) {
		return tlist.contains(new Long(t));
	}
	
	/**
	 * Devuelve true si el t es mayor-igual que el primero y
	 * menor-igual que el último
	 * @param t
	 * @return true si está, falso si no y 
	 * arroja IndexOutOfBoundsException si la lista está vacía
	 */
	public boolean isInRange(long t) {
		if(t>=firstTime() && t<= lastTime()) {
			return true;
		}
		return false;
	}
	public ArrayList<Long> getTlist() {
		return tlist;
	}

}
