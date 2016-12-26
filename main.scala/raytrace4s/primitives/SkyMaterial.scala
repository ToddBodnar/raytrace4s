package raytrace4s.primitives

class SkyMaterial() extends Material(new Color(0, 0, 0), 0.0, 0, 0, 0) {
  override def getColor(ray: Ray, distance: Double, normal: Vector3d, tracer: (Ray, Int) => Color, bounces: Int): Color = {
    new Color(0.5, 0.7, 1.0).merge(new Color(1, 1, 1), (ray.direction.unit.y + 1) / 2)
  }
}