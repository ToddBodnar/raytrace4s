package raytrace4s.primitives

class Vector3d(val x: Double, val y: Double, val z: Double) {
  def +(other: Vector3d): Vector3d = new Vector3d(x + other.x, y + other.y, z + other.z)
  def +(other: Double): Vector3d = new Vector3d(x + other, y + other, z + other)
  def -(other: Vector3d): Vector3d = this + (other * -1)
  def *(other: Vector3d): Vector3d = new Vector3d(x * other.x, y * other.y, z * other.z)
  def *(other: Double): Vector3d = new Vector3d(x * other, y * other, z * other)
  def /(other: Vector3d): Vector3d = new Vector3d(x / other.x, y / other.y, z / other.z)
  def /(other: Double): Vector3d = this * (1 / other)

  def sqrt(): Vector3d = new Vector3d(math.sqrt(x), math.sqrt(y), math.sqrt(z))

  def len: Double = math.sqrt(squared_len)
  def squared_len: Double = this dot this
  def unit: Vector3d = this / len

  def dot(other: Vector3d): Double = x * other.x + y * other.y + z * other.z
  def cross(other: Vector3d): Vector3d = new Vector3d(y * other.z - z * other.y, -(x * other.z - z * other.x), x * other.y - y * other.x)
  
  def apply(f: Double => Double): Vector3d = new Vector3d(f(x), f(y), f(z))
  def reduce(f: (Double, Double) => Double): Double = f(f(x,y),z)
  
  override def toString(): String = "["+x+","+y+","+z+"]"
}