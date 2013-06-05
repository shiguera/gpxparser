package com.mlab.tesis.srs;

public class EllipsoidWGS84 extends Ellipsoid {

	private final String NAME = "WGS84";
	private final double a = 6378137.0;
	private final double b = 6356752.3142;
	private final double e = Math.sqrt(a*a-b*b)/a;
	private final double eprim = Math.sqrt(a*a-b*b)/b;
	private final double flattening = (a-b)/a;
	private final double c = a*a / b;
	private final double inverseFlattening = 1 / flattening;
	@Override
	double getEccentricity() {
		return e;
	}

	@Override
	double getSecondEccentricity() {
		return eprim;
	}
	
	@Override
	double getSemiMajorAxis() {
		return a;
	}

	@Override
	double getSemiMinorAxis() {
		return b;
	}

	@Override
	double getFlattening() {
		return flattening;
	}

	@Override
	String getName() {
		return NAME;
	}

	@Override
	double getInverseFlattening() {
		return inverseFlattening;
	}
}
