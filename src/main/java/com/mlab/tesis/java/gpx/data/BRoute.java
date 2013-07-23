package com.mlab.tesis.java.gpx.data;




public class BRoute extends AbstractGpxElement {

	private final String TAG_RTEPT = "rtept";

	public BRoute() {
		super();
		this.tagname = "rte";
	}
	
	/**
	 * Añade un WayPoint al final de la ArrayList que contiene
	 * la lista de WayPoint del Route. 
	 * @param wp WayPoint que se quiere añadir al Route
	 * @return boolean true si se añade 
	 */
	public boolean addWayPoint(BWayPoint wp) {
		wp.setTag(TAG_RTEPT);
		return this.nodes.add(wp);
	}
	public BWayPoint getWayPoint(int index) {
		BWayPoint wp=null;
		if(index>=0 && index<=this.size()-1) {
			wp = (BWayPoint)this.nodes.get(index);
		}
		return wp;
	}

	@Override
	public boolean add(GpxNode node) {
		if(BWayPoint.class.isAssignableFrom(node.getClass())) {
			((BWayPoint)node).setTag(TAG_RTEPT);
			return this.addWayPoint((BWayPoint)node);
		}
		return false;
	}
	
}
