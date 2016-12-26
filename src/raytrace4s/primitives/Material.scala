package raytrace4s.primitives

class Material(baseColor: Color, diffuseAmt: Double, lightDampening: Double, reflectionAmount: Double, reflectionFuzzy: Double) {
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

      def reflect(incomming: Vector3d, normal: Vector3d): Vector3d = {
        incomming - (normal * ((incomming dot normal) * 2))
      }

      val target = ray.pointAt(distance) + normal + getP
      val diffusedColor = new Color((new Color(0, 0, 0).merge(tracer(new Ray(ray.pointAt(distance), target - ray.pointAt(distance)), bounces - 1), diffuseAmt).vector * lightDampening).sqrt * baseColor.vector)

      if (reflectionAmount == 0) {
        diffusedColor
      } else {
        val mirrored = diffusedColor.merge(tracer(new Ray(ray.pointAt(distance), reflect(ray.direction, normal) + (getP * reflectionFuzzy)), bounces - 1), 1 - reflectionAmount)
        mirrored
      }
    }
  }
}