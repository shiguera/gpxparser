package com.mlab.gpx.api;

import java.util.ArrayList;
import java.util.List;

import com.mlab.gpx.impl.util.XmlFactory;

/**
 * Interface for GpxElements that are composite elements
 * @author shiguera
 * 
 */
public abstract class CompositeGpxNode extends AbstractGpxNode {
	
	protected List<GpxNode> nodes;
	
	public CompositeGpxNode() {
		this.nodes = new ArrayList<GpxNode>();
	}
	
	@Override
	public GpxNode get(int index) {
		return (GpxNode)this.nodes.get(index);
	}
	@Override 
	public boolean add(GpxNode gpxnode) {
		return this.nodes.add(gpxnode);
	}
	@Override
	public boolean remove(GpxNode gpxnode) {
		return this.nodes.remove(gpxnode);
	}

	@Override
	public int size() {
		return this.nodes.size();
	}
	@Override
	public String asGpx() {
		StringBuilder builder = new StringBuilder();
		builder.append(XmlFactory.createTag(namespace,tagname,true));
		for(GpxNode node: nodes) {
			builder.append(node.asGpx());
		}
		builder.append(XmlFactory.createTag(namespace,tagname,false));
		return builder.toString();
	}



}
