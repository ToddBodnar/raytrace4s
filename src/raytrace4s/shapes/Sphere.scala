package raytrace4s.shapes
import raytrace4s.primitives.{ Material, Ray, Vector3d }

class Sphere(center: Vector3d, radius: Double, material: Material) extends Shape {
  def intersect(ray: Ray, bounces: Int): (Double, Vector3d, Vector3d, Material) = {
    def centeredCollision(centeredCenter: Vector3d): Double = {
      val a = ray.direction dot ray.direction
      val b = (centeredCenter dot ray.direction)
      val c = (centeredCenter dot centeredCenter) - radius * radius
      val descriminant = b * b - (a * c)
      if (descriminant <= 0.0001) {
        -1
      } else {
        if ((-b - math.sqrt(descriminant)) / a > 0) {
          (-b - math.sqrt(descriminant)) / a
        } else {
          (-b + math.sqrt(descriminant)) / a
        }
      }
    }

    val distance = centeredCollision(ray.origin - center)

    def normal(): Vector3d = {
      (ray.pointAt(distance) - center) / radius
    }
    //keep these two lines separate for later stuff
    //(distance, baseColor.merge(getNextBounce, diffuseAmount))
    (distance, ray.pointAt(distance), normal, material)
    //render normals
    //(distance, new Color((normal +1 )/2))
  }
}