package raytrace4s

import scala.App
import primitives._
import tools._

object Main extends App {
  val width = 100
  val height = 50
  new ImageWriter(width, height,new ColorWheelRenderer(width,height).stream).write("test")
}
