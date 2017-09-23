package raytrace4s.primitives
import raytrace4s.textures.{ColorTexture, Texture}
object MaterialFactory {
  def basicMaterial(color:Color): Material = {
    new Material(new ColorTexture(color), 0.25, 0.25,0,0,0,0)
  }
  def basicMaterial(texture:Texture): Material = {
    new Material(texture, 0.25, 0.25,0,0,0,0)
  }
}