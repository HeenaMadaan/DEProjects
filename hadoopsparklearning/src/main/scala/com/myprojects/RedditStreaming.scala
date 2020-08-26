package com.myprojects

import org.apache.spark.sql.streaming.ProcessingTime
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._


// Read Reddit posts streaming

object RedditStreaming {
  def main(args: Array[String]): Unit = {
    // 1. create spark session -  we have to import the necessary classes and create a local SparkSession, the starting point of all functionalities related to Spark.
    val spark= SparkSession
      .builder
      .appName("RedditStream")
      .master("local")
      .getOrCreate()

    //2. build schema of input stream
    /*
          val sf = StructField("schema",StringType)
        val schema= StructType(List(sf))*/
    val payloadSchema =  new StructType()
      .add("score", IntegerType, false)
      .add("saved", BooleanType, false)
      .add("stickied",BooleanType, false)
      .add("author", StringType, false)
      .add("author_flair_text", StringType)
      .add("archived", BooleanType, false)
      .add("can_gild", BooleanType, false)
      .add("created_utc", LongType,false)
      .add("contest_mode", BooleanType, false)
      .add("domain", StringType,false)
      .add("name", StringType)
      .add("gilded", IntegerType, false)
      .add("hidden", BooleanType, false)
      .add("hide_score", BooleanType, false)
      .add("id",StringType, false)
      .add("is_self", BooleanType, false)
      .add("link_flair_text",StringType)
      .add("link_flair_css_class",StringType)
      .add("locked", BooleanType, false)
      .add("over_18", BooleanType, false)
      .add("permalink", StringType, false)
      .add("post_hint", StringType)
      .add("quarantine", BooleanType, false)
      .add("selftext",StringType, false)
      .add("spam", BooleanType, false)
      .add("spoiler",BooleanType, false)
      .add("subreddit", StringType, false)
      .add("subreddit_id", StringType, false)
      .add("thumbnail",StringType, false)
      .add("title",StringType, false)
      .add("url",StringType,false)
      .add("visited",BooleanType, false)
      .add("removed",BooleanType, false)
      .add("num_comments",IntegerType, false)

    val schema = new StructType()
      .add("schema",StringType,true)
      .add("payload",payloadSchema)


    //3. create a streaming DataFrame that represents data received from a Kafka server on reddit-posts topic
    val reddit_df = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "reddit-posts")
      .option("startingOffsets", "earliest")
      .load()

    reddit_df.printSchema()

      val reddit_df1=reddit_df   // extracting value as your payload and timestamp as processing time for partitioning in parquet
      .select(from_json(col("value").cast("String"), schema).alias("cval"),col("timestamp"))
      .select(col("cval.payload.*"),col("timestamp"))
        .withColumn("Date_part", date_format(col("timestamp"),"yyyyMMdd"))
        // adding column in the data frame with with_column
        .withColumn("Hour_part",hour(col("timestamp")))
        .withColumn("Minute_part",minute(col("timestamp")))


    // to save output in parquet file, set you partitioning criteria, and output HDFS path
    val query= reddit_df1
      .writeStream
      .outputMode("append")  // other options- update, complete
      .trigger(ProcessingTime("30 seconds"))
      .format("parquet")
      .partitionBy("Date_part","Hour_part","Minute_part")
      .option("path","/home/milap/IdeaProjects/hadoopsparklearning/src/main/resources")
      .option("checkpointLocation","/home/milap/IdeaProjects/hadoopsparklearning/src/main/resources/checkpoint")
      .start()  // to start the execution of the query
    query.awaitTermination()

    // to have output of streaming on console
    /*    val query= reddit_df1
          .writeStream
            .outputMode("append")  // other options- update, complete
    //        .format("console") // to get output on console, you can have file sink like parquet, in memory sink, kafka sink, for each sink,
            .trigger(ProcessingTime("30 seconds"))
            .start()  // to start the execution of the query
        query.awaitTermination() */ // it waits for external input to terminate like query.stop() from other process or stop execution button input
  }
}
