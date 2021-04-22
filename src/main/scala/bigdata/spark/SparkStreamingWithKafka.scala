package bigdata.spark

import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord}
import org.apache.kafka.common.serialization.StringDeserializer

object SparkStreamingWithKafka extends App with Base {

  val conf = new SparkConf().setMaster("local[*]").setAppName("Spark streaming with Kafka")
  // 1.b A Spark context
  val sc = new SparkContext(conf)
  // 1.c A Spark streaming context
  val ssc = new StreamingContext(sc, Seconds(10))

  // 2. Create DStream
  // 2.a Create Kafka consuming configuration
  val kafkaConfig = Map[String, String] (
    ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> "localhost:9092",
    ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG -> classOf[StringDeserializer].getName,
    ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG -> classOf[StringDeserializer].getName,
    ConsumerConfig.GROUP_ID_CONFIG -> "test",
    ConsumerConfig.AUTO_OFFSET_RESET_CONFIG -> "earliest",
    ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG -> "false"
  )

}
