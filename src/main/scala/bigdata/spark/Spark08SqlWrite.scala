package bigdata.spark

import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

object Spark08SqlWrite extends App with Base {

  //on hdfs
  val movieFileName = "/user/bdsf2001/leila/movie"
  val ratingFileName = "/user/bdsf2001/leila/rating"

  //0.create spark session
  val spark = SparkSession.builder().appName("Spark SQL Practices").master("local[*]").getOrCreate()

  //Assume u have 2 dataframe (most probably read from a source) and u'd like to do sthg
  val movieDf: DataFrame = spark.read
    .option("header","true")
    .option("inferschema","true")
    .csv(movieFileName)

  val ratingDf: DataFrame= spark.read
    .option("header","true")
    .option("inferschema","true")
    .csv(ratingFileName)

  //create a tempview
  movieDf.createOrReplaceTempView("movie")
  ratingDf.createOrReplaceTempView("rating")

  //Join Dataframe
  //sql query
  val enrichedRating1 = spark.sql(
    """
      |SELECT movie.title, avg(rating.stars) AS rating
      |FROM movie JOIN rating ON movie.mID = rating.mID
      |GROUP BY movie.title
      |""".stripMargin
  )
  //1.write on HDFS
  //by adding the mode we can change or overwrite or append etc to the existing file no matter its type
  enrichedRating1.write.mode(SaveMode.Overwrite).json("/user/bdsf2001/leila/enriched_rating")

  spark.stop()

}
