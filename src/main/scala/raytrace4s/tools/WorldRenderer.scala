package raytrace4s.tools
import raytrace4s.primitives._
import raytrace4s.shapes.Shape

class WorldRenderer(camera:Camera, world: World) extends Renderer{

  def render(x: Int, y: Int, config: Config): Color = {

    val horizontal = new Vector3d(4, 0, 0)
    val vertical = new Vector3d(0, 2, 0)
    val origin = new Vector3d(0, 0, 0)
    val lowerLeft = new Vector3d(-2, -1, -1)

    def render(xCt: Int, yCt: Int, acc: BigVector3d): BigVector3d = {
      if (yCt >= config.sqrtSubSamples) {
        acc
      } else if (xCt >= config.sqrtSubSamples) {
        render(0, yCt + 1, acc)
      } else {
        render(xCt + 1, yCt, acc + (world.fire(camera.getRay((x + 1.0 * xCt / config.sqrtSubSamples - 0.5) / config.width , (y + 1.0 * yCt / config.sqrtSubSamples - 0.5) / config.height), config.maxBounces).vector.bigVector / config.sqrtSubSamples / config.sqrtSubSamples))
      }
    }
    new Color(render(0, 0, new BigVector3d(BigDecimal(0.0), BigDecimal(0.0), BigDecimal(0.0))).littleVector())
  }

}