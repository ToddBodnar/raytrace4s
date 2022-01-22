package raytrace4s.shapes
import raytrace4s.primitives.{ MaterialFactory, Material, Ray, Vector3d }
import raytrace4s.tools.JsonFields

class PolygonCollection(val center: Vector3d, rotation: Map[String, Double], val polygons: List[Triangle]) extends Shape(center, rotation) {

  def intersectInternal(ray: Ray, bounces: Int): Option[(Double, Vector3d, Vector3d, Material)] = {
     val intersections = polygons
        .map(p => p.intersectInternal(ray, bounces))
        .filter(r => !r.isEmpty)
        // get closest collision
        .filter(r => r.get._1 > 0.000001)
      
     if (intersections.isEmpty) {
        None
     } else {
        intersections.reduceLeft((a, b) => if (a.get._1 < b.get._1) a else b)
     }  
  }
  
  lazy val bboxStorage = _bbox()
  
  def bbox(): (Vector3d, Vector3d) = bboxStorage
  
  def _bbox(): (Vector3d, Vector3d) = {
  
    if (polygons.isEmpty) 
      (new Vector3d(-0, -0, -0), new Vector3d(0, 0, 0))
    else {
       (polygons.map(p => p.bbox()).flatMap(a => List(a._1,a._2)).reduceLeft(_.min(_)), 
       polygons.map(p => p.bbox()).flatMap(a => List(a._1,a._2)).reduceLeft(_.max(_)))
    }
  }
}