package bigdata.spark

import org.apache.spark.sql.{DataFrame, SparkSession}

object Spark04SqlFromSource extends App with Base{

  /** on HDFS */
  val fileName = "/user/bdsf2001/leila/movie"

  val spark = SparkSession.builder().appName("Spark SQL practice").master("local[*]").getOrCreate()

  val movieDf: DataFrame = spark.read
    .option("header", "true")
    .option("inferschema", "true") // check th data infer its type and come back
    .csv(fileName)

  //val movieDf:DataFrame=spark.read.csv(fileName)//with header

  // THIS IS FOR DEBUG AND SHALL NOT BE IN ANY PRODUCTION CODE NOR IN THE SUBMISSION
  movieDf.printSchema()
  movieDf.show()

  spark.stop()

}
