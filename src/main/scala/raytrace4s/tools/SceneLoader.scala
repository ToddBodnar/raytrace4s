package raytrace4s.tools

import scala.util.parsing.json._

import raytrace4s.shapes._
import raytrace4s.textures._
import raytrace4s.primitives._

object SceneLoader {
  def load(json: String): (Camera, World) = {
    JSON.parseFull(json) match {
      case Some(map: Map[String, Any]) => load(map)
      case other => load(Map.empty[String, Object])
    }
  }
  
  def load(json: Map[String, Any]): (Camera, World) = {
    val camera = new Camera(json(JsonFields.CAMERA_OBJECT).asInstanceOf[Map[String,Any]])
    
    val shapes = json(JsonFields.OBJECT_LIST).asInstanceOf[List[Map[String,Any]]].map(loadObject(_))
      
    val sky = loadSky(json(JsonFields.SKY_OBJECT).asInstanceOf[Map[String,Any]])
    
    return (camera, new World(sky, shapes))
  }
  
  def loadObject(json: Map[String, Any]): Shape = {
    val objTyp = json(JsonFields.OBJECT_TYPE)
    objTyp match {
      case JsonFields.SPHERE => new Sphere(json)
    }
  }
  
  def loadSky(json: Map[String, Any]): SkyMaterial = {
    json(JsonFields.SKY_OBJECT_TYPE) match {
      case JsonFields.NORMAL_SKY => new SkyMaterial()
      case JsonFields.DARK_SKY => new DarkSkyMaterial()
      case JsonFields.TEXTURE_SKY => new TextureSkyMaterial(json)
    }
  }
}