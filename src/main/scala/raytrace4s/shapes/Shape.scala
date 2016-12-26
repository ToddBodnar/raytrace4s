package raytrace4s.shapes
import raytrace4s.primitives.{ Color, Ray, Vector3d, Material }
trait Shape {
  /**
   * Calculate when the ray intersects the object, or nil if it doesn't
   */
  def intersect(ray: Ray, bounces: Int): (Double, Vector3d, Vector3d, Material)
}