name := "spark-playground"

version := "0.1"

scalaVersion := "2.11.8"

val sparkVersion = "2.4.4"

libraryDependencies ++=Seq(

  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-streaming" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-streaming-kafka-0-10" % sparkVersion % "provided"

)