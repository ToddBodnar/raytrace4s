package raytrace4s.tools
import raytrace4s.primitives.Color

trait Renderer {
  def render(x: Int, y: Int, config: Config): Color
}