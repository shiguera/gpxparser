package com.mlab.tesis.java.gpx.data;

/**
 * Clase para el elemento metadata de un documento gpx.
 * El esquema xsd:
 * <xsd:complexType name="metadataType">
 *   <xsd:sequence>
 *     <-- elements must appear in this order -->
 *     <xsd:element name="name" type="xsd:string" minOccurs="0"/>
 *     <xsd:element name="desc" type="xsd:string" minOccurs="0"/>
 *     <xsd:element name="author" type="personType" minOccurs="0"/>
 *     <xsd:element name="copyright" type="copyrightType" minOccurs="0"/>
 *     <xsd:element name="link" type="linkType" minOccurs="0" maxOccurs="unbounded"/>
 *     <xsd:element name="time" type="xsd:dateTime" minOccurs="0"/>
 *     <xsd:element name="keywords" type="xsd:string" minOccurs="0"/>
 *     <xsd:element name="bounds" type="boundsType" minOccurs="0"/>
 *     <xsd:element name="extensions" type="extensionsType" minOccurs="0"/>
 *   </xsd:sequence>
 * </xsd:complexType>
 * @author shiguera
 *
 */
public class Metadata extends AbstractGpxElement {
	
	private String name;
	private String desc;
	private String author;
	private String copyright;
	
	
	public Metadata() {
		this.name = "";
		this.desc = "";
		this.author = "";
		this.copyright = "";
	}
	
	@Override
	public String asGpx() {
		StringBuilder builder = new StringBuilder();
		builder.append("<metadata>");
		builder.append(nameTag());
		builder.append(descTag());
		builder.append(authorTag());
		builder.append(copyrightTag());
		builder.append("</metadata>");
		return builder.toString();
	}
	private String nameTag() {
		StringBuilder builder = new StringBuilder();
		if(!name.isEmpty()) {
			builder.append("<name>");
			builder.append(this.name);
			builder.append("</name>");
		}		
		return builder.toString();
	}
	private String descTag() {
		StringBuilder builder = new StringBuilder();
		if(!desc.isEmpty()) {
			builder.append("<desc>");
			builder.append(this.desc);
			builder.append("</desc>");
		}		
		return builder.toString();
	}
	private String authorTag() {
		StringBuilder builder = new StringBuilder();
		if(!author.isEmpty()) {
			builder.append("<author>");
			builder.append(this.author);
			builder.append("</author>");
		}		
		return builder.toString();
	}
	private String copyrightTag() {
		StringBuilder builder = new StringBuilder();
		if(!copyright.isEmpty()) {
			builder.append("<copyright>");
			builder.append(this.copyright);
			builder.append("</copyright>");
		}		
		return builder.toString();
	}


	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	public String getAuthor() {
		return author;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

}
