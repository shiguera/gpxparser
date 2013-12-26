package com.mlab.gpx.api;

import java.util.ArrayList;
import java.util.List;

import com.mlab.gpx.impl.util.XmlFactory;

public abstract class AbstractGpxElement implements GpxElement {

	protected String tagname;
	protected String namespace;
	protected List<GpxNode> nodes;
	
	protected AbstractGpxElement() {
		this.nodes = new ArrayList<GpxNode>();
		this.tagname ="";
		this.namespace = "";
	}
	protected AbstractGpxElement(String namespace, String name) {
		this();
		this.tagname = name;
		this.namespace = namespace;
	}
	
	@Override
	public String asGpx() {
		StringBuilder builder = new StringBuilder();
		builder.append(XmlFactory.createTag(namespace,tagname,true));
		for(GpxNode node: nodes()) {
			builder.append(node.asGpx());
		}
		builder.append(XmlFactory.createTag(namespace,tagname,false));
		return builder.toString();
	}

	@Override
	public boolean add(GpxNode node) {
		return this.nodes.add(node);
	}
	@Override
	public boolean remove(GpxNode gpxNode) {
		return this.nodes.remove(gpxNode);
	}
	@Override
	public GpxNode get(int index) {
		return (GpxNode)this.nodes.get(index);
	}

	@Override
	public List<GpxNode> nodes() {
		return nodes;
	}
	
	public String getTagName() {
		return tagname;
	}
	public String getNamespace() {
		return namespace;
	}
	public void setTagName(String name) {
		this.tagname = name;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	@Override
	public int size() {
		return this.nodes.size();
	}

}
