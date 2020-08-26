// broadcast and accumulator example
// Task- find probability of each word
// Solution-
// 1.Parse lines to get words and then group by to get word count
// 2.Get total word count through accumulator OR by count() which uses inbuilt accumulator
// 3.Broadcast total count to get probability of each word


package com.coursera

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.util.LongAccumulator
import org.apache.spark.{SparkConf, SparkContext}

object Accumulator {
  def main(args: Array[String]): Unit = {

    val sc=new SparkContext(new SparkConf().setAppName("MyAcc").setMaster("local[2]"))
    val data=sc.parallelize(List("This is good","I am good","i am looking at this book"))
    var word_count = sc.longAccumulator("word counter")
    val words=data
      .flatMap(l=>l.split(" "))
      .map(w=>accum(w,word_count)).reduceByKey((x,y)=>x+y) // count of each word
    words.collect().foreach(w=>println(s"fdgh${w}"))
    // Rather than collecting directly use count() and avoid accumulator for small data
    println(s"Total words:${word_count.value}")
    Thread.sleep(500)
    val countBroad= sc.broadcast(word_count.value) // creating broadcast variable
    val words1=words.map(w=> divide(w,countBroad)) // passing broadcast value
    words1.collect().foreach(println)
  }

  def accum(w: String ,word_count: LongAccumulator) : (String,Int)={
    word_count.add(1)
    (w.toLowerCase,1)
  }
  def divide(w : (String,Int), wordCount: Broadcast[java.lang.Long]): (String,Double) ={
    //println(s"word:${w._1} and count:${w._2} and total:${wordCount} and prob: ${w._2/wordCount.value.toDouble}")
    (w._1,w._2/wordCount.value.toDouble)  // use of broadcast value.
  }
}
