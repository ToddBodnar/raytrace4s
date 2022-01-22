package raytrace4s
import primitives.Pixel
import me.tongfei.progressbar._
import tools.{ Renderer, Config }
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.File

class ImageWriter(config: Config, renderer: Renderer) {
  def img = {
    val progressBar = new ProgressBar("Rendering image",config.height)
    progressBar.start
    val result = new BufferedImage(config.width, config.height, BufferedImage.TYPE_INT_RGB)
    (0 until config.height).toStream
      .par
      .foreach(y =>
        {
          for (x <- 0 until config.width){
            result.setRGB(x, config.height - y - 1, renderer.render(x, y, config).normalize.hex)
          }
          progressBar.step
        })
    progressBar.stop
    result
  }
  def write(file: String) = {
    ImageIO.write(img, "png", new File(file + ".png"))
  }
}