package raytrace4s.primitives

class Color(red: Double, blue: Double, green: Double){
  def hex:Int = (math.round(red*255)*256*256+math.round(blue*255)*256+math.round(green*255)).toInt
}