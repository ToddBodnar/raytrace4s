package raytrace4s.textures
import raytrace4s.primitives.{Vector3d, Color}

class ColorTexture(color: Color) extends Texture(new Vector3d(0,0,0), Map[String, Double]()) {
  override def colorAt(v: Vector3d) = color
  def colorAtInternal(v: Vector3d) = color
}