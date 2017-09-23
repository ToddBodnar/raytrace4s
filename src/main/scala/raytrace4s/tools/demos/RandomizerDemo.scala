package raytrace4s.tools.demos
import raytrace4s.tools.Randomizer

object RandomizerDemo extends App {
  for (i <- 0 to 100){
    println(i + "," + Randomizer.randomize(i) + "," + Randomizer.randomize(i)%100 + "," + Randomizer.randDouble(i))
  }
  
  
  
  var c_0 = 0
  var c_1 = 0
  var c_2 = 0
  
  println("Testing spread")
  for (i <- 0 to 10000){
    val v = Randomizer.randRange(i, 3)
    if(v == 0){
      c_0+=1
    }else if (v == 1){
      c_1+=1
    }else if (v == 2){
      c_2+=1
    }else{
      println(i + " mapped to " + v)
    }
  }
  println(c_0)
  println(c_1)
  println(c_2)
  
  
}