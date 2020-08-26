package com.coursera

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.storage.StorageLevel

import scala.collection.mutable

object SampleClass {

  def main(args: Array[String]): Unit = {
    val conf=new SparkConf().setAppName("MyApp").setMaster("local[2]")
    val sc = new SparkContext(conf)

    val rdd = sc.parallelize(List(
        "this is doing good but is expensive" ,
        "this will be good",
        "this needs to be solved"))
    val k = rdd.flatMap(line => parse1(line))
    println("Slide words:")
    //k.collect().foreach(println)
    k.map(w=>{
      (w._1.split("_")(0),1)
    }).reduceByKey((x,y)=>x+y).collect().foreach(println)
    val opRDD = rdd.flatMap(line => parse(line))
      .reduceByKey((a,b) => a+b).persist()

      opRDD.take(10)
      .foreach(
      op =>
    print(s"printing word: '${op._1}' count: '${op._2}' ")
    )

    //opRDD.map(words => words._2).sum()

     print(s"total count: ${opRDD.map(words=>words._2)
      .sum()}")
    //val wiki=sc.textFile("file:///home/milap/IdeaProjects/hadoopsparklearning/src/main/resources/stopwords.txt")
    //print(s"output is: ${wiki.map(s => s.str.take(5)}")
  }

  private def parse(line : String): Map[String, Int] = line.split(" ")
    .groupBy(words => words)
    .map(groupedWords => (groupedWords._1,groupedWords._2.length))
  private def parse1(line : String): Array[(String, Int)] = {
    val words=line.split(" ")
      words.sliding(2).map(w=> (w.mkString("_"),1)).toArray
  }


}
