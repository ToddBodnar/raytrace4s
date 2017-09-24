package raytrace4s.textures
import java.awt.image.BufferedImage
import raytrace4s.primitives.{Color, Vector3d}

class ImageSphereTexture(image: BufferedImage, origin: Vector3d) extends Texture {
  def colorAt(point: Vector3d): Color = {
    val centered = (point - origin).unit
    val u = 0.5 - Math.atan2(centered.z, centered.x) / 2 / Math.PI
    val v = 0.5 - Math.asin(centered.y) / Math.PI
    
    val uPixel = u * image.getWidth
    val vPixel = v * image.getHeight
    
    //todo: interpolate
    new Color(image.getRGB(Math.min(Math.round(uPixel.toFloat), image.getWidth - 1), Math.min(Math.round(vPixel.toFloat), image.getHeight - 1)))
  }
}