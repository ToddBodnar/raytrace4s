package raytrace4s.tools

class Config(val width: Int, val height: Int, val sqrtSubSamples: Int, val maxBounces: Int) {
  override def toString() : String = width+"x"+height+" image with "+sqrtSubSamples+" sub samples and "+maxBounces+" bounces"
}