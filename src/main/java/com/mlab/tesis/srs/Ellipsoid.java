package com.mlab.tesis.srs;

public abstract class Ellipsoid {
	
	abstract String getName();
	abstract double getEccentricity();
	abstract double getSemiMajorAxis();
	abstract double getSemiMinorAxis();
	abstract double getSecondEccentricity();
	abstract double getFlattening();
	abstract double getInverseFlattening();
	
	/**
	 * Obtiene la proyección UTM de un punto dado por sus coordenadas longitud, latitud
	 * @param longitude 
	 * @param latitude
	 * @return double[] con la xutm y la yutm
	 */
	public double[] proyUTM(double longitude, double latitude) {
		double lonrad = longitude * Math.PI / 180.0;
		//System.out.println("lonrad = "+lonrad);
		double latrad = latitude * Math.PI / 180.0;
		//System.out.println("latrad = "+latrad);
		
		double lambda0 = (getUTMZone(longitude)*6.0-183.0) * Math.PI / 180.0;
		//System.out.println("lambda0 = "+lambda0);
		
		double inclambda = lonrad - lambda0;
		//System.out.println("inclambda = "+inclambda);
		
		double A = Math.cos(latrad)*Math.sin(inclambda);
		//System.out.println("A = "+A);
				
		double xi = 0.5 * Math.log((1+A)/(1-A)); 
		//System.out.println("xi = "+xi);
		
		double eta = Math.atan(Math.tan(latrad)/Math.cos(inclambda)) - latrad;
		//System.out.println("eta = "+eta);

		double c = getSemiMajorAxis()*getSemiMajorAxis()/getSemiMinorAxis();
		double eprim2 = getSecondEccentricity()*getSecondEccentricity();
		double cosfi2 = Math.cos(latrad)*Math.cos(latrad);
		double ni = c / Math.sqrt(1+eprim2*cosfi2)*0.9996; 
		//System.out.println("ni = "+ni);
		
		double dseta = eprim2 * xi*xi * cosfi2 / 2.0;
		//System.out.println("dseta = "+dseta);
		
		double A1 = Math.sin(2* latrad);
		double A2 = A1 * cosfi2;
		double J2 = latrad + A1/2;
		double J4 = (3.0*J2 + A2)/4.0;
		double J6 = (5.0*J4+A2*cosfi2)/3.0;
		double alfa = 0.75 * eprim2;
		double beta = 5.0/3.0*alfa*alfa;
		double ganma = 35.0/27.0*alfa*alfa*alfa;
		double Bfi = 0.9996*c*(latrad-alfa*J2+beta*J4-ganma*J6);
		double x = xi * ni * (1+dseta/3.0) + 500000;
		double y = eta * ni * (1+dseta)+Bfi;
		if (latrad<0) {
			y += 10000000.0;
		}
		
		double[] xy = new double[] {x,y};
		return xy;
	}
	
	public static double getUTMZone(double longitude) {
		return Math.floor(longitude/6.0 + 31.0);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getName());
		String cad = String.format("  a = %12.4f  ", getSemiMajorAxis());
		builder.append(cad);
		cad = String.format("  b = %12.4f", getSemiMinorAxis());
		builder.append(cad);
		cad = String.format("  1/f = %8.4f", getInverseFlattening());
		builder.append(cad);		
		return builder.toString();
	}
	
}
