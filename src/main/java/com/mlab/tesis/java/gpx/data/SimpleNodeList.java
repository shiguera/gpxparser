package com.mlab.tesis.java.gpx.data;

import java.util.ArrayList;
import java.util.List;

public class SimpleNodeList implements GpxNodeList {

	List<GpxNode> nodes;
	
	SimpleNodeList() {
		this.nodes = new ArrayList<GpxNode>();
	}
	SimpleNodeList(List<GpxNode> nodes) {
		this.nodes = new ArrayList<GpxNode>(nodes);
	}
	
	@Override
	public int size() {
		return this.nodes.size();
	}

	@Override
	public GpxNode get(int index) {
		return this.nodes.get(index);
	}

}
