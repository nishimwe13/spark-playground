package bigdata.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.util.Random

object Spark01CoreRdd extends App with Base {

  //0.spark config
  val spark = new SparkConf().setAppName("Spark Core Practices").setMaster("local[*]")

  //1.spark context
  val sc = new SparkContext(spark)

  //2.business logic
  // 2.1 create rdd(1000 random numbers)

  val randomNumbers = (1 to 1000).map(_ => Random.nextInt(1000))
  val rdd0: RDD[Int]= sc.parallelize(randomNumbers)
  println(s"Number of partitions: ${rdd0.getNumPartitions}")
  rdd0.foreachPartition(partitionMembers => println(s"===> ${partitionMembers.toList.size}"))

  //2.2.change number of partitions
  //val rdd1 = rdd0.repartition(4)
  val rdd1 =  rdd0.coalesce(2) //this fx is used only to decrease number of partitions!
  rdd1.foreachPartition(partitionMembers => println(s"===> ${partitionMembers.toList.size}"))

  //2.3Transformation + action
  //lazy evaluation

  val sampleOfRandomNumbers = rdd1.map(_ * 2).take(10)
  sampleOfRandomNumbers.foreach(println)

  //2.4 filter
  println("~~~~~~~~2.4~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~`")
  val numbersLessThan100 = rdd0.filter(_ < 100).coalesce(2)
  numbersLessThan100.foreachPartition(partitionMembers => println(s"===> ${partitionMembers.toList.size}"))

  while(true) Thread.sleep(1000)

  //10.stop the context
  sc.stop()

}
