package raytrace4s.shapes
import raytrace4s.primitives.{ Color, Ray, Vector3d, Material }
abstract class Shape(location: Vector3d, rotation: Map[String, Double]) {
  def intersect(ray: Ray, bounces: Int): (Double, Vector3d, Vector3d, Material) = {
    val (t, loc, norm, mat) = intersectInternal(ray.translate(location).rotate(rotation), bounces)
    // don't waste time reversing the rays if there was no hit
    if (t > 0)
      (t, loc + location, norm.rotateReverse(rotation), mat)
    else
      (t, loc, norm, mat)
  }
  /**
   * Calculate when the ray intersects the object, or nil if it doesn't
   */
  def intersectInternal(ray: Ray, bounces: Int): (Double, Vector3d, Vector3d, Material)
}