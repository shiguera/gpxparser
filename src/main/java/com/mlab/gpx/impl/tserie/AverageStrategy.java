package com.mlab.gpx.impl.tserie;

public class AverageStrategy implements StrategyOnValues {

	private long halfInterval;
	
	public AverageStrategy(long halfinterval) {
		halfInterval = halfinterval;
	}

	@Override
	public double[] getValues(TSerie tserie, long t) {		
		if (halfInterval < 0l || !tserie.isInRange(t)) {
			return null;
		}
		long inf = t - halfInterval;
		if (!tserie.isInRange(inf)) {
			inf = 0;
		}
		long sup = t + halfInterval;
		if (!tserie.isInRange(sup)) {
			sup = tserie.getTime(tserie.size()-1);
		}
		int floor = tserie.indexOfFloor(inf);
		// floor es el primer indice del tiempo menor o igual
		// si es menor tomamos el siguiente, que será mayor
		// y estará dentro del intervalo solicitado
		if(tserie.getTime(floor)<inf) {
			floor ++;
		}
		int ceiling = tserie.indexOfCeiling(sup);
		// idem que con floor pero por arriba
		if (tserie.getTime(ceiling) > sup) {
			ceiling --;
		}
		// Ahora comprobamos que siguen siendo válidos
		if(floor>ceiling || !tserie.isInRange(floor) || 
				!tserie.isInRange(ceiling) || tserie.getDimension()<=0) {
			return null;
		}
		int dimension = tserie.getDimension();
		// Inicializar a cero para calcular media
		double[] values = new double[dimension];
		for(int j=0; j<dimension; j++ ) {
			values[j] = 0.0;
		}
		// sumar valores en rango
		for(int i = floor; i<= ceiling; i++) {
			for(int j=0; j<dimension; j++ ) {
				values[j] = values[j] + tserie.getValues(i)[j];
			}
		}
		// calcular valores medios
		for(int j=0; j<dimension; j++ ) {
			values[j] = values[j] / (double)(ceiling-floor+1);
		}
		return values;
	}
}
