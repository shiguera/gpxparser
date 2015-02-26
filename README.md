GpxParser
=========

**Version 3.3**

Librería de utilidades para trabajar con documentos GPX

GpxParser proporciona clases para poder crear, manipular, leer y escribir documentos GPX compuestos de WayPoints, Routes y Tracks.

Proporciona un mecanismo para manipular ExtendedWayPoints. Se trata de WayPoints que utilizan el mecanismo de extensiones previsto en el lenguaje para añadir a la información de posición de cada punto, información adicional procedente de sensores, u otra que se considere de interés.

Se puede consultar el javadoc en:

<a href='http://shiguera.github.io/gpxparser/' target='_blank'>GpxParser javadoc</a>


GpxFactory
----------
Es la clase que nos da entrada a los demás elementos de la librería. Es una clase abstracta, 
algunos de cuyos métodos serán implementados por las clases específicas derivadas. A la fecha de escribir este documento eran SimpleGpxFactory, AndroidGpxFactory, ExtendedGpxFactory y 
ClinometerGpxFactory.

La clase GpxFactory tiene un método estático que permite obtener instancias de GpxFactory del tipo adecuado. Para obtener una instancia de GpxFactory, por ejemplo del tipo ClinometerGpxFactory, se hace de la siguiente manera:

	GpxFactory factory = GpxFactory.getFactory(Type.ClinometerGpxFactory);

Los métodos de las clases GpxFactory son:

- **readGpxDocument(File gpxFile):** Lee un fichero en formato gpx y devuelve un GpxDocument o null



GpxDocument
-----------
El interface GpxDocument encapsula el contenido de un archivo en formato *gpx*. Un GpxDocument está formado por un elemento *metadata*, una colección de *WayPoints*, una colección de *Routes* y una colección de *Tracks*. 

Dispone de métodos para manipular dichos elementos. También devuelve una referencia al objeto *org.w3c.dom.Document* generado a partir del documento *gpx*.

Para leer un fichero GPX se utiliza el método *readGpxDocument()* de la clase GpxFactory que estemos utilizando. Por ejemplo, si estamos utilizando una SimpleGpxFactory para leer documentos GPX estándar, sin ExtendedWayPoint's podemos hacer:

	GpxFactory factory = GpxFactory.getFactory(Type.ClinometerGpxFactory);
	File gpxFile = new File("path_to_gpx_file");
	GpxDocument doc = factory.readGpxDocument(gpxFile);









