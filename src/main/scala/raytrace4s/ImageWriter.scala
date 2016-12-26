package raytrace4s
import primitives.Pixel
import tools.{ WorldRenderer, Config }
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.File

class ImageWriter(config: Config, renderer: WorldRenderer) {
  def img = {
    val result = new BufferedImage(config.width, config.height, BufferedImage.TYPE_INT_RGB)
    (0 until config.width).toStream
      .flatMap { x => (0 until config.height).toStream.map { y => (x, y) } }
      .par
      .foreach(pixel =>
        {
          result.setRGB(pixel._1, config.height - pixel._2 - 1, renderer.render(pixel._1, pixel._2, config).hex)
        })
    result
  }
  def write(file: String) = {
    ImageIO.write(img, "png", new File(file + ".png"))
  }
}