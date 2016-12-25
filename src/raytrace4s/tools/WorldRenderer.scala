package raytrace4s.tools
import raytrace4s.primitives._
import raytrace4s.shapes.Shape

class WorldRenderer(width: Int, height: Int, skybox: (Ray) => Color, world: List[Shape]) {
  /**
   * A default world renderer with a simple sky box
   */
  def this(width: Int, height: Int, world: List[Shape]) = {
    this(width, height, (ray: Ray) => new Color(0.5, 0.7, 1.0).merge(new Color(1, 1, 1), (ray.direction.unit.y + 1) / 2), world)
  }
  def stream: Stream[Pixel] = {
    val horizontal = new Vector3d(4, 0, 0)
    val vertical = new Vector3d(0, 2, 0)
    val origin = new Vector3d(0, 0, 0)
    val lowerLeft = new Vector3d(-2, -1, -1)

    def render(x: Int, y: Int): Color = {
      val ray = new Ray(origin, lowerLeft + horizontal * (x * 1.0 / width) + vertical * (y * 1.0 / height))
      def lookup(shapes: List[Shape], bestSoFar: (Double, Color)): (Double, Color) =
        {
          if (shapes.isEmpty) {
            bestSoFar
          } else {
            def merge(newResult: (Double, Color)) = if (newResult._1 > 0 && newResult._1 < bestSoFar._1) newResult else bestSoFar
            lookup(shapes.tail, merge(shapes.head.intersect(ray, world, 0)))
          }
        }
      lookup(world, (Double.PositiveInfinity, skybox(ray)))._2
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