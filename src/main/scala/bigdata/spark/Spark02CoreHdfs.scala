package bigdata.spark

import bigdata.spark.model.{Movie, Rating}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark02CoreHdfs extends App with Base {

  //0.spark config
  val spark = new SparkConf().setAppName("Spark Core Practices").setMaster("local[*]")

  //1.spark context
  val sc = new SparkContext(spark)
  val movies = sc.textFile("/user/bdsf2001/leila/movie")
  val movieRdd:RDD[Movie] = movies
    .filter(!_.contains("mID")) //filter the header.. there is no other way in spark core, please don't try weird things
    .map(Movie(_))

  /*this just for debugging .. must be removed from the project */
  movieRdd
    .take(10) //action to return 10 items ..we know (our movie file has less than 10 lines)
    .foreach(println)

  val ratings = sc.textFile("/user/bdsf2001/leila/rating")

  val ratingRdd:RDD[Rating] = ratings
    .filter(!_.contains("mID"))
    .map(Rating(_))

  /*prepare RDD for join ***/
  val a:RDD[(Int,Movie)]= movieRdd.keyBy(_.mId)
  val b: RDD[(Int,Rating)] = ratingRdd.keyBy(_.mId)

  /*JOIN ***/
  val enrichedRating = a.join(b).map{
    case(_,(movie,rating)) => Rating.toCsv(rating,movie)
  }
  enrichedRating.take(30).foreach(println)
  enrichedRating.saveAsTextFile("/user/bdsf2001/leila/enriched_rating/")

  sc.stop()
}
