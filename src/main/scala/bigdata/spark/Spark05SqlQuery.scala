package bigdata.spark

import org.apache.spark.sql.{DataFrame, SparkSession}

object Spark05SqlQuery extends App with Base {

  //0.create spark session
  val spark = SparkSession.builder().appName("Spark SQL Practices").master("local[*]").getOrCreate()

  //Assume u have a dataframe (most probably read from a source) and u'd like to do sthg
  val movieDf: DataFrame = spark.read
    .option("header","true")
    .option("inferschema","true")
    .csv("/user/bdsf2001/leila/movie")

  //1. create a global view
  movieDf.createOrReplaceTempView("movie")

  //2. run ur sql query
  val movieAfter2000 = spark.sql(
    """
      |SELECT * FROM MOVIE
      |WHERE YEAR > 2000
      |""".stripMargin
  )
  movieAfter2000.printSchema()
  movieAfter2000.show()

  spark.stop()
}
