package com.mlab.tesis.java.gpx.data;
import java.net.URI;

/**
 * <xsd:complexType name="linkType">
 *     <xsd:sequence>
 *         <-- elements must appear in this order -->
 *         <xsd:element name="text" type="xsd:string" minOccurs="0"/>
 *         <xsd:element name="type" type="xsd:string" minOccurs="0"/>
 *     </xsd:sequence>
 *     <xsd:attribute name="href" type="xsd:anyURI" use="required"/>
 * </xsd:complexType>
 * 
 * @author shiguera
 *
 */
public class Link {
	
	public URI uri;
	public String text;
	public String linktype;
	
	public Link(String txt, String ltype, String cadurl)  {
		this.text=txt;
		this.linktype=ltype;
		try {
			this.uri=new URI(cadurl);
		} catch (Exception e) {
			e.printStackTrace();
			this.uri=null;
		}
	}
	
	public String asGpx() {
		String cad="<link";
		if(this.text.isEmpty()==false) {
			cad+=" text=\""+this.text+"\"";
		}
		if(this.linktype.isEmpty()==false) {
			cad+=" type=\""+this.linktype+"\"";
		}
		cad+=">";
		cad += "<href>";
		if(this.uri!=null) {
			cad += this.uri.toString();			
		}
		cad += "</href>";
		cad += "</link>";
		return cad;
	}
	

}
