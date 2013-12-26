package com.mlab.gpx.impl;

import com.mlab.gpx.api.AbstractGpxNode;

/**
 * Utility class to add node content in a composite way to GpxNodes
 * @author shiguera
 *
 */
public class TextContent extends AbstractGpxNode  {

	private String content;
	
	TextContent() {
		this.content = "";
	}
	TextContent(String content) {
		this.content = content;
	}
	
	@Override
	public String asGpx() {
		return content;
	}
	
	@Override
	public String toString() {
		return content;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
