package raytrace4s.primitives

import raytrace4s.tools.JsonFields

class Vector3d(val x: Double, val y: Double, val z: Double) {
  def this (map: Map[String, Any]){
    this(map.get("x").get.toString().toDouble, map.get("y").get.toString().toDouble, map.get("z").get.toString().toDouble)
  }
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
  
  def bigVector(): BigVector3d = new BigVector3d(BigDecimal(x), BigDecimal(y), BigDecimal(z))
  
  def rotate(map: Map[String, Double]): Vector3d = rotate(map(JsonFields.ROTATION_YAW), map(JsonFields.ROTATION_PITCH), map(JsonFields.ROTATION_ROLL))
  
  def rotateReverse(map: Map[String, Double]): Vector3d = rotate(-map(JsonFields.ROTATION_YAW), -map(JsonFields.ROTATION_PITCH), -map(JsonFields.ROTATION_ROLL))
  
  def rotate(yaw: Double, pitch: Double, roll: Double): Vector3d = {
    new Vector3d(Math.cos(yaw) * Math.cos(pitch) * x +
        (Math.cos(yaw) * Math.sin(pitch) * Math.sin(roll) - Math.sin(yaw) * Math.cos(roll)) * y +
        (Math.cos(yaw) * Math.sin(pitch) * Math.cos(roll) + Math.sin(yaw) * Math.sin(roll)) * z,
        
        Math.sin(yaw) * Math.cos(pitch) * x + 
        (Math.sin(yaw) * Math.sin(pitch) * Math.sin(roll) + Math.cos(yaw) * Math.cos(roll)) * y +
        (Math.sin(yaw) * Math.sin(pitch) * Math.cos(roll) - Math.cos(yaw) * Math.sin(roll)) * z,
        
        - Math.sin(pitch) * x + 
        Math.cos(pitch) * Math.sin(roll) * y + 
        Math.cos(pitch) * Math.cos(roll) * z)
  }
  
  def min(other: Vector3d): Vector3d = new Vector3d(Math.min(x, other.x), Math.min(y, other.y), Math.min(z, other.z))
  def max(other: Vector3d): Vector3d = new Vector3d(Math.max(x, other.x), Math.max(y, other.y), Math.max(z, other.z))
  
  override def toString(): String = "["+x+","+y+","+z+"]"
}