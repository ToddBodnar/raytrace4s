package raytrace4s.primitives

class BigVector3d(val x: BigDecimal, val y: BigDecimal, val z: BigDecimal) {
    def +(other: BigVector3d): BigVector3d = new BigVector3d(x + other.x, y + other.y, z + other.z)
    def /(other: Double): BigVector3d = new BigVector3d(x / other, y / other, z / other)
    
    def littleVector(): Vector3d = new Vector3d(x.doubleValue(),y.doubleValue(),z.doubleValue())
}