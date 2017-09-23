package raytrace4s.textures
import raytrace4s.primitives.{Material, Vector3d, Color}
class CheckeredTexture(t1: Texture, t2: Texture, scale: Double) extends Texture{
  def colorAt(p: Vector3d): Color = {
    if(Math.sin(p.x/scale)*Math.sin(p.y/scale)*Math.sin(p.z/scale) > 0){
      t1.colorAt(p)
    }else {
      t2.colorAt(p)
    }
  }
}