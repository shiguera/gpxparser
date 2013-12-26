package com.mlab.gpx.api;

import java.util.List;

/**
 * Interface for GpxElements that are composite elements
 * @author shiguera
 *
 */
public interface GpxElement extends GpxNode {
	
	public List<GpxNode> nodes();
	public int size();

}
