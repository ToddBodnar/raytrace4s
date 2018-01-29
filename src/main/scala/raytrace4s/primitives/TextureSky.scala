package raytrace4s.primitives
import raytrace4s.textures.{Texture, TextureFactory}
import raytrace4s.tools.JsonFields

class TextureSkyMaterial(texture: Texture) extends SkyMaterial{
  def this(map: Map[String, Any]){
    this(TextureFactory.load(map(JsonFields.TEXTURE_FIELD).asInstanceOf[Map[String, Any]]))
  }
  
  override def getColor(ray: Ray, distance: Double, normal: Vector3d, tracer: (Ray, Int) => Color, bounces: Int): Color = {
    // we're on the "inside" of the sphere, but let's not mirror it, so it matches the input textures
    texture.colorAt(ray.direction * new Vector3d(-1,1,1))
  }
}