.. GpxFactory

==========
GpxFactory
==========

Es la clase base abstracta para las distintas factories. Tiene métodos para leer y parsear documentos GpxDocument. La diferencia en los documentos GpxDocument está en el tipo de WayPoint que utilizan. Hay tres topos de WayPoint:

* SimpleWayPoint : lon, lat, alt. Se crean y se parsean con una SimpleGpxFactory.
* AndroidWayPoint : Añade extensiones a SimpleWayPoint para *speed, bearing, accuracy*. Para crear y parsear se necesita una AndroidGpxFactory
* ExtendedWayPoint : Add extensions to AndroidWayPoint for *ax, ay, az, pressure*. Se crean y se parsean con una ExtendedGpxFactory.
 
