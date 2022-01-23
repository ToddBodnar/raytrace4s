package raytrace4s.shapes
import raytrace4s.primitives.{ MaterialFactory, Material, Ray, Vector3d }
import raytrace4s.tools.{JsonFields, Randomizer}

object PolygonCollectionBulder {
  def build(center: Vector3d, rotation: Map[String, Double], polygons: List[Shape]): PolygonCollection  = {
      if (polygons.length < 10) {
          new PolygonCollection(center, rotation, polygons)
      } else {
          // "randomly" choose a dimension to divide the shapes by
      
          val randomNumber = Randomizer.randomize(polygons.length)
          val sortByDim = if (randomNumber % 3 == 0) {
              p: Shape => p.bbox()._1.x
          } else if (randomNumber % 3 == 1) {
              p: Shape => p.bbox()._1.y
          } else {
              p: Shape => p.bbox()._1.z
          }
        
          
          val (first, second) = polygons.sortBy(sortByDim).splitAt(polygons.length / 2)
          
          new PolygonCollection(center, rotation, List(build(center, rotation, first), build(center, rotation, second)))
      }
  }
}

class PolygonCollection(val center: Vector3d, rotation: Map[String, Double], val polygons: List[Shape]) extends Shape(center, rotation) {

  def intersectInternal(ray: Ray, bounces: Int): Option[(Double, Vector3d, Vector3d, Material)] = {
     val intersections = polygons
        .filter(p => p.inBbox(ray))
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