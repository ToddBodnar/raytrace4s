package raytrace4s.primitives
import raytrace4s.textures.{ColorTexture, Texture, TextureFactory}
import raytrace4s.tools.JsonFields

object MaterialFactory {
  def load(map: Map[String, Any]): Material = {
    val materialType = map(JsonFields.MATERIAL_TYPE)
    materialType match {
      case JsonFields.MATERIAL_BASIC_COLOR => {
        basicMaterial(new Color(map))
      }
      case JsonFields.MATERIAL_BASIC_TEXTURE => {
        basicMaterial(TextureFactory.load(map(JsonFields.TEXTURE_FIELD).asInstanceOf[Map[String,Any]]))
      }
      case JsonFields.MATERIAL_LIGHT => {
        new LightMaterial(new Color(map))
      }
      case JsonFields.MATERIAL_CUSTOM => {
        //todo: implement fully
        basicMaterial(new Color(1.0,1.0,1.0))
      }
    }
  }
  def basicMaterial(color:Color): Material = {
    new Material(new ColorTexture(color), 0.25, 0.25,0,0,0,0)
  }
  def basicMaterial(texture:Texture): Material = {
    new Material(texture, 0.25, 0.25,0,0,0,0)
  }
}