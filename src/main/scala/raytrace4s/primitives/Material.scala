package raytrace4s.primitives
import raytrace4s.textures.{ColorTexture, Texture}
import raytrace4s.tools.Randomizer

class Material(baseTexture: Texture, diffuseAmt: Double, lightDampening: Double, reflectionAmount: Double, reflectionFuzzy: Double, transparency: Double, refractionIndex: Double) {
  def this(baseColor: Color, diffuseAmt: Double, lightDampening: Double, reflectionAmount: Double, reflectionFuzzy: Double, transparency: Double, refractionIndex: Double) {
    this(new ColorTexture(baseColor), diffuseAmt: Double, lightDampening: Double, reflectionAmount: Double, reflectionFuzzy: Double, transparency: Double, refractionIndex: Double);
  }
  
  def getColor(ray: Ray, distance: Double, normal: Vector3d, tracer: (Ray, Int) => Color, bounces: Int): Color = {
    if (bounces <= 0) {
      new Color(0, 0, 0)
    } else {

      def fireRay(v: Vector3d) = tracer(new Ray(ray.pointAt(distance), v), bounces - 1)

      def getP(): Vector3d = {
        val p = new Vector3d(Math.random, Math.random, Math.random) * 2 - new Vector3d(1, 1, 1)
        if (p.squared_len >= 1) {
          getP
        } else {
          p
        }
      }

      def reflect(): Vector3d = {
        ray.direction - (normal * ((ray.direction dot normal) * 2))
      }

      def refract(ni_over_nt: Double, normal: Vector3d): Vector3d = {
        val descriminant = 2 - ni_over_nt * ni_over_nt * (1 - math.pow(ray.direction.unit dot normal.unit, 2))
        if (descriminant >= 0) {
          //ray.direction * ni_over_nt  - (normal * ((ray.direction.unit dot normal) * ni_over_nt * math.sqrt(descriminant)))
          (ray.direction.unit - (normal * (ray.direction.unit dot normal.unit))) * ni_over_nt - (normal * math.sqrt(descriminant))
        } else {
          reflect
        }
      }
      val target = ray.pointAt(distance) + normal + getP

      def diffusedColor(): Color = new Color((new Color(0, 0, 0).merge(fireRay(target - ray.pointAt(distance)), diffuseAmt).vector * lightDampening).sqrt * baseTexture.colorAt(ray.pointAt(distance)).vector)

      def reflectionColor(): Color = {
        if (reflectionAmount > 0) {
          fireRay(reflect + (getP * reflectionFuzzy))
        } else {
          new Color(0, 0, 0)
        }
      }

      def refractedColor(): Color = {
        if (transparency == 0) {
          new Color(0, 0, 0)
        } else {
          def getRayDirection(): Vector3d = {
            if ((ray.direction.unit dot normal) > 0) {
              refract(refractionIndex, normal * -1)
            } else {
              refract(1 / refractionIndex, normal)
            }
          }
          val schlick = {
            val cos = (if ((ray.direction.unit dot normal) > 0) refractionIndex else -1) * (ray.direction.unit dot normal)
            val r0 = math.pow((1 - refractionIndex) / (1 + refractionIndex), 2)
            r0 + (1 - r0) * math.pow(1 - cos, 5)
          }
          if (schlick < 0 && Randomizer.randDouble(ray.direction.y + ray.direction.z) < schlick)
            fireRay(reflect + (getP * reflectionFuzzy))
          else
            fireRay(getRayDirection)
          //fireRay(getRayDirection).merge(fireRay(reflect + (getP * reflectionFuzzy)), 1 - schlick)
        }
      }
    

      if (reflectionAmount > 0 && Randomizer.randDouble(ray.direction.x, ray.direction.y) < reflectionAmount)
        reflectionColor()
      else if (transparency > 0 && Randomizer.randDouble(ray.direction.x, ray.direction.z) < transparency)
        refractedColor()
      else
        diffusedColor()
        
      //diffusedColor.merge(reflectionColor, 1 - reflectionAmount).merge(refractedColor, 1 - transparency)
    }
  }
}