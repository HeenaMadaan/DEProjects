package com.coursera

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.util.control.Breaks

object ShortestPath {
  def main(args: Array[String]): Unit = {
    val sc= new SparkContext(new SparkConf().setAppName("Shortest").setMaster("local[2]"))
    val edges = sc.textFile("file:///home/milap/IdeaProjects/hadoopsparklearning/src/main/resources/twitter_sample_small.txt",4)
   //val edges = sc.parallelize(List("2\t3","2\t4","3\t4","4\t5","4\t6","5\t6","5\t3"))
      .map(_.split("\t")).map(s=>(s(1),s(0)))
    var distances= sc.parallelize(List("12")).map(s=>(s,(s,0)))
    var newDistance : RDD[(String, (String,Int))] = null
    var checkDest : RDD[(String, (String, Int))] = null
    val loopB = new Breaks
    loopB.breakable{
     while (true){
        var neigh = distances.join(edges)
          .map{case (a,((b,c),d)) => (d,(b+","+d,c+1))}
            .reduceByKey((a,b)=> if(a._2 < b._2) a else b)
        //println("neigh:")
       // neigh.take(5).foreach(println)
        newDistance= distances.fullOuterJoin(neigh).map(findShort)
        //newDistance.take(5).foreach(println)
        checkDest= newDistance.filter(a=>a._1.equals("34"))
        var checkDestCount= checkDest.count()
        if (checkDestCount>0)
          loopB.break()
        distances=newDistance
      }
    }
    //println("final path:")
    checkDest.map(s=>s._2._1).saveAsTextFile("/home/milap/IdeaProjects/hadoopsparklearning/src/main/resources/ShortestPath")
    //checkDest.collect().foreach(s=>println(s"Shortest path from 12 to 34 is: ${s._2._1}"))
  }
  def findShort (s:(String,(Option[(String, Int)],Option[(String, Int)]))) ={
    val a = s._2._1 match {
      case Some(x) => x
      case None => null
    }
    val b = s._2._2 match {
      case Some(x) => x
      case None => null
    }
    if (a!=null) (s._1,a) else (s._1,b)
  }
}
