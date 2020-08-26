package com.coursera

import org.apache.spark.{SparkConf, SparkContext}

object twitter_countFollowers {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setAppName("Twitter_count").setMaster("local[2]"))
    val edges = sc.textFile(s"file:///home/milap/IdeaProjects/hadoopsparklearning/src/main/resources/twitter_sample_small.txt",4)
      .map(_.split("\t")).map(s=> (s(0).toLong,s(1).toLong))
    edges.take(10).foreach(println)
    val followers= edges.map(f=>(f._1,1)).reduceByKey((a,b)=>a+b)

    implicit def ordering[A <: (Long,Int)]: Ordering[A] = Ordering.by(_._2)
    // implicit ordering can be modified // here order by value
    // by default it is descending order by key
    followers.top(5).foreach(s =>println(s"user$s"))
    // implicit ordering modified to ascending order by value
    /*implicit val sortIntegersByString: Ordering[(Long, Int)] = new Ordering[(Long,Int)] {
       override def compare(a: (Long,Int), b: (Long,Int)): Int = {
               if(a._2 > b._2) {
                    -1
                 }else{
                    +1
                 }
             }
       }
    followers.top(5)(sortIntegersByString).foreach(s =>println(s"user reverse$s"))
*/
  }
}
