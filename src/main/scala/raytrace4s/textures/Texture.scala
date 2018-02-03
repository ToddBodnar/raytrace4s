package raytrace4s.textures
import raytrace4s.primitives.{Vector3d, Color}

abstract class Texture(location: Vector3d, rotation: Map[String, Double]) {
  def colorAt(point: Vector3d): Color = {
    colorAtInternal((point - location).rotate(rotation))
  }
  def colorAtInternal(point: Vector3d): Color
}