package com.mlab.gpx.api;


/**
 * Interface for attribute elements. They are GpxElements that have name and value
 * @author shiguera
 *
 */
public interface Attribute extends GpxNode {
	
	String getName();
	String getValue();
	void setName(String name);
	void setValue(String value);

}
