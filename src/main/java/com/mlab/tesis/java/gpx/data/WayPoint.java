package com.mlab.tesis.java.gpx.data;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 * Sirve para guardar los datos de un punto de un track, 
 * o de un route point o de un way point 
 * tomados con un GPS. Tiene campos para name, description, time,
 * longitude, latitude, altitude, speed, bearing y accuracy.
 * Tiene un método 'asGpx()' que devuelve la cadena en formato GPX 
 * correspondiente al WayPoint y otro método estático 'parse()' que
 * devuelve un WayPoint a partir de la cadena gpx 
 * <pre> {@code <wpt>...</wpt> } </pre>
 * @author shiguera
 *
 */
public class WayPoint  extends GpxElement {
	/**
	 * Tiempo en milisegundos desde 1 Enero 1970 en horario UTC. 
	 * Cuando no se dispone del dato
	 * se fija el valor en '0L'
	 */
	protected long time; // Milisegundos desde 1 Enero 1970 hora UTC
	/**
	 * nombre asignado al punto
	 */
	protected String name="";
	/**
	 * Descripción del punto
	 */
	protected String description="";
	/**
	 * Longitud del punto medida en grados entre -180.0 y 180.0 en el
	 * sistema de referencia WGS84 
	 */
	protected double longitude;
	/**
	 * Latitud del punto, medida en grados entre -90.0 y 90.0 en el 
	 * sistema de referencia WGS84 
	 */
	protected double latitude;
	/**
	 * Altitud del punto. Cuando no se conoce se fija el valor en '-1.0'
	 */
	protected double altitude;
	/**
	 * Rumbo seguido por el móvil medido en grados desde el Norte hacia el Este. 
	 * Cuando no se conoce el dato se 
	 * fija en '-1.0'
	 */
	protected double bearing;
	/**
	 * Velocidad del móvil medida en m/sg . Cuando no se conoce el dato
	 * se fija en '-1.0'
	 */
	protected double speed;
	/** Precisión de las medidas de posición en metros. Cuando no se conoce
	 * se fija en '-1.0'
	 */
	protected double accuracy;
	
	/**
	 * Este campo se utiliza para diferenciar en la salida gpx los waypoint aislados del
	 * documento gpx, que llevan un marcado <pre> {@code '<wpt>...</wpt> } </pre> de los waypoint cuando están 
	 * dentro de una Route o un TrackSegment. En esos casos el marcado es 
	 * <pre> {@code <rtept> y <trkpt> } </pre>
	 * respectivamente.
	 */
	public String TAG="wpt";
	
	/**
	 * Constructor de un objeto WayPoint
	 * @param time Long Tiempo en milisegundos desde el 
	 * 1 de Enero de 1970 a las 00:00 UTC. 
	 * Si es desconocido poner '0L'
	 * @param name String Nombre del punto. Cadena vacía si desconocido
	 * @param description String Descripción. Cadena vacía si desconocido
	 * @param longitude Longitud del punto medida en grados entre -180.0 y 180.0 en el
	 * sistema de referencia WGS84 
	 * @param latitude Latitud del punto, medida en grados entre -90.0 y 90.0 en el 
	 * sistema de referencia WGS84 
	 * @param altitude Altitud del punto. Cuando no se conoce se fija el valor en '-1.0'
	 * @param bearing Rumbo seguido por el móvil medido en grados desde el Norte hacia el Este. 
	 * Cuando no se conoce el dato se fija en '-1.0'
	 * @param speed Velocidad del móvil medida en m/sg . Cuando no se conoce el dato
	 * se fija en '-1.0'
	 * @param accuracy Precisión de las medidas de posición en metros. Cuando no se conoce
	 * se fija en '-1.0'
	 */
	public WayPoint(String name, String description, long time, 
			double longitude, double latitude, double altitude, 
			double speed, double bearing, double accuracy) {
		this.name=name;
		this.description=description;
		this.time=time;
		this.longitude=longitude;
		this.latitude=latitude;
		this.altitude=altitude;
		this.speed=speed;
		this.bearing=bearing;
		this.accuracy=accuracy;
	}
		
	/**
	 * Construye un WayPoint a partir de un long con el time y un 
	 * double[] con los valores {lon,lat,alt,speed,bearing,accuracy}
	 * @return Devuelve el WayPoint creado o null si hay errores
	 */	
	public static WayPoint fromValues(long t, double[] values) {
		if(values.length != 6) {
			return null;
		}
		WayPoint wp= new WayPoint("","",t, values[0],values[1],
			values[2],values[3],values[4],values[5]);
		return wp;
	}
	
	/**
	 * Crea una instancia de WayPoint a partir de la cadena
	 * gpx del mismo 
	 * @param cadgpx String <wpt lon=....></wpt>
	 * @return WayPoint o null si hay errores
	 * @throws ParserConfigurationException 
	 */
	public static WayPoint parseGpxString(String cadgpx) {
		WayPoint pt=null;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder=null;
		InputStream is = null;
		Document doc = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		    is = new ByteArrayInputStream(cadgpx.getBytes("UTF-8"));
		    doc = dBuilder.parse(is);	
			doc.getDocumentElement().normalize();
		} catch (Exception e) {
            e.printStackTrace();
            return null;
        }
		if(doc.getDocumentElement().getNodeName().equalsIgnoreCase("wpt")==false &&
				doc.getDocumentElement().getNodeName().equalsIgnoreCase("rtept")==false &&
				doc.getDocumentElement().getNodeName().equalsIgnoreCase("trkpt")==false) {
			return null;
		}
		Element wpt=doc.getDocumentElement();
		double lon, lat;
		try {
			lon=Double.parseDouble(wpt.getAttribute("lon"));
			lat=Double.parseDouble(wpt.getAttribute("lat"));			
		} catch (Exception e) {
            e.printStackTrace();
			return null;
		}
		double ele = -1.0;
		NodeList nl=doc.getElementsByTagName("ele");
		if(nl.getLength()>0) {
			try {
				ele=Double.parseDouble(nl.item(0).getTextContent());
			} catch (Exception e) {
	            e.printStackTrace();
				return null;
			}
		}
		String namee="";
		nl=doc.getElementsByTagName("name");
		if(nl.getLength()>0) {
			namee=nl.item(0).getTextContent();
		}
		String descrip="";
		nl=doc.getElementsByTagName("desc");
		if(nl.getLength()>0) {
			descrip=nl.item(0).getTextContent();
		}
		long t=0L;
		nl=doc.getElementsByTagName("time");
		if(nl.getLength()>0) {
			try {
				String cad=nl.item(0).getTextContent();
				SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.ss'Z'");
				format.setTimeZone(TimeZone.getTimeZone("UTC"));
				Date date=format.parse(cad);
				t=date.getTime();
			} catch (Exception e) {
	            e.printStackTrace();
				return null;
			}
		}
		double speedd=-1.0;
		nl=doc.getElementsByTagName("mlab:speed");
		if(nl.getLength()>0) {
			try {
				speedd=Double.parseDouble(nl.item(0).getTextContent());
			} catch (Exception e) {
	            e.printStackTrace();
				return null;
			}
		}
		double bearingg=-1.0;
		nl=doc.getElementsByTagName("mlab:bearing");
		if(nl.getLength()>0) {
			try {
				bearingg=Double.parseDouble(nl.item(0).getTextContent());
			} catch (Exception e) {
	            e.printStackTrace();
				return null;
			}
		}
		double acc=-1.0;
		nl=doc.getElementsByTagName("mlab:accuracy");
		if(nl.getLength()>0) {
			try {
				acc=Double.parseDouble(nl.item(0).getTextContent());
			} catch (Exception e) {
	            e.printStackTrace();
				return null;
			}
		}

		pt=new WayPoint(namee,descrip,t,lon,lat,ele,speedd,bearingg,acc);
		return pt;
	}

	/**
	 * Devuelve una cadena gpx '<wpt>....</wpt>'
	 * @return Devuelve una cadena gpx '<wpt>....</wpt>'
	 */
	@Override
	public String asGpx() {
		String slat=String.format("%12.6f",this.latitude).trim().replace(',', '.');
		String slon=String.format("%12.6f",this.longitude).trim().replace(',', '.');
		
		String cad="";
		cad = "<"+this.TAG+" ";
		cad += " lat=\""+slat+"\" lon=\""+slon+"\">";
		// Altitud
		if(this.altitude != -1.0) {
			cad += "<ele>"+String.format("%8.2f",this.altitude).trim().replace(',', '.')+"</ele>";
		}
		// Time
		if(this.time != 0l) {
			Date date=new Date(this.time);
			SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.ss'Z'");
			format.setTimeZone(TimeZone.getTimeZone("UTC"));
			String cadate=format.format(date);
			cad += "<time>"+cadate+"</time>";		
		}		 
		// name
		if(this.name.isEmpty()==false) {
			cad += "<name>"+this.name+"</name>";
		}
		// deac
		if(this.description.isEmpty()==false) {
			cad += "<desc>"+this.description+"</desc>";
		}
		// extensions
		if(this.bearing!=-1.0 || this.speed!=-1.0 || this.accuracy!=-1.0) {
			cad += "<extensions>";
			// Bearing
			if(this.bearing != -1.0) {
				cad += "<mlab:bearing>"+String.format("%12.2f",this.bearing).trim().replace(',', '.')+"</mlab:bearing>";		
			}
	
			// Speed
			if(this.speed != -1.0) {
				cad += "<mlab:speed>"+String.format("%12.2f",this.speed).trim().replace(',', '.')+"</mlab:speed>";		
			}
		
			// Accuracy
			if(this.accuracy != -1.0) {
				cad += "<mlab:accuracy>"+String.format("%12.2f",this.accuracy).trim().replace(',', '.')+"</mlab:accuracy>";		
			}
			
			cad += "</extensions>";
		}
		cad += "</"+this.TAG+">";
		return cad;
	}
	
	/**
	 * Devuelve un array de double con los seis valores:
	 * {lon,lat,alt,speed,bearing,accuracy}. La salida la utiliza
	 * el TrackSegment y otros para gestionar la TSerie con
	 * la colección de WayPoint
	 * @return double[] {lon,lat,alt,speed,bearing,accuracy}
	 */
	public double[] getValues() {
		double[] val=new double[] {
			this.longitude, this.latitude, this.altitude,
			this.speed, this.bearing, this.accuracy};
		return val;
	}
	
	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getAltitude() {
		return altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	public double getBearing() {
		return bearing;
	}

	public void setBearing(double bearing) {
		this.bearing = bearing;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	

	
}
