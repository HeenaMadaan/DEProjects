package com.coursera

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.immutable

object assignment_wordsArticle_tf_idf {
  def main(args: Array[String]): Unit = {
    val sc= new SparkContext(new SparkConf().setAppName("tf-idf").setMaster("local[2]"))
    val stopwords= sc.textFile("/home/milap/IdeaProjects/hadoopsparklearning/src/main/resources/stopwords.txt")
      .map(w=> w.toLowerCase)
    val stopB= sc.broadcast(stopwords.collect().toList)
    val tf= sc.textFile("/home/milap/IdeaProjects/hadoopsparklearning/src/main/resources/articles-part",4)
      .flatMap(line=>parse(line,stopB)).map{ case (a,b,c) => (b,(a,c))}
    val idf= tf.map(w=> (w._1,1)).reduceByKey((a,b)=>a+b). map(w=> (w._1,1/math.log(1+w._2).toFloat))
    val result = tf.join(idf).map{ case (a,((b,c),d)) => (a,b,c*d.toFloat)}
    result.saveAsTextFile("/home/milap/IdeaProjects/hadoopsparklearning/src/main/resources/words")
  }
  def parse(str: String, stoplist: Broadcast[List[String]]): immutable.Iterable[(String, String, Float)]
  ={
    val part = str.split("\t")
    //println(s"article id: ${part(0)}")
    //println(part(1))
    val line = part(1)
    var reg="^\\W+|\\W+$".r        // declaring regex
    val text1 = reg.replaceAllIn(line, "") // removing any spaces at the line end
    reg="\\W*\\s+\\W*".r
    val words=reg.split(text1).map(w=>(part(0),w.toLowerCase))    // splitting line to words
      .filter(a=> !stoplist.value.contains(a._2))
    //println(words.size)
    val wordCount= words.map{ case (a,b)=>((a,b),1)}.groupBy(w=>w)
      .map(w=>(w._1._1._1,w._1._1._2,w._2.length)).map(w=>(w._1,w._2,w._3/words.size.toFloat))
    wordCount
  }
}
