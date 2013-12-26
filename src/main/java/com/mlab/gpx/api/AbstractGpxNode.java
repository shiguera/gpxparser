package com.mlab.gpx.api;

public abstract class AbstractGpxNode implements GpxNode {

	protected String tagname;
	protected String namespace;
	
	public AbstractGpxNode() {
		this.tagname = "";
		this.namespace = "";
	}
	
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

	@Override
	public int size() {
		return -1;
	}
	@Override
	public String getTagName() {
		return tagname;
	}
	@Override
	public void setTagName(String tagname) {
		this.tagname = tagname;
	}
	@Override
	public String getNamespace() {
		return namespace;
	}
	@Override
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	

}
