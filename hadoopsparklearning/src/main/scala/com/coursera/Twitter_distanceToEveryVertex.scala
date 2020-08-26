package com.coursera

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.util.control.Breaks

object Twitter_distanceToEveryVertex {
  def main(args: Array[String]): Unit = {
    val sc= new SparkContext(new SparkConf().setAppName("distanceToVertex").setMaster("local[2]"))
    val edges= sc.textFile("file:///home/milap/IdeaProjects/hadoopsparklearning/src/main/resources/twitter_sample_small.txt",4)
    //val edges = sc.parallelize(List("2\t3","2\t4","3\t4","4\t5","4\t6","5\t6","5\t3"))
      .map(_.split("\t")).map(s=>(s(1),s(0)))
   // println("Edges:")
  //  edges.collect().foreach(println)
    var d=0
    var newDistance: RDD[(String, Int)] = null
    var distances = sc.parallelize(List(12)).map(s=> (s.toString(),d))
  //  println("distances:")
  //  distances.collect().foreach(println)
    val loop = new Breaks;
    loop.breakable {
      while (true) {
        // ex. (v12,d0) joined with edges to get neighbours
        // increase each neighbor distance by 1 from previous distance
        // shows new neighbour is at distance b+1 from the initial vertex
        var neighJ = distances.join(edges)
      //  println("neighbours:")
      //  neighJ.collect().foreach(println)
        var neigh = neighJ.map { case (a, (b, c)) => (c, b + 1) }
            .reduceByKey((a,b)=>scala.math.min(a,b))

       println("neighbours join:")
        neigh.take(5).foreach(println)
        // neigh forms new distance calculation and distances is old distance
        // compare old distance with new distance to get smaller one
         newDistance = distances.fullOuterJoin(neigh).map(findShort)
        //newDistance.take(20).foreach(println)

        // ex- dsitances = (2,0), neigh((3,1),(4,1))
        // full outer join output= (4,(None,Some(1)),  (2,(Some(0),None)),  (3,(None,Some(1)))
        //println("New distances:")
        implicit def ordering[A <: (String, Int)] : Ordering[A] = Ordering.by(_._2)
        newDistance.top(10).foreach(println)
        d = d + 1
        var count = newDistance.filter(s => s._2 == d).count()
        if (count > 0) distances = newDistance else loop.break
      }
    }
    implicit def ordering[A <: (String, Int)] : Ordering[A] = Ordering.by(_._2)
    newDistance.top(5).foreach(println)
  }
  def findShort(s:(String, (Option[Int],Option[Int])))={
    val x= s._2._1 match {
      case Some(v)=> v
      case None => 0
    }
    val y=s._2._2 match {
      case Some(v) => v
      case None => 0
    }
    if (x!=0) (s._1,x) else (s._1,y)

  }

}
