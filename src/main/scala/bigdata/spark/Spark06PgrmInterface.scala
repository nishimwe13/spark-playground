package bigdata.spark

import org.apache.spark.sql.functions.col
import org.apache.spark.sql.{DataFrame, SparkSession}

object Spark06PgrmInterface extends App with Base {

  //0.create spark session
  val spark = SparkSession.builder().appName("Spark SQL Practices").master("local[*]").getOrCreate()

  //Assume u have a dataframe (most probably read from a source) and u'd like to do sthg
  val movieDf: DataFrame = spark.read
    .option("header","true")
    .option("inferschema","true")
    .csv("/user/bdsf2001/leila/movie")

  //simulate the sql query
  import spark.implicits._
  val movieAfter2000 = movieDf.select(col("mId"),$"director").where(col("year") > 2000)

  movieAfter2000.printSchema()
  movieAfter2000.show()
  spark.stop()
}
