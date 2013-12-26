package com.mlab.gpx.api;

public abstract class LeafGpxNode implements GpxNode {

	@Override
	public String asGpx() {
		return null;
	}

	@Override
	public boolean add(GpxNode gpxNode) {
		return false;
	}

	@Override
	public boolean remove(GpxNode gpxNode) {
		return false;
	}

	@Override
	public GpxNode get(int index) {
		return null;
	}

}
