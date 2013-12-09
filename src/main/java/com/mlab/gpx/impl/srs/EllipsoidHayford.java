package com.mlab.gpx.impl.srs;

public class EllipsoidHayford extends Ellipsoid {

	private final String NAME = "Hayford";
	private final double a = 6378388.0;
	private final double b = 6356911.946130;
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
