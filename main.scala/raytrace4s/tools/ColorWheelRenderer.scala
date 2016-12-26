package raytrace4s.tools
import raytrace4s.primitives._

class ColorWheelRenderer(width: Int, height: Int) {
  def stream: Stream[Pixel] = {
    (0 until width).toStream
      .flatMap { x => (0 until height).toStream.map { y => (x, y) } }
      .map { (loc: (Int, Int)) =>
        {
          new Pixel(loc._1, loc._2, new Color(1.0 * loc._2 / height, 1.0 * loc._1 / width, 0.2))
        }
      }
  }
}