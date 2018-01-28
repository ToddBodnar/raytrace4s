package raytrace4s.primitives

import raytrace4s.tools.JsonFields

class Camera(verticalFov: Double, aspectRatio: Double, origin: Vector3d, up: Vector3d, at: Vector3d, aperature: Double, focusDist: Double) {
  def this(map: Map[String, Any]){
    this(map(JsonFields.CAMERA_VERTICAL_FOV).asInstanceOf[Double],
        map(JsonFields.CAMERA_ASPECT_RATIO).asInstanceOf[Double],
        new Vector3d(map(JsonFields.CAMERA_ORIGIN_VECTOR).asInstanceOf[Map[String,Any]]),
        new Vector3d(map(JsonFields.CAMERA_UP_VECTOR).asInstanceOf[Map[String,Any]]),
        new Vector3d(map(JsonFields.CAMERA_TARGET_VECTOR).asInstanceOf[Map[String,Any]]),
        map(JsonFields.CAMERA_APERATURE).asInstanceOf[Double],
        map(JsonFields.CAMERA_FOCUS_DISTANCE).asInstanceOf[Double])
  }
  
  def theta = verticalFov * math.Pi / 180.0
  def half_height = math.tan(theta / 2.0)
  def half_width = aspectRatio * half_height
  def w = (origin - at).unit
  def u = (up cross w).unit
  def v = w cross u
  def horizontal = u.unit * 2 * half_width
  def vertical = v.unit*2 * half_height
  def lower_left_corner = origin - ( u * half_width * focusDist) - (v * half_height* focusDist) - (w* focusDist)
  
  def getRay(x: Double, y: Double): Ray = {
    val dofOffset = ( (w * (Math.random - .5)*2) + (v * (Math.random - .5)*2)).unit*aperature
    new Ray(origin+dofOffset, (lower_left_corner + (horizontal *focusDist* x) + (vertical*focusDist * y) - origin - dofOffset).unit)
  }
}