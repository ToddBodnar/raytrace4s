package raytrace4s.textures
import raytrace4s.primitives.{Vector3d, Color}

class ColorTexture(color: Color) extends Texture {
  def colorAt(v: Vector3d) = color
}