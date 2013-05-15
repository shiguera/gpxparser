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
public class Metadata implements GpxNode {
	
	public Metadata() {
		
	}
	
	@Override
	public String asGpx() {
		String cad="<metadata>";
		
		cad += "</metadata>";
		return cad;
	}
}
