package raytrace4s.textures
import raytrace4s.primitives.{Material, Vector3d, Color}
import raytrace4s.tools.{Randomizer}

class PerlinTexture (t1: Texture, t2: Texture, scale: Double) extends Texture{
  def colorAt(point: Vector3d): Color = {
    val p = perlin(point)
    if(p < -10)
      new Color(0,1,0)
    else
      t1.colorAt(point).merge(t2.colorAt(point), p)
    /*if(perlin(point) > .5)
      t1.colorAt(point)
    else
      t2.colorAt(point)*/
  }
  
  def perlin(point: Vector3d): Double = {
    val zero = new Vector3d(0,0,0)
    val xs = Array(new Vector3d(1,0,0), zero)
    val ys = Array(new Vector3d(0,1,0), zero)
    val zs = Array(new Vector3d(0,0,1), zero)
    
    val distanceFromBaseCorner = ((point * scale * 100).apply(Math.floor) - (point * scale * 100)).apply(Math.abs)// /scale / 100
    //println(distanceFromBaseCorner)
    var amt = 0.0
    var amtv = zero
    var amts = 0.0
    for (x <- xs){
      for(y <- ys){
        for(z <- zs){
          val offset = x + y + z
          val atCorner = Randomizer.randDouble(Math.floor(
            ((point) * 100 * scale  + offset)
            .apply(Math.floor)
            .reduce((a,b) => {
              //println(a+","+b)
              //println(              a.toInt ^ b.toInt)
              (a+.5) * (b+.5)
            })).toLong)
            
            //.reduce(_.toInt * _.toInt)))
            //println(((offset) * distanceFromBaseCorner + (offset + 1) * (distanceFromBaseCorner + 1)).reduce(_*_))
          amt += ((offset) * distanceFromBaseCorner + ((offset * -1 + 1) * (distanceFromBaseCorner * -1 + 1))).reduce(_*_)*atCorner
          //println(offset * distanceFromBaseCorner)
          //println(((offset) * distanceFromBaseCorner + ((offset * -1 + 1) * (distanceFromBaseCorner * -1 + 1))).reduce(_*_))
          //return atCorner
          amtv+=(((offset) * distanceFromBaseCorner + ((offset * -1 + 1) * (distanceFromBaseCorner * -1 + 1))))
          amts+=(((offset) * distanceFromBaseCorner + ((offset * -1 + 1) * (distanceFromBaseCorner * -1 + 1)))).reduce(_*_)
        }
      }
    }
    //println(amtv)
    //println(amt)
    //println(amts)
    amt
  }
}