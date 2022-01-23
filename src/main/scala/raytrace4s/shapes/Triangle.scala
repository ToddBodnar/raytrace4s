package raytrace4s.shapes
import raytrace4s.primitives.{ MaterialFactory, Material, Ray, Vector3d }
import raytrace4s.tools.JsonFields

class Triangle(v1: Vector3d, v2: Vector3d, v3: Vector3d, material: Material, center: Vector3d, rotation: Map[String, Double]) extends Shape(center, rotation)  {
  def this(map: Map[String, Any]){
    this(new Vector3d(map(JsonFields.TRIANGLE_V1).asInstanceOf[Map[String, Any]]), 
         new Vector3d(map(JsonFields.TRIANGLE_V2).asInstanceOf[Map[String, Any]]), 
         new Vector3d(map(JsonFields.TRIANGLE_V3).asInstanceOf[Map[String, Any]]), 
         MaterialFactory.load(map(JsonFields.OBJECT_MATERIAL).asInstanceOf[Map[String, Any]]),
         new Vector3d(map(JsonFields.OBJECT_CENTER_VECTOR).asInstanceOf[Map[String, Any]]), 
         map(JsonFields.OBJECT_ROTATION).asInstanceOf[Map[String, Double]])
  }
  
  def this(vectors: List[Vector3d], material: Material, center: Vector3d, rotation: Map[String, Double]) {
    this(vectors(0), vectors(1), vectors(2), material, center, rotation)
  }
  
  val edge1 = v2 - v1
  val edge2 = v3 - v1
    
  //see https://www.khronos.org/opengl/wiki/Calculating_a_Surface_Normal
  val normal = new Vector3d(edge1.y * edge2.z - edge1.z * edge2.y,
                            edge1.z * edge2.x - edge1.x * edge2.z,
                            edge1.x * edge2.y - edge1.y * edge2.x)
                            
  val flippedNormal = normal * (-1.0)
  
  val bboxMin = v1.min(v2).min(v3) + (-0.001)
  val bboxMax = v1.max(v2).max(v3) + 0.001
  
  def intersectInternal(ray: Ray, bounces: Int): Option[(Double, Vector3d, Vector3d, Material)] = {
    // see https://www.scratchapixel.com/lessons/3d-basic-rendering/ray-tracing-rendering-a-triangle/moller-trumbore-ray-triangle-intersection
    var p = ray.direction.cross(edge2)
    
    var determinate = edge1 dot p
    var flipped = false
    
    if ( determinate <= 0) {
      p = ray.direction.cross(edge1)
      determinate = edge2 dot p
      flipped = true
    }
    
    val tv = ray.origin - v1
    val u = tv dot p / determinate
    
    if (u < 0 || u > 1) {
      return None
    }
      
    val q = tv cross (if (flipped) edge2 else edge1)
    
    val v = ray.direction dot q / determinate
    
    val t = (if (flipped) edge1 else edge2) dot q / determinate
    
    if (v < 0 || u + v > 1) {
      None
    }  
    else
      Some((t, ray.pointAt(t), normal * (if (flipped) -1 else 1), material))
  }
  
  def bbox(): (Vector3d, Vector3d) = {
    (bboxMin, bboxMax)
  }
}