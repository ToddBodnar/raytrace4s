package raytrace4s.textures
import raytrace4s.primitives.{Material, Vector3d, Color}
import raytrace4s.tools.{Randomizer}

class PerlinTexture (t1: Texture, t2: Texture, scale: Double, location: Vector3d, rotation: Map[String, Double]) extends Texture(location, rotation){
  def colorAtInternal(point: Vector3d): Color = {
    //todo: make this function modifiable through args
    val res = Math.max(0, Math.min(1, 0.5 * (1 + Math.sin(scale*point.z + 10 * turbulance(point)))))
      
    t1.colorAt(point).merge(t2.colorAt(point), res)
  }
  
  def turbulance(point: Vector3d): Double = {
    def turbulance(point: Vector3d, acc: Double, itr: Integer, weight: Double): Double = {
      if(itr == 0){
        Math.abs(acc + weight * perlin(point))
      }else{
        turbulance(point * 2, acc + weight * perlin(point), itr-1, weight/2) 
      }
    }
    turbulance(point, 0, 15, 1.0)
  }
  
  def perlin(point: Vector3d): Double = {
    val zero = new Vector3d(0,0,0)
    val xs = Array(new Vector3d(1,0,0), zero)
    val ys = Array(new Vector3d(0,1,0), zero)
    val zs = Array(new Vector3d(0,0,1), zero)
    
    val baseCorner = point.apply(Math.floor)
    val distanceFromBaseCorner = (point - baseCorner)// /scale / 100
    //hermite cubic interpolation
    val distanceFromBaseCornerSmoothed = distanceFromBaseCorner * distanceFromBaseCorner * (distanceFromBaseCorner * -2 + 3)
    
    //println(distanceFromBaseCorner)
    var amt = 0.0
    var amtv = zero
    var amts = 0.0
    //println("-------")
    for (x <- xs){
      for(y <- ys){
        for(z <- zs){
          val offset = x + y + z
          val atCorner = Randomizer.randDouble(
            (baseCorner + offset)
            .apply(Math.floor)
            .reduce((a,b) => (a + .5) * (b + .5))
            .toLong)
            
           val atCornerV = Randomizer.randUnit(
            (baseCorner + offset)
            .apply(Math.floor)
            .reduce((a,b) => (a + .5) * (b + .5))
            .toLong)
            
          //println(atCornerV.dot(distanceFromBaseCorner - offset))
            
          amt += ((offset) * distanceFromBaseCornerSmoothed + ((offset * -1 + 1) * (distanceFromBaseCornerSmoothed * -1 + 1))).reduce(_*_)*(atCornerV.dot(distanceFromBaseCorner - offset))
        //println((atCorner.dot(distanceFromBaseCorner - offset)))
          //println(distanceFromBaseCorner+";"+offset)
        }
      }
    }
    //println(amt)
    amt
  }
}