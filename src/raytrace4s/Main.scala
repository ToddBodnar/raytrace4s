package raytrace4s

import scala.App
import primitives._
import tools._
import shapes._

object Main extends App {
  val width = 1000
  val height = 500

  val shapes = List(new Sphere(new Vector3d(0, 0, -1), 0.5, new Color(1, 0, 0)),
    new Sphere(new Vector3d(0.35, 0, -.75), 0.35, new Color(0, 0, 0)),
    new Sphere(new Vector3d(-0.35, 0.35, -1.05), 0.35, new Color(0, 1, 0)))

  new ImageWriter(width, height, new WorldRenderer(width, height, shapes).stream).write("test")
}
