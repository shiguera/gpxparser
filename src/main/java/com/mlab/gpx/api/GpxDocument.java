package com.mlab.gpx.api;

import java.util.List;

import org.w3c.dom.Document;

import com.mlab.gpx.impl.Extensions;
import com.mlab.gpx.impl.Metadata;
import com.mlab.gpx.impl.Route;
import com.mlab.gpx.impl.Track;

/**
 * Interface for GpxDocuments. A GpxDocument has one Metadata element,
 * a List of WayPoint's, a List of Route's, a List of Tracks and one 
 * Extensions element.<br>
 * It has a method to get a java DomDocument and methods to add, remove or get 
 * WayPoints,  Routes and Tracks
 * @author shiguera
 *
 */
public interface GpxDocument {
	
	Document getDomDocument();
	Metadata getMetadata();
	List<WayPoint> getWayPoints();
	List<Route> getRoutes();
	List<Track> getTracks();
	Extensions getExtensions();
	
	boolean addTrack(Track track);
	boolean removeTrack(Track track);
	Track getTrack(int index);
	boolean hasTracks();	
	int trackCount();
	
	boolean addWayPoint(WayPoint wp);
	boolean removeWayPoint(WayPoint wp);
	WayPoint getWayPoint(int index);
	boolean hasWayPoints();
	int wayPointCount();
	
	boolean addRoute(Route rte);
	boolean removeRoute(Route route);
	Route getRoute(int index);
	boolean hasRoutes();
	int routeCount();
	
	String asGpx();

}
