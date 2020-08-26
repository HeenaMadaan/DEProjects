// to study join example

package com.coursera

import org.apache.spark.{SparkConf, SparkContext}

object wordyWord {
  def main(args: Array[String]): Unit = {
    val sc=new SparkContext(new SparkConf().setAppName("Wordness").setMaster("local[2]"))
    val data=sc.parallelize(List("I am doing good","Do not worry about it","You are good","It is gonna be good"))
    //parsing words and caching it with the word and word count
    val wc=data.flatMap(line=>parseline(line)).reduceByKey((a,b)=>a+b).cache()
    wc.top(20).foreach(w=> println(s"word ${w._1} count is: ${w._2}"))
    print(s"Unique words:${wc.count()}")  // total word count
    print(s"Duplicate words are:${wc.filter(w => w._2>1).count()}") // duplicate words count
    println(s"Non duplicate words are:${wc.filter(w=>w._2==1).count()}") // single words count
    val words=sc.parallelize(Array(("this",2),("is",5),("good",10)))
    val pairs=sc.parallelize(Array((("this","is"),3),(("is","good"),4)))
    val tmp1 = pairs.map {case((a,b),c) => (a, (b,c))} // maping with case statement to get a new formatted pair
    val tmp2 = tmp1.join(words).map{case(a, ((b,c), e)) => (b, (a,c,e))} // joining and formatting pair for next join
    val res = tmp2.join(words).map{case(b, ((a,c,e), d)) => ((a,b), c,d,e)}
    val PMI= res.map(w=>{
      ((w._1),math.log(w._2)/(w._3*w._4))
    })

    println(res.collect().mkString(","))
    println(PMI.collect().foreach(println))
  }
  def parseline(s: String): Map[String,Int] ={
    s.split(" ")
      .groupBy(words=> words)   // (word, string[Iterable])
      .map(wc=>(wc._1,wc._2.length))  // (word,word_count)
  }
}
