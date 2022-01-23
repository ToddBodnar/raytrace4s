package raytrace4s.tools

import scala.util.parsing.json._
import java.nio.ByteBuffer
import java.nio.file.{Files, Paths}

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
      case JsonFields.SHAPE_SPHERE => new Sphere(json)
      case JsonFields.SHAPE_TRIANGLE => new Triangle(json)
      case JsonFields.SHAPE_PLATONIC => PlatonicSolid.fromJson(json)
      case JsonFields.SHAPE_IMPORTED => importShapeFile(json)
    }
  }
  
  def importShapeFile(json: Map[String, Any]): Shape = {
    json(JsonFields.OBJECT_SUB_TYPE) match {
      case "stl_bin" => loadStlBin(json)
    }
  }
  
  def loadStlBin(json: Map[String, Any]): Shape = {
    // see https://stackoverflow.com/questions/7598135/how-to-read-a-file-as-a-byte-array-in-scala for discussion on loading options
    val byteArray = Files.readAllBytes(Paths.get(json("fileName").toString))
    
    val center = new Vector3d(json(JsonFields.OBJECT_CENTER_VECTOR).asInstanceOf[Map[String, Any]])
    
    val rotation = json(JsonFields.OBJECT_ROTATION).asInstanceOf[Map[String, Double]]
    
    val material = MaterialFactory.load(json(JsonFields.OBJECT_MATERIAL).asInstanceOf[Map[String, Any]])
    
    val polygons = byteArray
        .drop(84) // first 80 are boilerplate/filler for bin stl files
        .grouped(50) // each of the trianges is defined by 50 bytes of data
        .map(triangleBytes => {
          val vertexes = triangleBytes
                  // vertexes are 3 4-byte floats
                  .grouped(12)
                  // first one is a normal, should be ignored
                  .drop(1)
                  // ignore the "attribute field", just take the 3 vertexes
                  .take(3)
                  .map(vertexBin => {
                      def grabFloat(idx: Int): Float = ByteBuffer.wrap(vertexBin.drop(4 * idx).take(4).reverse).getFloat()
                      
                      new Vector3d(grabFloat(0), grabFloat(2), grabFloat(1))
                  }).toList
          
          if (vertexes.length == 3) Some(new Triangle(vertexes, material, center, rotation)) else None
        }).flatten.toList
        
      val res = PolygonCollectionBulder.build(center, rotation, polygons)
      
      println("Loaded "+polygons.length+" polygons with bbox of "+res.bbox)
      
      res
  }
  
  def loadSky(json: Map[String, Any]): SkyMaterial = {
    json(JsonFields.SKY_OBJECT_TYPE) match {
      case JsonFields.NORMAL_SKY => new SkyMaterial()
      case JsonFields.DARK_SKY => new DarkSkyMaterial()
      case JsonFields.TEXTURE_SKY => new TextureSkyMaterial(json)
    }
  }
}