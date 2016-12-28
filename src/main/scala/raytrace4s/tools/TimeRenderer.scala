package raytrace4s.tools
import raytrace4s.primitives.Color

/**
 * Visualizes pixel render time
 */
class TimeRenderer(renderer:Renderer, maxTime: Double) extends Renderer{
  def render(x: Int, y: Int, config: Config): Color = {
    val start = System.currentTimeMillis()
    val Color = renderer.render(x,y,config)
    val end = System.currentTimeMillis()
    val c = if ( end - start > maxTime) 1 else (end - start)/ maxTime
    new Color(c,c,c)
  }
}