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

public class SimpleGpxFactory extends GpxFactory {

	SimpleGpxFactory() {
		this.factoryType = GpxFactory.Type.SimpleGpxFactory;
	}
	
	/**	
	 * Parsea un documento de texto gpx a GpxDocument
	 * @param cadgpx
	 * @return GpxDocument 
	 */
	@Override
	public SimpleGpxDocument parseGpxDocument(String cadgpx) {
		Document doc= parseXmlDocument(cadgpx);
		//System.out.println(doc.getTextContent());
		if(doc.getDocumentElement().getNodeName().equalsIgnoreCase("gpx")==false) {
			return null;
		}
		//Element gpx=doc.getDocumentElement();
		
		SimpleGpxDocument gpxDocument = new SimpleGpxDocument(this.factoryType);
		
		// FIXME Procesar metadata
		
		// Procesar nodos WayPoint
		NodeList nl=doc.getElementsByTagName("wpt");
		//System.out.println("----------------\n"+nl.getLength()+"---------------\n");
		for(int i=0; i< nl.getLength(); i++) {
			SimpleWayPoint wp= parseWpt(GpxFactory.nodeAsString(nl.item(i),false));
			if(wp!=null) {
				gpxDocument.addWayPoint(wp);
			}
		}
		// Procesar nodos RTE
		nl=doc.getElementsByTagName("rte");
		for(int i=0; i< nl.getLength(); i++) {
			Route rte= parseRoute(GpxFactory.nodeAsString(nl.item(i),false));
			if(rte!=null) {
				gpxDocument.addRoute(rte);
			}
		}
		// Procesar nodos TRK
		nl=doc.getElementsByTagName("trk");
		for(int i=0; i< nl.getLength(); i++) {
			Track trk= parseTrack(GpxFactory.nodeAsString(nl.item(i),false));
			if(trk!=null) {
				gpxDocument.addTrack(trk);
			}
		}
		
		// TODO Procesar nodos Extensions

		return gpxDocument;
	}

	
	/**
	 * Construye un WayPoint a partir de un long con el time y un 
	 * double[] con los valores {lon,lat,alt,speed,bearing,accuracy}
	 * @return Devuelve el WayPoint creado o null si hay errores
	 */	
	@Override
	public WayPoint createWayPoint(long time, double[] values) {
		if(values==null || values.length != 6) {
			return null;
		}
		WayPoint wp= new SimpleWayPoint("","",time, values[0],values[1],
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
	@Override
	public SimpleWayPoint parseWpt(String cadgpx) {
			SimpleWayPoint pt=null;
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

			pt=new SimpleWayPoint(namee,descrip,t,lon,lat,ele,speedd,bearingg,acc);
			return pt;
		}

	@Override
	public TrackSegment parseSegment(String cadgpx) {
		TrackSegment ts= new TrackSegment();

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
		if(doc.getDocumentElement().getNodeName().equalsIgnoreCase("trkseg")==false) {
			return null;
		}
		NodeList nl=doc.getElementsByTagName("trkpt");
		//System.out.println(nl.getLength());
		if(nl.getLength()>0) {
			for (int i=0; i<nl.getLength(); i++) {
				try {
					SimpleWayPoint wp= this.parseWpt(GpxFactory.nodeAsString(nl.item(i),false));
					if(wp!=null) {
						ts.addWayPoint(wp);
					}
				} catch (Exception e) {
		            e.printStackTrace();
					return null;
				}
			}
		}
		return ts;
	
	}
	
	@Override
	public Route parseRoute(String cadgpx) {
			Route rte= new Route();

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
			if(doc.getDocumentElement().getNodeName().equalsIgnoreCase("rte")==false) {
				return null;
			}
			NodeList nl=doc.getElementsByTagName("rtept");
			if(nl.getLength()>0) {
				for (int i=0; i<nl.getLength(); i++) {
					try {
						SimpleWayPoint wp= this.parseWpt(GpxFactory.nodeAsString(nl.item(i),false));
						if(wp!=null) {
							rte.addWayPoint(wp);
						}
					} catch (Exception e) {
			            e.printStackTrace();
						return null;
					}
				}
			}
			return rte;
		}

	@Override
	public Track parseTrack(String cadgpx) {
		Track t= new Track();

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
		if(doc.getDocumentElement().getNodeName().equalsIgnoreCase("trk")==false) {
			return null;
		}
		NodeList nl=doc.getElementsByTagName("trkseg");
		if(nl.getLength()>0) {
			for (int i=0; i<nl.getLength(); i++) {
				try {
					TrackSegment ts = this.parseSegment(GpxFactory.nodeAsString(nl.item(i),false));
					if(ts!=null) {
						t.addTrackSegment(ts);
					}
				} catch (Exception e) {
		            e.printStackTrace();
					return null;
				}
			}
		}
		return t;
	}



}
