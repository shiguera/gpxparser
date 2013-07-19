package com.mlab.tesis.java.gpx;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class Util {
	
	/**
	 * Formatea un double a los digitos y precisión deseados, 
	 * sustituyendo la coma decimal por el punto decimal.<br/>
	 * Los números se redondeán al número de decimales pedido.
	 * @param value double valor
	 * @param digits número total de dígitos
	 * @param decimals número de decimales
	 * @return cadena con el número formateado xxx.xxx
	 * Si value es NaN o infinito, o digits<=0 o decimals<0 
	 * arroja IllegalArgumentException
	 */
	static public String doubleToString(double value, int digits, int decimals) {
		// System.out.println(value);
		if(Double.isNaN(value) || Double.isInfinite(value) || digits <= 0 || decimals <0) {
			throw new IllegalArgumentException();
		}
		StringBuilder builder = new StringBuilder();
		builder.append("%");
		builder.append(String.format("%d", digits));;
		builder.append(".");
		builder.append(String.format("%d", decimals));;
		builder.append("f");
		
		String cad = String.format(builder.toString(), value).replace(',', '.').trim();
		return cad;
	}
	/**
	 * Extrae la fecha del vídeo a partir del nombre del fichero
	 * en formato yyyyMMdd_HHmmss.mp4
	 * @param file fichero de video a comprobar
	 * @return long con la fecha o -1l si hay error 
	 */
	public static long startTimeFromFilename(File file) {
		String filename = fileNameWithoutExtension(file);
		Date date=null;
		long t = -1l;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		try {
			date=df.parse(filename);
			t = date.getTime();
		} catch (ParseException e1) {
			t = -1l;
		}
		return t;
	}
	
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
     * Formatea una fecha 'yyyy-MM-ddThh:mm:ss.ssZ'
     * @param t tiempo en milisegundos de la fecha
     * @return 'yyyy-MM-ddThh:mm:ss.ssZ'
     */
    static public String dateTimeToStringGpxFormat(long t) {
    	SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.ss'Z'");
   		format.setTimeZone(TimeZone.getTimeZone("UTC"));
    	return format.format(new Date(t));
    }
    static public long parseGpxDate(String cadGpxDateTime) {
    	SimpleDateFormat format = null;
    	if(cadGpxDateTime.length()==23) {
    		format= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.ss'Z'");	
    	} else if (cadGpxDateTime.length()==22) {
        	format= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.s'Z'");    		
    	} else if (cadGpxDateTime.length()==20) {
        	format= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");    		
    	}
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date date;
		long t = -1l;
		try {
			date = format.parse(cadGpxDateTime);
			t = date.getTime();
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		return t;
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
     * Extrae la extensión de un fichero
     */
    public static String getFileExtension(File file) {
    	String ext = "";
    	int index = file.getName().lastIndexOf('.');
    	if(index != -1) {
        	ext = file.getName().substring(index+1, file.getName().length());    		
    	}
    	//System.out.println(ext);
    	return ext;
    }
    
    /**
	 * Devuelve el nombre del fichero sin extensión
	 * @param file Fichero
	 * @return String Nombre sin extensión del fichero
	 */
	public static String fileNameWithoutExtension(File file) {
		String filename = file.getName();
		String ext = Util.getFileExtension(file);
		try {
			int end = filename.length()-ext.length()-1;
			filename = filename.substring(0,end);
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			filename = "";
		}
		return filename;		
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
	 * @return String con el fichero leido o una cadena vacía
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
