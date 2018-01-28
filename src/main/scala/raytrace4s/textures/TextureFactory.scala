package raytrace4s.textures
import raytrace4s.primitives.{Color, Vector3d}
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
    map(TEXTURE_TYPE) match {
      case CHECKER_TEXTURE => {
         new CheckeredTexture(
             load(map(TEXTURE_ONE).asInstanceOf[Map[String,Any]]),
             load(map(TEXTURE_TWO).asInstanceOf[Map[String,Any]]),
                 map(SCALE).asInstanceOf[Double])
      }
      case PERLIN_TEXTURE => {
         new PerlinTexture(
             load(map(TEXTURE_ONE).asInstanceOf[Map[String,Any]]),
             load(map(TEXTURE_TWO).asInstanceOf[Map[String,Any]]),
                 map(SCALE).asInstanceOf[Double])
      }
      case COLOR_TEXTURE => {
        new ColorTexture(new Color(map))
      }
      case IMAGE_TEXTURE => {
        new ImageSphereTexture(ImageIO.read(new File(map(IMAGE).asInstanceOf[String])), new Vector3d(map(IMAGE_ORIGIN).asInstanceOf[Map[String,Any]]))
      }
    }
  }
}