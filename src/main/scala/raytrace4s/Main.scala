package raytrace4s

import scala.App
import primitives._
import tools._
import shapes._

object Main extends App {
  val width = 1000
  val height = 500

  val subsamples = 2
  val maxsteps = 10

  val fast = new Config(100, 50, 5, 10)
  val fastbig = new Config(1000, 500, 5, 10)
  val nice = new Config(1000, 500, 10, 20)
  val print = new Config(1000, 500, 20, 50)

  val currentConfig = fastbig

  val shapes = List(new Sphere(new Vector3d(0, 0, -1), 0.5, new Material(new Color(1, 0, 0), 0.25, 0.5, 0, 0, 0, 0)),
    new Sphere(new Vector3d(0.35, 0, -.75), 0.35, new Material(new Color(0.1, 0.1, 0.1), 0.25, 0.5, 0, 0, 0, 0)),
    new Sphere(new Vector3d(1.1, 0.3, -.75), 0.15, new Material(new Color(0.99, 0.99, 0.99), 0.25, 0.5, 0, 0, 1, 1.5)),
    new Sphere(new Vector3d(1.1, 0.0, -.75), -0.15, new Material(new Color(0.99, 0.99, 0.99), 0.25, 0.5, 0, 0, 1, 1.5)),
    new Sphere(new Vector3d(-0.35, 0.35, -1.05), 0.35, new Material(new Color(0, 1, 0), 0.25, 0.5, 0, 0, 0, 0)),
    new Sphere(new Vector3d(-1.5, -0.25, -1.05), 0.15, new Material(new Color(.9, .9, .9), 0.25, 0.5, .5, .25, 0, 0)),
    new Sphere(new Vector3d(-1.5, 0.05, -1.05), 0.15, new Material(new Color(.9, .9, .9), 0.25, 0.5, 1, .25, 0, 0)),
    new Sphere(new Vector3d(-1.5, 0.35, -1.05), 0.15, new Material(new Color(.9, .9, .9), 0.25, 0.5, .5, 0, 0, 0)),
    new Sphere(new Vector3d(-1.5, 0.65, -1.05), 0.15, new Material(new Color(.9, .9, .9), 0.25, 0.5, 1, 0, 0, 0)),
    new Sphere(new Vector3d(0, -10000.5, -1), 10000, new Material(new Color(1, 1, 0), 0.25, 0.5, 0, 0, 0, 0)))

  new ImageWriter(currentConfig, new WorldRenderer(new World(shapes))).write("test")
}
