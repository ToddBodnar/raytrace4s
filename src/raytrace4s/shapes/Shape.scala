package raytrace4s.shapes
import raytrace4s.primitives.{ Color, Ray }
trait Shape {
  /**
   * Calculate when the ray intersects the object, or nil if it doesn't
   */
  def intersect(ray: Ray, tracer: (Ray) => Color, bounces: Int): (Double, Color)
}