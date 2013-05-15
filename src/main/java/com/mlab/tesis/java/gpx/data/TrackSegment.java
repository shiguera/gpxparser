package com.mlab.tesis.java.gpx.data;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.mlab.tesis.java.tserie.TSerie;

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
public class TrackSegment  implements GpxElement {
	
	private ArrayList<GpxNode> wpts;
	private TSerie tserie;
	
	public TrackSegment() {
		// (lon,lat,alt,vel,rumbo,accuracy)
		//tSerie=new TTSerie(6);
		tserie = new TSerie();
		wpts= new ArrayList<GpxNode>();
		
	}
	/**
	 * Añade un AbstractWpt al final de la TSerie que contiene
	 * la lista de AbstractWpt del TrackSegment. Si el tiempo del 
	 * AbstractWpt que se quiere añadir es menor o igual que el
	 * último tiempo de la TSerie no se añade y se devuelve false
	 * @param wp AbstractWpt que se quiere añadir al TrackSegment
	 * @return boolean true si se añade y false si no se añade
	 */
	public boolean addWayPoint(WayPoint wp) {
		boolean result = false;
		if(this.tserie.canAdd(wp.getTime(), wp.getValues())) {
			result = this.tserie.add(wp.getTime(), wp.getValues());
			if(result) {
				result = this.wpts.add(wp);
			} 
		}
		return result;
	}
	/**
	 * Proporciona acceso al ArrayList de WayPoint
	 * @return Devuelve una referencia al ArrayList de WayPoint
	 */
	public ArrayList<GpxNode> getWpts() {
		return wpts;
	}
	
	/**
	 * Da acceso directo a los AbstractWpt's del segmento
	 * @param index índice del AbstractWpt buscado
	 * @return AbstractWpt o null
	 */
	public WayPoint getWayPoint(int index) {
		WayPoint pt=null;
		if(index>=0 && index < this.wpts.size()) {
			pt = (WayPoint)this.wpts.get(index);
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
	public WayPoint getStartWayPoint() {
		WayPoint wpt = null;
		if (this.size()>0) {
			wpt = (WayPoint)this.wpts.get(0);
		}
		return wpt;
	}
	/**
	 * Devuelve el ultimo AbstractWpt del segmento o nulo
	 * @return
	 */
	public WayPoint getEndWayPoint() {
		WayPoint wpt = null;
		if (this.size()>0) {
			wpt = (WayPoint)this.wpts.get(this.size()-1);
		}
		return wpt;
	}

	/**
	 * Devuelve el número de puntos en el segmento
	 * @return
	 */
	public int wayPointCount() {
		return this.wpts.size();
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
	 * Devuelve una cadena Gpx del segmento TrackSegment
	 */
	@Override
	public String asGpx() {
		String cad="<trkseg>";
		// Comprobar que hay algún AbstractWpt
		if(wpts.size()>0) {
			WayPoint wp = null;
			for(int i=0;i<this.wpts.size(); i++) {
				wp = (WayPoint)this.wpts.get(i);
				if(wp!=null) {
					wp.setTag("trkpt");
					cad += wp.asGpx();					
				}
			}
		}
		cad += "</trkseg>";
		return cad;
	}

	
	public int size() {
		return this.tserie.size();
	}
	@Override
	public boolean add(GpxNode node) {
		if(WayPoint.class.isAssignableFrom(node.getClass())) {
			return this.addWayPoint((WayPoint)node);
		}
		return false;
	}
	
	@Override
	public GpxNodeList nodes() {
		return new SimpleNodeList(this.wpts);
	}
}
