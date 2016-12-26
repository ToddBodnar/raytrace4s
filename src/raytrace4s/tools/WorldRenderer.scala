package raytrace4s.tools
import raytrace4s.primitives._
import raytrace4s.shapes.Shape

class WorldRenderer(width: Int, height: Int, world: World) {
  def stream: Stream[Pixel] = {
    val horizontal = new Vector3d(4, 0, 0)
    val vertical = new Vector3d(0, 2, 0)
    val origin = new Vector3d(0, 0, 0)
    val lowerLeft = new Vector3d(-2, -1, -1)

    def render(x: Int, y: Int): Color = {
      world.fire(new Ray(origin, lowerLeft + horizontal * (x * 1.0 / width) + vertical * (y * 1.0 / height)))
    }

    (0 until width).toStream
      .flatMap { x => (0 until height).toStream.map { y => (x, y) } }
      .map { (loc: (Int, Int)) =>
        {
          new Pixel(loc._1, height - loc._2 - 1, render(loc._1, loc._2))
        }
      }
  }
}