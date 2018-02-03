package raytrace4s.shapes
import raytrace4s.primitives.{ Color, Ray, Vector3d, Material }
abstract class Shape(location: Vector3d, rotation: Map[String, Double]) {
  def intersect(ray: Ray, bounces: Int): (Double, Vector3d, Vector3d, Material) = {
    intersectInternal(ray.translate(location).rotate(rotation), bounces)
  }
  /**
   * Calculate when the ray intersects the object, or nil if it doesn't
   */
  def intersectInternal(ray: Ray, bounces: Int): (Double, Vector3d, Vector3d, Material)
}