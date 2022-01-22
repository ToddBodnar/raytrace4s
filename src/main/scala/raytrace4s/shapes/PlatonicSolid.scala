package raytrace4s.shapes
import raytrace4s.primitives.{ MaterialFactory, Material, Ray, Vector3d }
import raytrace4s.tools.JsonFields

object PlatonicSolid {
  def fromJson(json: Map[String, Any]) = {
      build(new Vector3d(json(JsonFields.OBJECT_CENTER_VECTOR).asInstanceOf[Map[String, Any]]), 
            json(JsonFields.OBJECT_ROTATION).asInstanceOf[Map[String, Double]],
            json(JsonFields.OBJECT_SUB_TYPE).asInstanceOf[String],
            MaterialFactory.load(json(JsonFields.OBJECT_MATERIAL).asInstanceOf[Map[String, Any]])
      )
  }
  def build(center: Vector3d, rotation: Map[String, Double], shapeType: String, material: Material) = {
    
    def buildPoly(v1: Vector3d, v2: Vector3d, v3: Vector3d) = new Triangle(v1, v2, v3, material, center, rotation)
    
    shapeType match {
        case "pyramid" => {
        
          val v1 = new Vector3d(0, -1.0/3.0, Math.sqrt(8.0/9.0))
          val v2 = new Vector3d(Math.sqrt(2.0/3.0), -1.0/3.0, -Math.sqrt(2.0/9.0))
          val v3 = new Vector3d(-Math.sqrt(2.0/3.0), -1.0/3.0, -Math.sqrt(2.0/9.0))
          val v4 = new Vector3d(0, 1, 0)
      
          new PolygonCollection(center, rotation, 
              List(buildPoly(v1, v2, v3), 
                   buildPoly(v1, v2, v4), 
                   buildPoly(v2, v3, v4), 
                   buildPoly(v3, v1, v4)))
        
        }
        case "cube" => {
          def buildSquare(v1: Vector3d, v2: Vector3d, v3: Vector3d, v4: Vector3d) = 
              List(buildPoly(v1, v2, v4), buildPoly(v2, v3, v4))
              
          def p(x: Double, y: Double, z: Double) = new Vector3d(x,y,z)
          
          new PolygonCollection(center, rotation, 
          buildSquare(p(-1,-1,-1), p(-1,-1,1), p(1,-1,1), p(1,-1,-1)) :::
          buildSquare(p(-1,1,-1), p(-1,1,1), p(1,1,1), p(1,1,-1)) :::
          buildSquare(p(-1,-1,-1), p(-1,1,-1), p(-1, 1, 1), p(-1,-1,1)) ::: 
          buildSquare(p(1,-1,-1), p(1,1,-1), p(1, 1, 1), p(1,-1,1)) :::
          buildSquare(p(-1,-1,-1), p(1,-1,-1), p(1,1,-1), p(-1,1,-1)) ::: 
          buildSquare(p(-1,-1,1), p(1,-1,1), p(1,1,1), p(-1,1,1)) )
        }
        
        case "octahedron" => {
          val top = new Vector3d(0,1,0)
          val bottom = new Vector3d(0,-1,0)
          
          val ring1 = new Vector3d(0,0,-1)
          val ring3 = new Vector3d(0,0,1)
          val ring2 = new Vector3d(-1,0,0)
          val ring4 = new Vector3d(1,0,0)
          
          new PolygonCollection(center, rotation,
          List(buildPoly(ring1, ring2, top),
               buildPoly(ring2, ring3, top),
               buildPoly(ring3, ring4, top),
               buildPoly(ring4, ring1, top),
               buildPoly(ring1, ring2, bottom),
               buildPoly(ring2, ring3, bottom),
               buildPoly(ring3, ring4, bottom),
               buildPoly(ring4, ring1, bottom)))
        }
        
        case "dodecahedron" => {
          // mini helper fn, "build positive"
          def bp(theBool: Boolean) = if (theBool) 1 else -1
          
          // from https://en.wikipedia.org/wiki/Regular_dodecahedron
          val theta = (1 + Math.sqrt(5.0)) / 2.0
          
          def pointBuilder(ptType: Int, pos1: Boolean, pos2: Boolean, pos3: Boolean) = 
              ptType match {
                  case 1 => new Vector3d(bp(pos1), bp(pos2), bp(pos3)) / theta
                  case 2 => new Vector3d(0, bp(pos2), bp(pos3) / theta / theta)
                  case 3 => new Vector3d(bp(pos1) / theta / theta, 0, bp(pos3))
                  case _ => new Vector3d(bp(pos1), bp(pos2) / theta / theta, 0)
              }
              
          def buildPenta(v1: Vector3d, v2: Vector3d, v3: Vector3d, v4: Vector3d, v5: Vector3d) = 
              List(buildPoly(v1, v2, v3), buildPoly(v4,v3,v1), buildPoly(v1,v4,v5))
              
          new PolygonCollection(center, rotation, 
            // top two 
            buildPenta(pointBuilder(3, false, true, true), pointBuilder(3, true, true, true), pointBuilder(1, true, true, true), pointBuilder(2, true, true, true), pointBuilder(1, false, true, true)) :::
            buildPenta(pointBuilder(3, false, true, true), pointBuilder(3, true, true, true), pointBuilder(1, true, false, true), pointBuilder(2, true, false, true), pointBuilder(1, false, false, true)) :::
            
            // bottom two //todo: why are these slightly "wrong"?
            buildPenta(pointBuilder(3, false, true, false), pointBuilder(3, true, true, false), pointBuilder(1, true, true, false), pointBuilder(2, true, true, false), pointBuilder(1, false, true, false)) :::
            buildPenta(pointBuilder(3, false, true, false), pointBuilder(3, true, true, false), pointBuilder(1, true, false, false), pointBuilder(2, true, false, false), pointBuilder(1, false, false, false)) :::
            
            
            // near top two
            buildPenta(pointBuilder(3, true, false, true), pointBuilder(1, true, false, true), pointBuilder(4, true, false, true), pointBuilder(4, true, true, true), pointBuilder(1, true, true, true)) :::
            buildPenta(pointBuilder(3, false, false, true), pointBuilder(1, false, false, true), pointBuilder(4, false, false, true), pointBuilder(4, false, true, true), pointBuilder(1, false, true, true)) :::
            
            
            // near bottom two
            buildPenta(pointBuilder(3, true, false, false), pointBuilder(1, true, false, false), pointBuilder(4, true, false, false), pointBuilder(4, true, true, false), pointBuilder(1, true, true, false)) :::
            buildPenta(pointBuilder(3, false, false, false), pointBuilder(1, false, false, false), pointBuilder(4, false, false, false), pointBuilder(4, false, true, false), pointBuilder(1, false, true, false)) :::
            
            
            // center ring
            
            buildPenta(pointBuilder(1, true, false, true), pointBuilder(4, true, false, true), pointBuilder(1, true, false, false), pointBuilder(2, true, false, false), pointBuilder(2, true, false, true)) :::
            buildPenta(pointBuilder(1, false, false, true), pointBuilder(4, false, false, true), pointBuilder(1, false, false, false), pointBuilder(2, false, false, false), pointBuilder(2, false, false, true)) :::
            buildPenta(pointBuilder(1, true, true, true), pointBuilder(4, true, true, true), pointBuilder(1, true, true, false), pointBuilder(2, true, true, false), pointBuilder(2, true, true, true)) :::
            buildPenta(pointBuilder(1, false, true, true), pointBuilder(4, false, true, true), pointBuilder(1, false, true, false), pointBuilder(2, false, true, false), pointBuilder(2, false, true, true)) ::: 
            Nil
            )
        }
        
        case "icosahedron" => {
        
            val theta = (1 + Math.sqrt(5.0)) / 2.0
            
            val topPoint = new Vector3d(0, 0, 1);
            val bottomPoint = new Vector3d(0, 0, -1)
            
            // the middle two sets of points are rings at an offset of each other
            def pointIdxToVect(top: Boolean) (idx: Int) = {
                val inRadians = Math.toRadians(72*(idx + (if (top) 0 else .5)))
                new Vector3d(Math.sin(inRadians), Math.cos(inRadians), if (top) Math.atan(1/2.0) else Math.atan(-1/2.0))
            }
            
            val highMiddlePoints = (0 to 5).map(pointIdxToVect(true))
            val bottomMiddlePoints = (0 to 5).map(pointIdxToVect(false))
            
            new PolygonCollection(center, rotation, 
              (0 to 5).map( idx => buildPoly(topPoint, highMiddlePoints(idx), highMiddlePoints((idx + 1) % 5))).toList :::
              (0 to 5).map( idx => buildPoly(bottomPoint, bottomMiddlePoints(idx), bottomMiddlePoints((idx + 1) % 5))).toList :::
              
              (0 to 5).map( idx => buildPoly(bottomMiddlePoints(idx), highMiddlePoints(idx), highMiddlePoints((idx + 1) % 5))).toList :::
              (0 to 5).map( idx => buildPoly(highMiddlePoints((idx + 1 ) % 5), bottomMiddlePoints(idx), bottomMiddlePoints((idx + 1) % 5))).toList
                    
            )
        }
        
        case _ => new PolygonCollection(center, rotation, List.empty)
    }
  }
}