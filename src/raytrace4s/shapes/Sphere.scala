package raytrace4s.shapes
import raytrace4s.primitives.{ Color, Ray, Vector3d }

class Sphere(center: Vector3d, radius: Double, baseColor: Color) extends Shape {
  def intersect(ray: Ray, tracer: (Ray) => Color, bounces: Int): (Double, Color) = {
    def centeredCollision(centeredCenter: Vector3d): Double = {
      val a = ray.direction dot ray.direction
      val b = centeredCenter dot ray.direction * 2
      val c = (centeredCenter dot centeredCenter) - radius * radius
      val descriminant = b * b - a * c * 4
      if (descriminant < 0) {
        -1
      } else {
        (-b - math.sqrt(descriminant)) / 2 / a
      }
    }

    def normal(distance: Double): Vector3d = {
      (ray.pointAt(distance) - center).unit
    }
    //keep these two lines separate for later stuff
    val distance = centeredCollision(ray.origin - center)
    //(distance, baseColor)
    //render normals
    (distance, new Color(normal(distance) * .5 + .5))
  }
}