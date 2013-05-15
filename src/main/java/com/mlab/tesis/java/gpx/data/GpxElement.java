package com.mlab.tesis.java.gpx.data;


public interface GpxElement extends GpxNode {
	
	public boolean add(GpxNode node);
	public GpxNodeList nodes();

}
