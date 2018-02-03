package raytrace4s.textures
import raytrace4s.primitives.{Color, Vector3d}
import raytrace4s.tools.JsonFields

import javax.imageio.ImageIO
import java.io.File

object TextureFactory {
  val TEXTURE_TYPE = "type"
  val CHECKER_TEXTURE = "checkered"
  val COLOR_TEXTURE = "color"
  val IMAGE_TEXTURE = "image"
  val PERLIN_TEXTURE = "perlin"
  
  val SCALE = "scale"
  
  val TEXTURE_ONE = "textureOne"
  val TEXTURE_TWO = "textureTwo"
  
  val IMAGE = "image"
  val IMAGE_ORIGIN = "origin"
  
  def load(map: Map[String, Any]): Texture = {
    val center =  map.get(JsonFields.OBJECT_CENTER_VECTOR) match {
      case Some(obj) => new Vector3d(obj.asInstanceOf[Map[String, Any]])
      case None => new Vector3d(0,0,0)
    }
      
    val rotation = (map.get(JsonFields.OBJECT_ROTATION) getOrElse Map[String, Double]().withDefaultValue(0.0)).asInstanceOf[Map[String, Double]]
    
    map(TEXTURE_TYPE) match {
      case CHECKER_TEXTURE => {
         new CheckeredTexture(
             load(map(TEXTURE_ONE).asInstanceOf[Map[String,Any]]),
             load(map(TEXTURE_TWO).asInstanceOf[Map[String,Any]]),
                 map(SCALE).asInstanceOf[Double],
                 center,
                 rotation)
      }
      case PERLIN_TEXTURE => {
         new PerlinTexture(
             load(map(TEXTURE_ONE).asInstanceOf[Map[String,Any]]),
             load(map(TEXTURE_TWO).asInstanceOf[Map[String,Any]]),
                 map(SCALE).asInstanceOf[Double],
                 center,
                 rotation)
      }
      case COLOR_TEXTURE => {
        new ColorTexture(new Color(map))
      }
      case IMAGE_TEXTURE => {
        new ImageSphereTexture(ImageIO.read(new File(map(IMAGE).asInstanceOf[String])),
                 center,
                 rotation)
      }
    }
  }
}