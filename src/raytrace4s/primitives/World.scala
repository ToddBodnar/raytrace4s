package raytrace4s.primitives
import raytrace4s.shapes.Shape

class World(val skybox: (Ray) => Color, val shapes: List[Shape]) {
  def this(shapes: List[Shape]) = this((ray: Ray) => new Color(0.5, 0.7, 1.0).merge(new Color(1, 1, 1), (ray.direction.unit.y + 1) / 2), shapes)

  def fire(ray: Ray): Color = {
    def findNearest(subShapes: List[Shape], bestSoFar: (Double, Color)): (Double, Color) = {
      if (subShapes.isEmpty) {
        bestSoFar
      } else {
        def merge(newResult: (Double, Color)) = if (newResult._1 > 0 && newResult._1 < bestSoFar._1) newResult else bestSoFar
        findNearest(subShapes.tail, merge(subShapes.head.intersect(ray, fire, 0)))
      }
    }

    findNearest(shapes, (Double.PositiveInfinity, skybox(ray)))._2
  }
}