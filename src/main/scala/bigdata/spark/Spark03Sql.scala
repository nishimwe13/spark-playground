package bigdata.spark

import bigdata.spark.model.Movie
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types._
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

object Spark03Sql extends App with Base {

  //0.spark config
  // This is me typing
  val spark = SparkSession.builder().appName("Spark SQL Practices").master("local[*]").getOrCreate()

  //1.spark context
  val sc = spark.sparkContext
  val rawMovie: RDD[String] = sc.parallelize(
    List(
      "101,Gone with the wind,1939,Victor Fleming",
      "102,Star Wars,1977,George Lucas"
    )
  )

  //2.how to create DF from RDD using inference
  //2.a create RDD of "case class" (e.g. RDD[Movie])
  //2.b use ".toDF" from Spark session implicits

  val movieRdd: RDD[Movie] = rawMovie.map(Movie(_))

  import spark.implicits._

  val movieDf: DataFrame = movieRdd.toDF

  //THIS FOR DEBUG
  movieDf.schema
  movieDf.show(20) //shows 20 records and if file is big it will b truncated
  movieDf.show(20, truncate = false) //no truncate

  //3.how to create DF from RDD using programming interface
  //3.a convert it to RDD[Row]
  //3.b create a schema
  //3.c apply schema on the RDD

  val movieRows: RDD[Row] = rawMovie
    .map(_.split(",", -1))
    .map(fields => Row(fields(0).toInt, fields(1), fields(2).toInt, fields(3)))

  val schema = StructType(
    List(
      StructField("mId", IntegerType, nullable = false),
      StructField("title", StringType, nullable = false),
      StructField("year", IntegerType, nullable = false),
      StructField("mId", StringType)

    )
  )
  val movieDf2: DataFrame = spark.createDataFrame(movieRows,schema)

  //JUST FOR DEBUGGING
  movieDf2.printSchema()
  movieDf2.show()

  //this will stop spark session and context as well
  spark.stop()
}
