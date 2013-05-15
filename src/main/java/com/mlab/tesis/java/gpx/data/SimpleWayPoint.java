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
public class SimpleWayPoint  implements WayPoint {

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
	public String tag="wpt";
		
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
	public SimpleWayPoint(String name, String description, long time, 
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
	 * Devuelve una cadena gpx '<wpt>....</wpt>'
	 * @return Devuelve una cadena gpx '<wpt>....</wpt>'
	 */
	@Override
	public String asGpx() {
		String slat=String.format("%12.6f",this.latitude).trim().replace(',', '.');
		String slon=String.format("%12.6f",this.longitude).trim().replace(',', '.');
		
		String cad="";
		cad = "<"+this.tag+" ";
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
		if(this.name.isEmpty() == false) {
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
		cad += "</"+this.tag+">";
		return cad;
	}
	
	/**
	 * Devuelve un array de double con los seis valores:
	 * {lon,lat,alt,speed,bearing,accuracy}. La salida la utiliza
	 * el TrackSegment y otros para gestionar la TSerie con
	 * la colección de WayPoint
	 * @return double[] {lon,lat,alt,speed,bearing,accuracy}
	 */
	@Override
	public double[] getValues() {
		double[] val=new double[] {
			this.longitude, this.latitude, this.altitude,
			this.speed, this.bearing, this.accuracy};
		return val;
	}
	
	@Override
	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	@Override
	public double getAltitude() {
		return altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	@Override
	public double getBearing() {
		return bearing;
	}

	public void setBearing(double bearing) {
		this.bearing = bearing;
	}

	@Override
	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	@Override
	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	@Override
	public String getTag() {
		return tag;
	}

	@Override
	public void setTag(String tag) {
		this.tag = tag;
		
	}

	

	
}
