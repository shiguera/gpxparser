package com.mlab.tesis.java.gpx.data;

import com.mlab.tesis.java.tserie.TSerie;
import com.mlab.tesis.srs.EllipsoidWGS84;

/**
 * Representa un elemento 'tracksegment' de un documento Gpx. La definición del elemento es:<p>
 * <pre>
 * {@code 
 * <xsd:complexType name="trksegType">
 *    <xsd:sequence>
 *       <-- elements must appear in this order --> 
 *       <xsd:element name="trkpt" type="wptType" minOccurs="0" maxOccurs="unbounded"/>  
 *       <xsd:element name="extensions" type="extensionsType" minOccurs="0"/>     
 *    </xsd:sequence>
 * </xsd:complexType>
 * }
 * </pre>
 * */
public class BTrackSegment extends AbstractGpxElement {
	
	private final String TAG_TRKPT = "trkpt";
	private final String TAGNAME = "trkseg";
	private TSerie tserie;
	
	public BTrackSegment() {
		// (lon,lat,alt,vel,rumbo,accuracy)
		super();
		tserie = new TSerie();
		this.tagname = TAGNAME;
	}
	
	
	/**
	 * Da acceso directo a los AbstractWpt's del segmento
	 * @param index índice del AbstractWpt buscado
	 * @return AbstractWpt o null
	 */
	public BWayPoint getWayPoint(int index) {
		BWayPoint pt=null;
		if(index>=0 && index < this.size()) {
			pt = (BWayPoint)this.nodes.get(index);
		}
		return pt;
	}

	/**
	 * Devuelve el tiempo correspondiente al primer WayPoint del TrackSegment
	 * @return long Tiempo del primer WayPoint del TrackSegment
	 */
	public long getStartTime() {
		return this.tserie.firsTime();
	}
	
	/**
	 * Devuelve el tiempo correspondiente al último WayPoint del TrackSegment
	 * @return long Tiempo del último WayPoint del TrackSegment
	 */
	public long getEndTime() {
		return this.tserie.lastTime();
	}
	/**
	 * Devuelve el primer AbstractWpt del segmento
	 * @return
	 */
	public BWayPoint getStartWayPoint() {
		BWayPoint wpt = null;
		if (this.size()>0) {
			wpt = (BWayPoint)this.nodes.get(0);
		}
		return wpt;
	}
	/**
	 * Devuelve el ultimo AbstractWpt del segmento o nulo
	 * @return
	 */
	public BWayPoint getEndWayPoint() {
		BWayPoint wpt = null;
		if (this.size()>0) {
			wpt = (BWayPoint)this.nodes.get(this.size()-1);
		}
		return wpt;
	}

	/**
	 * Devuelve un double con los valores de 
	 * [lon,lat,alt,vel,rumbo,acc] interpolados para ese tiempo
	 * @param time tiempo en milisegundos UTC de los valores solicitados
	 * @return double[] [lon,lt,alt,vel,rumbo,acc] con los valores interpolados.<p>
	 * Si el tiempo es menor que el primero del trkseg o mayor
	 * que el último del trkseg devuelve null
	 */
	public double[] getValues(long time) {
		return this.tserie.getValues(time);
	}
	
	/**
	 * Comprueba si es un WayPoint y delega en el método
	 * addWayPoint() que añade a la lista de puntos y a la
	 * TSerie tras actualizar la etiqueta del wp
	 */
	@Override
	public boolean add(GpxNode node) {
		if(BWayPoint.class.isAssignableFrom(node.getClass())) {
			return this.addWayPoint((BWayPoint)node);
		}
		return false;
	}
	/**
	 * Añade un WayPoint a la TSerie y a la List\<GpxNode\> que contiene
	 * la lista de WayPoint del TrackSegment. Si el tiempo es menor 
	 * o igual que el último tiempo de la TSerie no se añade y se devuelve false
	 * @param wp WayPoint que se quiere añadir al TrackSegment
	 * @return boolean true si se añade y false si no se añade
	 */
	public boolean addWayPoint(BWayPoint wp) {
		boolean result = false;
		if(this.tserie.canAdd(wp.getTime(), wp.getValues())) {
			result = this.tserie.add(wp.getTime(), wp.getValues());
			if(result) {
				wp.setTag(TAG_TRKPT);
				result = this.nodes.add(wp);
			} 
		} else {
			System.out.println("Can't add waypoint");
		}
		return result;
	}

	public String asCsv(boolean withUtmCoords) {
		StringBuilder builder = new StringBuilder();
		if(this.size()>0) {
			for(int i=0; i< this.nodes.size(); i++) {
				builder.append(((BWayPoint)this.nodes.get(i)).asCsv(withUtmCoords));
				if(!isLastPoint(i)) {
					builder.append("\n");
				}
			}
		}
		return builder.toString();
	}
	
	private boolean isLastPoint(int index) {
		if(index==this.nodes.size()-1) {
			return true;
		}
		return false;
	}

	public double length() {
		double length = 0.0;
		
		if(this.nodes.size()>0) {
			EllipsoidWGS84 ell = new EllipsoidWGS84();
			BWayPoint last = (BWayPoint)this.nodes().get(0);
			double[] lastxy= ell.proyUTM(last.getLongitude(), last.getLatitude());
			
			for(int i=0; i<this.nodes.size(); i++) {
				BWayPoint current = (BWayPoint)this.nodes().get(i);
				double[] currentxy = ell.proyUTM(current.getLongitude(), current.getLatitude());
				double incd = Math.sqrt((currentxy[0]-lastxy[0])*(currentxy[0]-lastxy[0])+
						(currentxy[1]-lastxy[1])*(currentxy[1]-lastxy[1]));
				length += incd;
				last = current.clone();
				lastxy= ell.proyUTM(last.getLongitude(), last.getLatitude());
			}
		}
		
		return length;
	}
}
