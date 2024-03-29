package raytrace4s.tools

object JsonFields {
  val OBJECT_CENTER_VECTOR = "center"
  val OBJECT_SCALE = "scale"
  val OBJECT_MATERIAL = "material"
  val OBJECT_ROTATION = "rotation"
  
  val TRIANGLE_V1 = "v1"
  val TRIANGLE_V2 = "v2"
  val TRIANGLE_V3 = "v3"
  
  val ROTATION_YAW = "yaw"
  val ROTATION_PITCH = "pitch"
  val ROTATION_ROLL = "roll"
  
  val CAMERA_VERTICAL_FOV = "fov"
  val CAMERA_ASPECT_RATIO = "aspect"
  val CAMERA_ORIGIN_VECTOR = "origin"
  val CAMERA_UP_VECTOR = "up"
  val CAMERA_TARGET_VECTOR = "target"
  val CAMERA_APERATURE = "aperature"
  val CAMERA_FOCUS_DISTANCE = "focus_distance"
  
  val CAMERA_OBJECT = "camera"
  val OBJECT_LIST = "objects"
  
  val OBJECT_TYPE = "type"
  val OBJECT_SUB_TYPE = "subType"
  
  val SHAPE_SPHERE = "sphere"
  val SHAPE_TRIANGLE = "triangle"
  val SHAPE_PLATONIC = "platonic"
  val SHAPE_IMPORTED = "imported"
  val SKY_BOX = "sky_box"
  
  val MATERIAL_TYPE = "type"
  
  val MATERIAL_BASIC_COLOR = "colorMaterial"
  val MATERIAL_BASIC_TEXTURE = "textureMaterial"
  val MATERIAL_LIGHT = "lightMaterial"
  val MATERIAL_CUSTOM = "customMaterial"
  val MATERIAL_DIFFUSE = "diffuseAmt"
  val MATERIAL_LIGHT_DAMPENING = "lightDampening"
  val MATERIAL_REFLECTION_AMOUNT = "reflectionAmount"
  val MATERIAL_REFLECTION_FUZZINESS = "reflectionFuzziness"
  val MATERIAL_TRANSPARENCY = "transparency"
  val MATERIAL_REFRACTION_INDEX = "refractionIndex"
  
  val TEXTURE_FIELD = "texture"
  
  val SKY_OBJECT = "sky"
  val SKY_OBJECT_TYPE = "type"
  val NORMAL_SKY = "blue"
  val DARK_SKY = "dark"
  val TEXTURE_SKY = "textureSky"
  
}