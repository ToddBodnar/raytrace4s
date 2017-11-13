package raytrace4s.shapes

trait Loadable {
  def load(map: Map[String, Any]): Some[Any]
}