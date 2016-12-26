package raytrace4s
import primitives.Pixel
import tools.WorldRenderer
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.File

class ImageWriter(width: Integer, height: Integer, renderer: WorldRenderer) {
  def img = {
    val result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    (0 until width).toStream
      .flatMap { x => (0 until height).toStream.map { y => (x, y) } }
      .par
      .foreach(pixel =>
        {
          result.setRGB(pixel._1, height - pixel._2 - 1, renderer.render(pixel._1, pixel._2).hex)
        })
    result
  }
  def write(file: String) = {
    ImageIO.write(img, "png", new File(file + ".png"))
  }
}