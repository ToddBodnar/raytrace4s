package raytrace4s.primitives

class Ray(val origin: Vector3d, val direction: Vector3d) {
  def pointAt(time: Double): Vector3d = origin + direction * time
}