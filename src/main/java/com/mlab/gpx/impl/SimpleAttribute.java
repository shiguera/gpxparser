package com.mlab.gpx.impl;

import com.mlab.gpx.api.Attribute;


public class SimpleAttribute implements Attribute {

	private String name;
	private String value;
	
	public SimpleAttribute() {
		this.name = "";
		this.value = "";
	}
	public SimpleAttribute(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	/**
	 * Devuelve una cadena name='value'
	 * @return cadena name='value'
	 */
	@Override
	public String asGpx() {
		return name+"='"+value+"'";
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}

}
