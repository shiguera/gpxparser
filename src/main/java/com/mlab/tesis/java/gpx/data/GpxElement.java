package com.mlab.tesis.java.gpx.data;

import java.util.List;


public interface GpxElement extends GpxNode {
	
	public boolean add(GpxNode node);
	public List<GpxNode> nodes();
	public int size();

}
