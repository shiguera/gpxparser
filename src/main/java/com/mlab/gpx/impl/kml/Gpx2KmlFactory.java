package com.mlab.gpx.impl.kml;

import java.io.File;

import com.mlab.gpx.api.WayPoint;
import com.mlab.gpx.impl.TrackSegment;
import com.mlab.gpx.impl.util.Util;

import de.micromata.opengis.kml.v_2_2_0.BalloonStyle;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.LineString;
import de.micromata.opengis.kml.v_2_2_0.LookAt;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Style;
import de.micromata.opengis.kml.v_2_2_0.StyleSelector;

public class Gpx2KmlFactory {

	private final static int DEFAULT_PLACEMARK_IMAGE_WITH = 400;
	private final static String DEFAULT_BALLOON_BACKGROUND = "#a0d0b0";
	
	public static boolean writeToFile(Kml kml, File file) {
		try {
			kml.marshal(file);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	public static Kml trackSegmentToKml(TrackSegment segment, String docname, boolean withStartPoint, boolean withEndPoint) {
		Kml kml = de.micromata.opengis.kml.v_2_2_0.KmlFactory.createKml();
		Document document = kml.createAndSetDocument().withName(docname).withOpen(true);
		WayPoint wp = segment.getStartWayPoint();
		WayPoint wpe = segment.getEndWayPoint();
		double r = Util.bearing(wp.getLongitude(), wp.getLatitude(), wpe.getLongitude(), wpe.getLatitude());
		double length = segment.length();
		
		final LookAt lookat = createLookAt(wp.getLongitude(),
				wp.getLatitude(), r, length, 50.0 );
		
		final Style style = createLineStyle("linestyleExample","7f0000ff",4.0d);		
		document.getStyleSelector().add(style);

		LineString ls = com.mlab.gpx.impl.kml.Gpx2KmlFactory.segmentToLineString(segment);
		
		Placemark pm3 = document.createAndAddPlacemark().withName("gpxSegment");
		pm3.setGeometry(ls);
		pm3.setStyleUrl("#linestyleExample");

		if(withStartPoint) {
			Placemark pm1 = document.createAndAddPlacemark().withName("Start Point");
			pm1.createAndSetPoint().addToCoordinates(wp.getLongitude(), wp.getLatitude(), wp.getAltitude());
		}
		if(withEndPoint) {
			Placemark pm2 = document.createAndAddPlacemark().withName("End Point");
			pm2.createAndSetPoint().addToCoordinates(wpe.getLongitude(), wpe.getLatitude(), wpe.getAltitude());
		}

		return kml;
	}
	public static Style createLineStyle(String id, String color, double width) {
		final Style style = new Style();
		style.setId(id);
		style.createAndSetLineStyle().withColor(color).withWidth(width);
		return style;
	}
	public static LookAt createLookAt(double lon, double lat, double heading, double range, double tilt) {
		LookAt l = new LookAt();
		l.setLongitude(lon);
		l.setLatitude(lat);
		l.setHeading(heading);
		l.setRange(range);
		l.setTilt(tilt);
		return l;
	}
	public static LineString segmentToLineString(TrackSegment segment) {
		if(segment == null || segment.size()<2) {
			return null;
		}
		LineString ls = new LineString();
		double lon, lat, alt;
		for(int i=0; i<segment.size(); i++) {
			lon = segment.getWayPoint(i).getLongitude();
			lat = segment.getWayPoint(i).getLatitude();
			alt = segment.getWayPoint(i).getAltitude();
			ls.addToCoordinates(lon, lat, alt);
		}
		return ls;
	}
	
	public static Placemark createPlacemark(String name, String description, double longitude, double latitude, double altitude) {
		final Kml kml = new Kml();
		Placemark placeMark = kml.createAndSetPlacemark();
    	placeMark.withName(name).withOpen(Boolean.TRUE).withDescription(description)
    		.withStyleUrl("#balloonstyle")
    	   .createAndSetPoint().addToCoordinates(longitude,latitude,altitude);		
		return placeMark;
	}
	public static Kml createPlaceMarkWithBalloon(Placemark placemark) {
		final Kml kml = new Kml();
		Document doc = kml.createAndSetDocument();
		String text = "<b>"+placemark.getName()+"</b><br/><em>"+placemark.getDescription()+"</em>";
		BalloonStyle bs = new BalloonStyle().withBgColor("#f0f0f0").withText(text);
		Style style = new Style().withId("balloonstyle").withBalloonStyle(bs);
		placemark.createAndAddStyle().createAndSetBalloonStyle().withBgColor("#f0f0f0").withText(text);
		doc.addToFeature(placemark);
		return kml;
	}
	public static Kml createPlaceMarkWithImage(Placemark placemark, File imagefile) {
		final Kml kml = new Kml();
		Document doc = kml.createAndSetDocument();
		String text = "<b>"+placemark.getName()+"</b><br/><em>"+placemark.getDescription()+"</em><br/>"+
				"<img src='"+imagefile.getPath()+"' width='"+DEFAULT_PLACEMARK_IMAGE_WITH+"'/>";
		placemark.createAndAddStyle().createAndSetBalloonStyle().withBgColor(DEFAULT_BALLOON_BACKGROUND).withText(text);
		doc.addToFeature(placemark);
		return kml;
	}

}
