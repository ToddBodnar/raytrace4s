package raytrace4s.primitives

class Material(baseColor: Color, diffuseAmt: Double, lightDampening: Double) {
  def getColor(ray: Ray, distance: Double, normal: Vector3d, tracer: (Ray, Int) => Color, bounces: Int): Color = {
    if (bounces <= 0) {
      new Color(0, 0, 0)
    } else {

      def getP(): Vector3d = {
        val p = new Vector3d(Math.random, Math.random, Math.random) * 2 - new Vector3d(1, 1, 1)
        if (p.squared_len >= 1) {
          getP
        } else {
          p
        }
      }
      val target = ray.pointAt(distance) + normal + getP
      new Color((new Color(0,0,0).merge(tracer(new Ray(ray.pointAt(distance), target - ray.pointAt(distance)), bounces - 1), diffuseAmt).vector*lightDampening).sqrt*baseColor.vector)
    }
  }
}