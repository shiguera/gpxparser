package com.mlab.tesis.java.gpx.data;

import java.util.List;

/**
 * Elemento Composite para elementos GpxNode
 * @author shiguera
 *
 */
public interface GpxElement extends GpxNode {
	
	public boolean add(GpxNode node);
	public List<GpxNode> nodes();
	public int size();

}
