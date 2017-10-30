package raytrace4s.primitives

class LightMaterial(color: Color) extends Material(color, 0.0, 0, 0, 0, 0, 0) {
  override def getColor(ray: Ray, distance: Double, normal: Vector3d, tracer: (Ray, Int) => Color, bounces: Int): Color = {
    //todo: dropoff
    color
  }
}