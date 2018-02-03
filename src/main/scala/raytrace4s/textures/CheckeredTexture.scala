package raytrace4s.textures
import raytrace4s.primitives.{Material, Vector3d, Color}
class CheckeredTexture(t1: Texture, t2: Texture, scale: Double, location: Vector3d, rotation: Map[String, Double]) extends Texture(location, rotation){
  def colorAtInternal(p: Vector3d): Color = {
    if(Math.sin(p.x/scale)*Math.sin(p.y/scale)*Math.sin(p.z/scale) > 0){
      t1.colorAtInternal(p)
    }else {
      t2.colorAtInternal(p)
    }
  }
}