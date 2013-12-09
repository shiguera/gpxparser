package com.mlab.gpx.impl.srs;

import java.util.Calendar;
import java.util.TimeZone;


public class SRS {

	public static double DMSToDegrees(double degrees, double minutes, double seconds) {
		double sign = 1.0;
		if(degrees<0.0) {
			sign = -1.0;
		}
		double value = Math.abs(degrees)+minutes/60.0+seconds/3600.0;
		return sign*value;
	}
	
	public static String doubleToString(double value, int digits, int decimals) {
		return String.format(cadFormatDouble(digits,decimals),value).trim().replace(',', '.');
	}
	private static String cadFormatDouble(int digits, int decimals) {
		String cadformat = "%"+String.format("%d.%d", digits, decimals)+"f";
		return cadformat;
	}

	public static double round(double d, int decimals) {
		if(decimals<0) {
			return d;
		}
		double factor = Math.pow(10.0, decimals);
		double result = (Math.rint(d*factor))/factor;
		return result;
	}
	
	 /**
     * Devuelve una cadena en la forma 2012-10-09T12:00:23
     * @param t
     * @param gmt
     * @return Devuelve una cadena en la forma 2012-10-09T12:00:23
     */
    static public String dateTimeToString(long t, boolean gmt) {    	
    	String cad=SRS.dateToString(t,gmt);
    	cad+="T"+SRS.timeToString(t,gmt);
    	return cad;
    }
    /**
     * 
     * @param t Milisegundos de la hora
     * @param gmt si true, se utilizará hora GMT
     * @return Cadena con la hora en formato hh:mm:ss
     */
    static public String timeToString(long t, boolean gmt) {
    	//Log.i("HAL","SRS.timeToString()");
    	Calendar cal=Calendar.getInstance();
    	cal.setTimeInMillis(t);
    	if(gmt) {
    		cal.setTimeZone(TimeZone.getTimeZone("gmt"));
    	}
    	String date=String.format("%02d", cal.get(Calendar.HOUR_OF_DAY))+":"+
    			String.format("%02d", cal.get(Calendar.MINUTE))+":"+
    			String.format("%02d", cal.get(Calendar.SECOND));
    	//Log.d("HAL","Time:"+date);
        return date;
    }
    static public String dateToString(long t, boolean gmt) {
    	//Log.i("HAL","SRS.dateToString()");
    	Calendar cal=Calendar.getInstance();
    	if(gmt) {
    		cal.setTimeZone(TimeZone.getTimeZone("gmt"));
    	}
    	cal.setTimeInMillis(t);
    	String date=cal.get(Calendar.YEAR)+"-"+String.format("%02d", cal.get(Calendar.MONTH)+1)+"-"+
    			String.format("%02d", cal.get(Calendar.DAY_OF_MONTH));
    	//Log.d("HAL","Date:"+date);
        return date;
    }
	
}
