package com.mlab.tesis.java.gpx.data;


/**
 * Clase base abstracta para los WayPoint <br>
 * Incluye campos para: name, description, time, long, lat, alt, speed, bearing, accuraccy
 * @author shiguera
 *
 */
public abstract class AbstractWayPoint implements WayPoint {

	protected final String TAG_WAYPOINT = "wpt";
	
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
	protected String tag;
		
	protected AbstractWayPoint() {
		this.name = "";
		this.description = "";
		this.time = -1l;
		this.longitude = 0.0;
		this.latitude = 0.0;
		this.altitude = 0.0;
		this.speed = 0.0;
		this.bearing = 0.0;
		this.accuracy = -1.0;
		this.tag = TAG_WAYPOINT;
	}
	
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
	protected AbstractWayPoint(String name, String description, long time, 
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
		this.tag = TAG_WAYPOINT;
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

	/**
	 * Devuelve una cadena gpx '<wpt>....</wpt>'
	 * Las subclases deberán implementar 'extensionsAsGpx()' que 
	 * devuelve el grupo de elementos '<extension>...</extension>'
	 * que añada la subclase.
	 * @return Devuelve una cadena gpx '<wpt>....</wpt>'
	 */
	@Override
	public String asGpx() {
		//System.out.println("HOLA"+this.toString());
		String slat = Util.doubleToString(latitude, 12, 6);
		String slon = Util.doubleToString(longitude, 12, 6);
		
		String cad="";
		cad = "<"+this.tag+" ";
		cad += " lat=\""+slat+"\" lon=\""+slon+"\">";
		// Altitud
		if(this.altitude >= 0.0) {
			cad += "<ele>"+String.format("%8.2f",this.altitude).trim().replace(',', '.')+"</ele>";
		}
		// Time
		if(this.time >= 0l) {
			cad += "<time>"+Util.dateTimeToStringGpxFormat(time)+"</time>";		
		}		 
		// name
		if(!this.name.isEmpty()) {
			cad += "<name>"+this.name+"</name>";
		}
		// deac
		if(!this.description.isEmpty()) {
			cad += "<desc>"+this.description+"</desc>";
		}
		// extensions
		if(this.bearing != -1.0 || this.speed!=-1.0 || this.accuracy!=-1.0) {
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
			
			// abstract method
			cad += extensionsAsGpx();
			
			cad += "</extensions>";
		}
		cad += "</"+this.tag+">";
		return cad;	
	}

	/**
	 * Método abstracto utilizado por el método 'asGpx()'
	 * Las clases derivadas deben implementar este método que devolverá una
	 * cadena Gpx con sus extensiones en la forma:<br>
	 * <ns:extensionName1>....</ns:extensionName1>...<ns:extensionName2>...</ns:extensionName2>
	 * La cadena se insertará durante la ejecución del método 'asGpx()'
	 * @return
	 */
	public abstract String extensionsAsGpx();
	@Override
	public String asCsv() {
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("%d", this.time));
		builder.append(",");
		builder.append(Util.doubleToString(this.longitude, 12, 6));
		builder.append(",");
		builder.append(Util.doubleToString(this.latitude, 12, 6));
		builder.append(",");
		builder.append(Util.doubleToString(this.altitude, 12, 2));
		builder.append(",");
		builder.append(Util.doubleToString(this.speed, 12, 2));
		builder.append(",");
		builder.append(Util.doubleToString(this.bearing, 12, 2));
		builder.append(",");
		builder.append(Util.doubleToString(this.accuracy, 12, 2));
		return builder.toString();
	}	

	@Override
	public long getTime() {
		return time;
	}
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public double getLongitude() {
		return longitude;
	}

	@Override
	public double getLatitude() {
		return latitude;
	}
	@Override
	public double getAltitude() {
		return altitude;
	}
	@Override
	public double getSpeed() {
		return speed;
	}
	@Override
	public double getBearing() {
		return bearing;
	}
	@Override
	public double getAccuracy() {
		return accuracy;
	}
	@Override
	public String getTag() {
		return tag;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}
	public void setBearing(double bearing) {
		this.bearing = bearing;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}
	@Override
	public void setTag(String tag) {
		this.tag = tag;		
	}
}
