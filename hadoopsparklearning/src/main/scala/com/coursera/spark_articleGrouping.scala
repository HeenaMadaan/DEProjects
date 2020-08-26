// Task-  Return all the article ids where the word is present
//Input- file containing lines comprising of article id tab separated article
//Output- words tab separated with all the articles ids where word is present

// Solution :
// 1. Parse input file to get article id and article words. Pair word with article id
// 2. group by words and join article ids separated with #


package com.coursera

import org.apache.spark.{SparkConf, SparkContext}

import scala.io.Source
import scala.util.matching.Regex

object spark_articleGrouping {
  def main(args: Array[String]): Unit = {
    val sc=new SparkContext(new SparkConf().setAppName("Pairs").setMaster("local[2]"))
    val wiki = sc.textFile("file:///home/milap/IdeaProjects/hadoopsparklearning/src/main/resources/articles-part",16)
      .flatMap(line => parse(line))   // (word, articleid) pairs
    // group by words and join all the article ids with #
    // finally tab separated output saved into file
    val group_w=wiki.groupByKey().map(w=> (w._1,w._2.mkString("#"))).map(w=> w._1.concat("\t").concat(w._2))
      //.groupBy(_._1)
   // wiki.top(5).foreach(println)
    //group_w.takeOrdered(50).foreach(w=>println(s"word:${w._1} article list is : ${w._2}"))
    //wiki.take(10).foreach(w=> println(s"word ${w._1} count is: ${w._2}"))
    group_w.saveAsTextFile("/home/milap/IdeaProjects/hadoopsparklearning/src/main/resources/articleList1")
  }
  def parse(line: String): Array[(String, String)] ={
    val idx = line.indexOf("\t")   // finds first occurrence of tab character
    var article_id=line.substring(0,idx)
    var text = line.substring(idx+1)      // breaks the string from tab+1 index till end
    var reg="^\\W+|\\W+$".r        // declaring regex
    var text1=reg.replaceAllIn(text,"")   // removing any spaces at the line end
    reg="\\W*\\s+\\W*".r
    val words =reg.split(text1)    // splitting line to words
    //words.map(w=> (w,1))
   // (article_id,words.size)  to send article and its no of words as a pair, and use map at the source to collect
    words.distinct.map(s=> (s,article_id))
  }
}
