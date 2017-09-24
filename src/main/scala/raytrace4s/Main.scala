package raytrace4s

import scala.App
import primitives._
import tools._
import shapes._
import textures._

import javax.imageio.ImageIO
import java.io.File

object Main extends App {
  val width = 1000
  val height = 500

  val subsamples = 2
  val maxsteps = 10

  val fast = new Config(100, 50, 5, 10)
  val fastbig = new Config(1000, 500, 5, 10)
  val nice = new Config(1000, 500, 10, 20)
  val print = new Config(1000, 500, 20, 50)

  val currentConfig = print
  
  
  def endBookOne(): WorldRenderer = {
    val world = new Sphere(new Vector3d(0, -10000.5, -1), 10000, new Material(new Color(.8, .8, .8), 0.25, 0.5, 0, 0, 0, 0))

    def genSpheres(ct: Int): List[Sphere] = {
      if(ct <= 0){
        return List()
      }else{
        val rnd = math.random
        val material = if(rnd < .8){
          new Material(new Color(math.random/2+.5, math.random/2+.5, math.random/2+.5), 0.25, 0.25,0,0,0,0)
        } else if (rnd < 0.95){
          new Material(new Color(1,1,1), .25, .5, 1, 0,0,0)
        } else{
          new Material(new Color(1,1,1), 0,0,0,0,1, 1.5)
        }
        List(new Sphere(new Vector3d(math.random * 40 - 10, -0.35, math.random * 40 - 10), 0.15, material)):::genSpheres(ct-1)
      }
    }
    
    def removeCollision(current:List[Sphere], next:Sphere) : List[Sphere] = {
      if(current.exists { ball => ((ball.center - next.center).len) < (ball.radius + next.radius) }){
        current
      }else{
        current :+ next
      }
    }
    
    val camera = new Camera(50, 2, new Vector3d(-2,1,-4), new Vector3d(0,1,0), new Vector3d(0,0.5, 0), .01, 3.8)
    
    //val camera = new Camera(90, 2, new Vector3d(0,1,0), new Vector3d(.25,.75,0), new Vector3d(-.25,0,-1))
    
    /**val shapes = List(new Sphere(new Vector3d(0, 0, -1), 0.5, new Material(new Color(1, 0, 0), 0.25, 0.5, 0, 0, 0, 0)),
      new Sphere(new Vector3d(0.35, 0, -.75), 0.35, new Material(new Color(0.1, 0.1, 0.1), 0.25, 0.5, 0, 0, 0, 0)),
      new Sphere(new Vector3d(1.1, 0.3, -.75), 0.15, new Material(new Color(0.99, 0.99, 0.99), 0.25, 0.5, 0, 0, 1, 1.5)),
      new Sphere(new Vector3d(1.1, 0.0, -.75), -0.15, new Material(new Color(0.99, 0.99, 0.99), 0.25, 0.5, 0, 0, 1, 1.5)),
      new Sphere(new Vector3d(-0.35, 0.35, -1.05), 0.35, new Material(new Color(0, 1, 0), 0.25, 0.5, 0, 0, 0, 0)),
      new Sphere(new Vector3d(-1.5, -0.25, -1.05), 0.15, new Material(new Color(.9, .9, .9), 0.25, 0.5, .5, .25, 0, 0)),
      new Sphere(new Vector3d(-1.5, 0.05, -1.05), 0.15, new Material(new Color(.9, .9, .9), 0.25, 0.5, 1, .25, 0, 0)),
      new Sphere(new Vector3d(-1.5, 0.35, -1.05), 0.15, new Material(new Color(.9, .9, .9), 0.25, 0.5, .5, 0, 0, 0)),
      new Sphere(new Vector3d(-1.5, 0.65, -1.05), 0.15, new Material(new Color(.9, .9, .9), 0.25, 0.5, 1, 0, 0, 0)),
      
      new Sphere(new Vector3d(-.6, -0.35, -.5), 0.15, new Material(new Color(.2, .2, .9), 0.25, 0.5, 0,0, 0, 0)),
      new Sphere(new Vector3d(-.9, -0.35, -1), 0.15, new Material(new Color(.2, .2, .9), 0.25, 0.5, 0,0, 0, 0)),
      new Sphere(new Vector3d(-1.2, -0.35, -1.5), 0.15, new Material(new Color(.2, .2, .9), 0.25, 0.5, 0,0, 0, 0)),
      new Sphere(new Vector3d(-1.5, -0.35, -2), 0.15, new Material(new Color(.2, .2, .9), 0.25, 0.5, 0,0, 0, 0)),
      new Sphere(new Vector3d(-1.8, -0.35, -2.5), 0.15, new Material(new Color(.2, .2, .9), 0.25, 0.5, 0,0, 0, 0)),
      new Sphere(new Vector3d(-2.1, -0.35, -3), 0.15, new Material(new Color(.2, .2, .9), 0.25, 0.5, 0,0, 0, 0)),
      
      
      new Sphere(new Vector3d(0, -10000.5, -1), 10000, new Material(new Color(1, 1, 0), 0.25, 0.5, 0, 0, 0, 0)))
    **/
    val preprogramedShapes = List(world,
        new Sphere(new Vector3d(2, 0.5, 0), 1, new Material(new Color(1, 0,0), 0.25, 0.5, 0, 0, 0, 0)),
      new Sphere(new Vector3d(0, 0.5, 0), 1, new Material(new Color(.9,.9,.9), 0.25, 0.5, 0, 0, 1, 1.5)),
      new Sphere(new Vector3d(-2, 0.5, 0), 1, new Material(new Color(.8,1,.8), 0.25, 0.5, .95, 0, 0,0)))
      
    val shapes = genSpheres(400).foldLeft(preprogramedShapes)(removeCollision)
    new WorldRenderer(camera,new World(shapes))
    //new ImageWriter(currentConfig, new TimeRenderer(new WorldRenderer(new World(shapes)), 100.0)).write("test")
  }
  
  def checkerBox(): WorldRenderer = {
    val camera = new Camera(50, 2, new Vector3d(-2,1,-4), new Vector3d(0,1,0), new Vector3d(0,0.5, 0), .01, 3.8)
    val world = new Sphere(new Vector3d(0, -10000.5, -1), 10000, MaterialFactory.basicMaterial(new CheckeredTexture(new ColorTexture(new Color(.005,.005,.005)), new ColorTexture(new Color(.995,.995,.995)), .1)))

    val shapes = List(world,
        new Sphere(new Vector3d(2, 0.5, 0), 1, MaterialFactory.basicMaterial(new Color(1, 0,0))),
      new Sphere(new Vector3d(0, 0.5, 0), 1, new Material(new Color(.9,.9,.9), 0.25, 0.5, 0, 0, 0, 0)),
      new Sphere(new Vector3d(-2, 0.5, 0), 1, new Material(new Color(.8,1,.8), 0.25, 0.5, .95, 0, 0,0)))
      
      
     new WorldRenderer(camera, new World(shapes))
  }
  
  def textureGenerators(): WorldRenderer = {
    val camera = new Camera(50, 2, new Vector3d(-2,1,-4), new Vector3d(0,1,0), new Vector3d(0,0.5, 0), .01, 3.8)
    val world = new Sphere(new Vector3d(0, -10000.5, -1), 10000, MaterialFactory.basicMaterial(new CheckeredTexture(new ColorTexture(new Color(.005,.005,.005)), new ColorTexture(new Color(.995,.995,.995)), .1)))

    val shapes = List(world,
        new Sphere(new Vector3d(2, 0.5, 0), 1, MaterialFactory.basicMaterial(new ColorTexture(new Color(1, 0,0)))),
      new Sphere(new Vector3d(0, 0.5, 0), 1, MaterialFactory.basicMaterial(new ImageSphereTexture(ImageIO.read(new File("earth.jpg")), new Vector3d(0, 0.5, 0)))),
      new Sphere(new Vector3d(-2, 0.5, 0), 1, MaterialFactory.basicMaterial(new PerlinTexture(new ColorTexture(new Color(.995,.995,.995)), new ColorTexture(new Color(.005,.005,.005)), 1))))
      
      
     new WorldRenderer(camera, new World(shapes))
  }
  
  new ImageWriter(currentConfig, textureGenerators()).write("test")
}
