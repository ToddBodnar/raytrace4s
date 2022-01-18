package raytrace4s.shapes
import raytrace4s.primitives.{ Color, Ray, Vector3d, Material, MaterialFactory }
abstract class Shape(location: Vector3d, rotation: Map[String, Double]) {
  val defaultMaterial = MaterialFactory.basicMaterial(new Color(0,0,0))
  
  //todo: make returned object optional
  def intersect(ray: Ray, bounces: Int): Option[(Double, Vector3d, Vector3d, Material)] = {
    val transformed = ray.translate(location).rotate(rotation)
    
    if (!inBbox(transformed))
      return None
    
    intersectInternal(transformed, bounces) match {
      case None => None
      case Some((t: Double, loc: Vector3d, norm: Vector3d, mat: Material)) => {
        // don't waste time reversing the rays if there was no hit
        
        if (t > 0)
          Some((t, loc + location, norm.rotateReverse(rotation), mat))
        else
          Some((t, loc, norm, mat))
      }
    }
  }
  /**
   * Calculate when the ray intersects the object, or nil if it doesn't
   */
  def intersectInternal(ray: Ray, bounces: Int): Option[(Double, Vector3d, Vector3d, Material)]
  
  def bbox(): (Vector3d, Vector3d)
  
  def inBbox(ray: Ray): Boolean = {
    val (bboxMin, bboxMax) = bbox()
    def calcTimes(min: Double, max: Double, origin: Double, direction: Double): (Double, Double) = {
      if(direction > 0)
        ((min - origin) / direction, (max - origin) / direction)
      else
        ((max - origin) / direction, (min - origin) / direction)
    }
    
    var (minTX, maxTX) = calcTimes(bboxMin.x, bboxMax.x, ray.origin.x, ray.direction.x)
    var (minTY, maxTY) = calcTimes(bboxMin.y, bboxMax.y, ray.origin.y, ray.direction.y)
    
    val minTP = Math.max(minTX, minTY)
    val maxTP = Math.min(maxTX, maxTY)
    
    if (minTP > maxTP)
      return false
    
    var (minTZ, maxTZ) = calcTimes(bboxMin.z, bboxMax.z, ray.origin.z, ray.direction.z)
    
    val minT = Math.max(minTZ, minTP)
    val maxT = Math.min(maxTZ, maxTP)
    
    if (minT > maxT)
      return false
    else
      true
  }
}