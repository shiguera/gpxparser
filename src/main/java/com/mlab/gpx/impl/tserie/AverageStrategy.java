package com.mlab.gpx.impl.tserie;

public class AverageStrategy implements StrategyOnValues {

	private TSerie tserie;
	private long halfInterval;
	
	public AverageStrategy(TSerie serie, long halfinterval) {
		tserie = serie;
		halfInterval = halfinterval;
	}

	@Override
	public double[] getValues(long t) {
		long inf = t - halfInterval;
		long sup = t + halfInterval;
		
		return null;
	}

}
