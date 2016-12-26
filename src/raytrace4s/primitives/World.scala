package raytrace4s.primitives
import raytrace4s.shapes.Shape

class World(val skybox: Material, val shapes: List[Shape]) {
  def this(shapes: List[Shape]) = this(new SkyMaterial(), shapes)

  def fire(ray: Ray, bouncesRemaining: Int): Color = {
    def findNearest(subShapes: List[Shape], bestSoFar: (Double, Vector3d, Vector3d, Material)): (Double, Vector3d, Vector3d, Material) = {
      if (subShapes.isEmpty) {
        bestSoFar
      } else {
        def merge(newResult: (Double, Vector3d, Vector3d, Material)) = if (newResult._1 > 0.00001 && newResult._1 < bestSoFar._1) newResult else bestSoFar
        findNearest(subShapes.tail, merge(subShapes.head.intersect(ray, bouncesRemaining)))
      }
    }
    if (bouncesRemaining <= 0) {
      new Color(0, 0, 0)
    } else {
      val result = findNearest(shapes, (Double.PositiveInfinity, new Vector3d(0, 0, 0), new Vector3d(0, 0, 0), skybox))
      result._4.getColor(ray, result._1, result._3, fire, bouncesRemaining - 1)
    }
  }
}