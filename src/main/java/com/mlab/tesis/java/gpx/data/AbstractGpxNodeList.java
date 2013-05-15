package com.mlab.tesis.java.gpx.data;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGpxNodeList implements GpxNodeList {

	private List<GpxNode> nodelist;
	
	protected AbstractGpxNodeList() {
		this.nodelist = new ArrayList<GpxNode>();
	}
	
	@Override
	public int size() {
		return this.nodelist.size();
	}

	@Override
	public GpxNode get(int index) {
		return this.nodelist.get(index);
	}

}
