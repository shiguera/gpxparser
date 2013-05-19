package com.mlab.tesis.java.gpx.data;

import java.util.List;

import org.w3c.dom.Document;

public interface GpxDocument extends GpxNode {
	
	Document getDomDocument();
	Metadata getMetadata();
	List<WayPoint> getWayPoints();
	List<Route> getRoutes();
	List<Track> getTracks();
	Extensions getExtensions();
	
	void addTrack(Track track);
	void addWayPoint(WayPoint wp);
	void addRoute(Route rte);

	

}
