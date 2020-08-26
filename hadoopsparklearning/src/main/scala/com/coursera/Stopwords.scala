package com.coursera

import org.apache.spark.{SparkConf, SparkContext}

object Stopwords {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setAppName("MyApp").setMaster("local[2]"))
    val data = sc.parallelize(List(")I am doing good~", "Do+not \"worry\" about it", "You are good", "It is _______gonna be good"))
    val stopRDD = sc.parallelize(List("you", "Am")).map(w => w.toLowerCase)
      val stop = stopRDD.collect()
    val stopb= sc.broadcast(stop)
    val words=data.flatMap(l=>l.split(" ")).map(w=>w.toLowerCase)
    val filterwords= words.filter(w => !stopb.value.contains(w))
    println("stop words:")
    filterwords.collect().foreach(s => println(s"value is:: $s"))
    println("file words:")

    words.subtract(stopRDD).collect().foreach(s => println(s"subtract value is:: $s"))
    words.collect().foreach(println)
  }
}
