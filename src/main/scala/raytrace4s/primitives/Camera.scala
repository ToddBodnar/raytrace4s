package raytrace4s.primitives

class Camera(verticalFov: Double, aspectRatio: Double, origin: Vector3d, up: Vector3d, at: Vector3d) {
  def theta = verticalFov * math.Pi / 180.0
  def half_height = math.tan(theta / 2.0)
  def half_width = aspectRatio * half_height
  def w = (origin - at).unit
  def u = (up cross w).unit
  def v = w cross u
  def horizontal = u * 2 * half_width
  def vertical = v*2 * half_height
  def lower_left_corner = origin - ( u * half_width) - (v * half_height) - w
  
  def getRay(x: Double, y: Double): Ray = new Ray(origin, (lower_left_corner + (horizontal * x) + (vertical * y) - origin).unit)
}