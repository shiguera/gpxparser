package com.mlab.tesis.java.gpx.data;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 *
 */
public class SimpleWayPoint  extends AbstractWayPoint {

	public SimpleWayPoint() {
		super();
	}
	
	public SimpleWayPoint(String name, String description, long time, 
			double longitude, double latitude, double altitude, 
			double speed, double bearing, double accuracy) {
		super(name,description,time,longitude,latitude,altitude,
				speed,bearing, accuracy);
	}

	public SimpleWayPoint(String name, String descrip, long time, List<Double> values) {
		this.name = name;
		this.description = descrip;
		this.time = time;
		this.longitude = values.get(0);
		this.latitude = values.get(1);
		this.altitude = values.get(2);
		this.speed = values.get(3);
		this.bearing = values.get(4);
		this.accuracy = values.get(5);
	}
	
	@Override
	public String extensionsAsGpx() {
		return "";
	}	
}
