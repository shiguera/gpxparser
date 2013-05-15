package com.mlab.tesis.java.gpx.data;

public abstract class AbstractExtensions implements Extensions {

	@Override
	public String asGpx() {
		return "<extensions></extensions>";
	}

}
