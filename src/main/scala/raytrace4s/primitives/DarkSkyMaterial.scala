package raytrace4s.primitives

class DarkSkyMaterial() extends SkyMaterial() {
  //todo: merge this with sky material / make more generic
  override def getColor(ray: Ray, distance: Double, normal: Vector3d, tracer: (Ray, Int) => Color, bounces: Int): Color = {
    new Color(0.0, 0.0, 0.0)
  }
}