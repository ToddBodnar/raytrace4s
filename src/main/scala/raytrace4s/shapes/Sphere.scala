package raytrace4s.shapes
import raytrace4s.primitives.{ MaterialFactory, Material, Ray, Vector3d }
import raytrace4s.tools.JsonFields

class Sphere(val center: Vector3d, val radius: Double, material: Material, rotation: Map[String, Double]) extends Shape(center, rotation) {
  def this(map: Map[String, Any]){
    this(new Vector3d(map(JsonFields.OBJECT_CENTER_VECTOR).asInstanceOf[Map[String, Any]]), 
         map(JsonFields.OBJECT_SCALE).asInstanceOf[Double], 
         MaterialFactory.load(map(JsonFields.OBJECT_MATERIAL).asInstanceOf[Map[String, Any]]),
         map(JsonFields.OBJECT_ROTATION).asInstanceOf[Map[String, Double]])
  }

  def intersectInternal(ray: Ray, bounces: Int): Option[(Double, Vector3d, Vector3d, Material)] = {
    def centeredCollision(centeredCenter: Vector3d): Option[Double] = {
      val a = ray.direction dot ray.direction
      val b = (centeredCenter dot ray.direction)
      val c = (centeredCenter dot centeredCenter) - radius * radius
      val descriminant = b * b - (a * c)
      if (descriminant <= 0.0001) {
        None
      } else {
        if ((-b - math.sqrt(descriminant)) / a > 0) {
          Some((-b - math.sqrt(descriminant)) / a)
        } else {
          Some((-b + math.sqrt(descriminant)) / a)
        }
      }
    }

    val distance = centeredCollision(ray.origin)

    distance match {
        case None => None
        case Some(dist) => {
            def normal(): Vector3d = {
              ((ray.pointAt(dist)) / radius)
            }
            //keep these two lines separate for later stuff
            //(distance, baseColor.merge(getNextBounce, diffuseAmount))
            Some((dist, ray.pointAt(dist), normal, material))
            //render normals
            //(distance, new Color((normal +1 )/2))
        }
    }
    
  }
  
  def bbox(): (Vector3d, Vector3d) = {
    (new Vector3d(-radius, -radius, -radius), new Vector3d(radius, radius, radius))
  }
}