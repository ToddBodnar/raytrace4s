package raytrace4s.primitives

class Color(red: Double, blue: Double, green: Double) {
  def this(vec: Vector3d) = this(vec.x, vec.y, vec.z)
  def this(pixel: Integer) = this(((pixel & 0x00ff0000) >> 16) / 255.0, ((pixel & 0x0000ff00) >> 8) / 255.0, (pixel & 0x000000ff) / 255.0)
  def merge(other: => Color, porportion: Double): Color = {
    if (porportion >= 1) {
      this
    } else if (porportion <= 0) {
      other
    } else {
      new Color(vector * (porportion) + other.vector * (1 - porportion))
    }
  }
  def normalize = new Color(math.min(1, red), math.min(1, blue), math.min(1, green))
  def vector = new Vector3d(red, blue, green)
  def hex: Int = (math.round(red * 255) * 256 * 256 + math.round(blue * 255) * 256 + math.round(green * 255)).toInt
}