package com.mlab.gpx.test.impl.tserie;

import junit.framework.Assert;

import org.junit.Test;

import com.mlab.gpx.impl.tserie.AverageStrategy;
import com.mlab.gpx.impl.tserie.TSerie;

public class TestAverageStrategy {

	@Test
	public void test() {
		System.out.println("TestAverageStrategy.test()");
		TSerie serie = new TSerie();
		serie.add(new Long(0l), new double[] {1.0,1.0});
		serie.add(new Long(100l), new double[] {1.0,1.0});
		serie.add(new Long(200l), new double[] {2.0,4.0});
		serie.add(new Long(300l), new double[] {3.0,9.0});
		serie.add(new Long(400l), new double[] {4.0,16.0});
		
		AverageStrategy strategy = new AverageStrategy(100l);
		double[] values = serie.getValues(200l, strategy);
		Assert.assertNotNull(values);
		Assert.assertEquals(2.0, values[0], 0.01);
		Assert.assertEquals(4.66, values[1], 0.01);
		
		strategy = new AverageStrategy(50l);
		values = serie.getValues(200l, strategy);
		Assert.assertNotNull(values);
		Assert.assertEquals(2.0, values[0], 0.01);
		Assert.assertEquals(4.0, values[1], 0.01);
		
		strategy = new AverageStrategy(200l);
		values = serie.getValues(100l, strategy);
		Assert.assertNotNull(values);
		Assert.assertEquals(1.75, values[0], 0.01);
		Assert.assertEquals(3.75, values[1], 0.01);
		
		strategy = new AverageStrategy(200l);
		values = serie.getValues(400l, strategy);
		Assert.assertNotNull(values);
		Assert.assertEquals(3.0, values[0], 0.01);
		Assert.assertEquals(9.66, values[1], 0.01);
		
		
		
		
		
	}

}
