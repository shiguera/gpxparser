package com.mlab.tesis.java.gpx.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;


public class Util {
    /**
     * Devuelve una cadena en la forma 2012-10-09T12:00:23
     * @param t
     * @param gmt
     * @return Devuelve una cadena en la forma 2012-10-09T12:00:23
     */
    static public String dateTimeToString(long t, boolean gmt) {    	
    	String cad=Util.dateToString(t,gmt);
    	cad+="T"+Util.timeToString(t,gmt);
    	return cad;
    }
    /**
     * 
     * @param t Milisegundos de la hora
     * @param gmt si true, se utilizará hora GMT
     * @return Cadena con la hora en formato hh:mm:ss
     */
    static public String timeToString(long t, boolean gmt) {
    	//Log.i("HAL","Util.timeToString()");
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
    	//Log.i("HAL","Util.dateToString()");
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

    /**
	 * Escribe una cadena de texto en un fichero
	 * 
	 * @param filename
	 *            String Nombre del fichero
	 * @param cad
	 *            String Cadena de texto a escribir
	 * @return 1 si todo va bien, negativo o cero en caso contrario
	 */
	public static int write(String filename, String cad) {
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(filename));
			writer.write(cad + "\n");
			writer.close();
		} catch (FileNotFoundException fe) {
			System.out.println("File " + filename + " not found.\n"
					+ fe.getMessage());
			return -1;
		} catch (NumberFormatException ne) {
			System.out.println("Number format error. " + ne.getMessage());
			return -2;
		} catch (Exception e) {
			System.out.println("Unidentified error. " + e.getMessage());
			return -3;
		}
    
		return 1;
	}

	/**
	 * Lee un fichero de texto y lo entrega en forma de un String
	 * @param file File fichero de texto
	 * @return String con el fichero leido
	 */
	public static String readFileToString(File file) {
		StringBuilder builder = new StringBuilder();
		BufferedReader reader;
		String line="";	
		try {
			reader = new BufferedReader(new FileReader(file));
			line="";
			while((line=reader.readLine()) != null) {
				builder.append(line);
				builder.append("\n");
			}
			reader.close();
		} catch (FileNotFoundException fe) {
			//LOG.info("File "+filename+" not found.\n"+fe.getMessage());
			return "";
		} catch (NumberFormatException ne) {
			//LOG.info("Number format error. "+ne.getMessage());
			return "";
		} catch (Exception e) {
			//LOG.info("Unidentified error. "+e.getMessage());
			return "";
		}
		return builder.toString();
		
	}
	
	/**
	 * Lee una matriz de doubles desde un fichero CSV
	 * @param filename Nombre del fichero
	 * @param delimiter Delimitador de campos
	 * @return Matriz con los doubles leidos
	 */
	public static double[][] readCsvDoubles(String filename, String delimiter) {
		//LOG.info("Galib.read("+filename+", "+delimiter+")");
		ArrayList<double[]> arrvpoint=new ArrayList<double[]>();
		BufferedReader reader;
		String line="";	
		int numcolumns=0;
		double d[];
		try {
			reader = new BufferedReader(new FileReader(filename));
			line="";
			while((line=reader.readLine()) != null) {
				String[] arr=line.split(delimiter);
				d = new double[arr.length];
				for(int i=0; i<d.length; i++) {
					d[i]=Double.parseDouble(arr[i].trim());
				}
				arrvpoint.add(d);
				numcolumns=d.length; //
			}
			reader.close();
		} catch (FileNotFoundException fe) {
			//LOG.info("File "+filename+" not found.\n"+fe.getMessage());
			return null;
		} catch (NumberFormatException ne) {
			//LOG.info("Number format error. "+ne.getMessage());
			return null;
		} catch (Exception e) {
			//LOG.info("Unidentified error. "+e.getMessage());
			return null;
		}
		double[][] result= new double[arrvpoint.size()][numcolumns];
		return arrvpoint.toArray(result);
	}
	
	 /**
	 * Lee un recurso almacenado en /src/main/resources y lo devuelve como fichero
	 * que queda grabado en el directorio raiz de la aplicación
	 * @param filename nombre del fichero (sin path, solo nombre)
	 * @return File del fichero grabado en el directorio de la aplicación
	 */
	public static File readResourceFile(String filename) {
		//LOG.info("readResourceFile():"+filename);
		File file=new File(filename);
		try {
			InputStream is = ClassLoader.getSystemResourceAsStream(filename);
			OutputStream out = new FileOutputStream(file);
			 
			int read = 0;
			byte[] bytes = new byte[1024];
		 
			while ((read = is.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
		 
			is.close();
			out.flush();
			out.close();
		 
			//LOG.info("   New file created");
		} catch (IOException e) {
			System.out.println(e.getMessage());
			//LOG.severe("Error, can't read file");					    
	    }
		return file;
	}


}
