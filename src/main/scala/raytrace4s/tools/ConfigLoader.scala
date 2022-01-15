package raytrace4s.tools

import scala.io.Source
import scala.math._
import scala.util.parsing.json._
import scala.util.Try
import java.text.ParseException

object ConfigLoader {

    def load(name: String): Try[Config] = configFromFile("render_settings/"+name+".json")
    
    def default = load("fastbig")

    def configFromFile(file: String): Try[Config] = {
      Try{
          JSON.parseFull(Source.fromFile(file).mkString) match {
          case Some(map: Map[String, Any]) => {
             
              val width = map("width") match {
                 case i: Double => i.toInt
                 case _ => throw new ParseException("Missing width", 0)
              }
              val height = map("height") match {
                 case i: Double => i.toInt
                 case _ => throw new ParseException("Missing height", 1)
              }
              val sqrtSubSamples = map("subSamples") match {
                 case i: Double => Math.floor(Math.sqrt(i)).toInt
                 case _ => throw new ParseException("Missing subSamples", 2)
              }
              val maxBounces = map("maxBounces") match {
                 case i: Double => i.toInt
                 case _ => throw new ParseException("Missing maxBounces", 3)
              }
             
              new Config(width, height, sqrtSubSamples, maxBounces)
          }
          case other => throw new ParseException("Not a json?", -1)
        }
      }
    }
}