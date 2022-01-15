scalaVersion := "2.11.0"

libraryDependencies ++= Seq(
  "me.tongfei" %% "progressbar" % "0.3.2",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.2"
)

Compile / mainClass := Some("raytrace4s.Main")