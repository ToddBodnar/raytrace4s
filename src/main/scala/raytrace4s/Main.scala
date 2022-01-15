package raytrace4s

import scala.App
import scala.io.Source
import scala.util.{Try, Success, Failure}
import primitives._
import tools._
import shapes._
import textures._

import javax.imageio.ImageIO
import java.io.File

object Main extends App {
  val defaultFile = "panorama_seesh_mahal"
  
  val currentConfigUnsafe = if (args.length < 2) ConfigLoader.default else ConfigLoader.load(args(1))
  
  val renderConfig = currentConfigUnsafe match {
      case Success(s) => s
      case Failure(f) => throw f
  }
  
  val jobName = if (args.length < 1) defaultFile else args(0)
  
  val (camera, scene) = SceneLoader.load(Source.fromFile("scenes/" + jobName + ".json").mkString)
  val render = new WorldRenderer(camera, scene)
  new ImageWriter(renderConfig, render).write("renders/" + jobName)
}
