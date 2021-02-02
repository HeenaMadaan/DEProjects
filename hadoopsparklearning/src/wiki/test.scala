package com.wiki

import org.apache.spark.sql.SparkSession

object test extends App {
  val spark = SparkSession.builder().config("spark.master","local[*]").appName("topcategories")
    .enableHiveSupport().getOrCreate()

//  spark.sql("DROP table topcategories")
  val tables= spark.sql("show tables")
  tables.take(10).foreach(println)
  spark.sql("select * from topcategories").take(10).foreach(println)
  spark.sql("describe topcategories").collect().foreach(println)
  val df=1
}
