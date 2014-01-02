package com.mlab.gpx.impl;

/**
 * Guarda las esquinas de un área rectangular definido en coordenadas geográficas
 * @author shiguera
 *
 */
public class GpxEnvelope {
	
	private double minLat, maxLat, minLon, maxLon;
	private boolean isFirstTime;
	
	public GpxEnvelope() {
		minLat = 0.0;
		minLon=0.0;
		maxLat=0.0;
		maxLon=0.0;
		isFirstTime = true;
	}
	public GpxEnvelope(double minlat, double minlon, double maxlat, double maxlon) {
		minLat = minlat;
		minLon = minlon;
		maxLat = maxlat;
		maxLon = maxlon;
		isFirstTime = false;
	}
	public void update(double newlat, double newlon) {
		if(isFirstTime) {
			minLat=newlat;
			minLon=newlon;
			maxLat=newlat;
			maxLon=newlon;		
			isFirstTime = false;
			return;
		}
		minLat = (newlat<minLat?newlat:minLat);
		minLon = (newlon<minLon?newlon:minLon);
		maxLat = (newlat>maxLat?newlat:maxLat);
		maxLon = (newlon>maxLon?newlon:maxLon);
	}
	public void update(GpxEnvelope newenv) {
		if(isFirstTime) {
			minLat=newenv.getMinLat();
			minLon=newenv.getMinLon();
			maxLat=newenv.getMaxLat();
			maxLon=newenv.getMaxLon();		
			isFirstTime = false;
			return;
		}
		minLat = (newenv.getMinLat()<minLat?newenv.getMinLat():minLat);
		minLon = (newenv.getMinLon()<minLon?newenv.getMinLon():minLon);
		maxLat = (newenv.getMaxLat()>maxLat?newenv.getMaxLat():maxLat);
		maxLon = (newenv.getMaxLon()>maxLon?newenv.getMaxLon():maxLon);
	}
	/**
	 * Devuelve double[]={minLat, minLon, maxLat, maxLon}
	 * @return
	 */
	public double[] getEnvelope() {
		return new double[]{minLat, minLon, maxLat, maxLon};
	}
	/**
	 * Devuelve double[]={minLat, minLon}
	 * @return
	 */
	public double[] getMinCorner() {
		return new double[]{minLat, minLon};
	}
	/**
	 * Devuelve double[]={maxLat, maxLon}
	 * @return
	 */
	public double[] getMaxCorner() {
		return new double[]{maxLat, maxLon};
	}
	public double getMinLat() {
		return minLat;
	}
	public void setMinLat(double minLat) {
		this.minLat = minLat;
	}
	public double getMaxLat() {
		return maxLat;
	}
	public void setMaxLat(double maxLat) {
		this.maxLat = maxLat;
	}
	public double getMinLon() {
		return minLon;
	}
	public void setMinLon(double minLon) {
		this.minLon = minLon;
	}
	public double getMaxLon() {
		return maxLon;
	}
	public void setMaxLon(double maxLon) {
		this.maxLon = maxLon;
	}
	public double[] getCenter() {
		return new double[] {(minLat+maxLat)/2.0, (minLon+maxLon)/2.0};
	}
	@Override
	public String toString() {
		String s = String.format("(%10.6f,%10.6f,%10.6f,%10.6f)", 
			minLat,minLon, maxLat,maxLon);
		return s;
	}

}
