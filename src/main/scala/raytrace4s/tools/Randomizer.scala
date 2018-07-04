package raytrace4s.tools

import raytrace4s.primitives.Vector3d

object Randomizer {
  val seed = 0xb5ad4eceda1ce2a9L;
  /**
   * Modified Middle Square Weyl PRNG
   */
  def randomize(i: Long): Long = {
    def randomize(x: Long, w: Long, iteration: Int): Long = {
      if(iteration <= 0){
        x
      }else{
        val t = (x*x + w + seed)
        randomize((t>>32)|(t<<32), w+seed, iteration - 1)
      }
    }
    randomize(0,i,3)
  }
 
  
  def randRange(seed: Long, max: Long): Long = {
    Math.abs(randomize(seed)) % max
  }
  
  def randDouble(seed: Long): Double = {
    randRange(seed, 10000) / 10000.0
  }
  
  def randDouble(seed: Double): Double = {
    randDouble((seed * 1000).toLong)
  }
  
  def randUnit(seed: Long): Vector3d = {
    new Vector3d(randDouble(seed + 1)*2-1, randDouble(seed * 2)*2-1, randDouble(seed * 3)*2-1).unit
  }
}