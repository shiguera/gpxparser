package com.mlab.tesis.java.gpx.data;


public class SimpleGpxAttribute implements GpxAttribute {

	private String name;
	private String value;
	
	public SimpleGpxAttribute() {
		this.name = "";
		this.value = "";
	}
	public SimpleGpxAttribute(String name, String value) {
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
