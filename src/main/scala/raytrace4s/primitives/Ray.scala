package raytrace4s.primitives

import raytrace4s.tools.JsonFields

class Ray(val origin: Vector3d, val direction: Vector3d) {
  def pointAt(time: Double): Vector3d = origin + (direction * time)
  
  def translate(location: Vector3d): Ray = new Ray(origin - location, direction)
  
  def rotate(yaw: Double, pitch: Double, roll: Double): Ray = {
    new Ray(origin.rotate(yaw, pitch, roll), direction.rotate(yaw, pitch, roll))
  }
  
  def rotate(map: Map[String, Double]): Ray = {
    rotate(map(JsonFields.ROTATION_YAW), map(JsonFields.ROTATION_PITCH), map(JsonFields.ROTATION_ROLL))
  }
}