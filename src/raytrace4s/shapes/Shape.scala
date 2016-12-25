package raytrace4s.shapes
import raytrace4s.primitives.{ Color, Ray }
trait Shape {
  /**
   * Calculate when the ray intersects the object, or nil if it doesn't
   */
  def intersect(ray: Ray, world: List[Shape], bounces: Int): (Double, Color)
}