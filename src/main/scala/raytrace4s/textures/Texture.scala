package raytrace4s.textures
import raytrace4s.primitives.{Vector3d, Color}

trait Texture {
  def colorAt(point: Vector3d): Color
}