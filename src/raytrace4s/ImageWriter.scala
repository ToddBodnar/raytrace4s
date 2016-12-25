package raytrace4s
import primitives.Pixel
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.File

class ImageWriter(width: Integer, height: Integer, pixels: Stream[Pixel]) {
  def img = {
    val result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    pixels.foreach { pixel =>
      {
        result.setRGB(pixel.x, pixel.y, pixel.color.hex)
      }
    }
    result
  }
  def write(file: String) = {
    ImageIO.write(img, "png", new File(file + ".png"))
  }
}