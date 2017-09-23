package raytrace4s.tools

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
    randomize(0,i,10)
  }
  
  def randRange(seed: Long, max: Long): Long = {
    Math.abs(randomize(seed)) % max
  }
  
  def randDouble(seed: Long): Double = {
    randRange(seed, 10000) / 10000.0
  }
}