// Task-  Return all the article ids where the word is present. Words present in only one article id in one file. Words present in more than 1 article id in other file.
//Input- file containing lines comprising of article id tab separated article
//Output- words tab separated with articles ids where word. Single article presence in one file and rest in other file
// also print the count of two files
// Solution :
// 1. Parse input file to get article id and article words. Pair word with article id
// 2. RDD is group by word and then filtered to get words having count of article id>1 in one RDD and words with count of article id =1 in other RDD
// 3. Use accumulator to count the rows in two files

package com.coursera

import org.apache.spark.util.LongAccumulator
import org.apache.spark.{SparkConf, SparkContext}

object spark_articleGrouping2 {
  def main(args: Array[String]): Unit = {
    val sc=new SparkContext(new SparkConf().setAppName("Pairs").setMaster("local[2]"))
    val wiki = sc.textFile("file:///home/milap/IdeaProjects/hadoopsparklearning/src/main/resources/articles-part",16)
      .flatMap(line => parse(line)).groupByKey() // group by words
    val group_len=wiki.map(w=>(w,w._2.size)).persist()
    // persisting RDD having word, article id iterable and article id count to avoid recalculation
    var s_count=sc.longAccumulator  // to count single article words
    var d_count=sc.longAccumulator   // to count multiple article words
    val group1_single=group_len.
      filter(w=>w._2==1).map(w=>w._1)  // filtering single occurrence using article id count and separating count from word and article iterable
      .map(w=> (w._1,w._2.mkString(",")))  // converting article id iterable into string
      .map(w=> w._1.concat("\t").concat(w._2)) // word tab separated with article id string
      .map(w => cal_count(w,s_count)) // calling accumulator function to count words having single article occurrence
    val group2_duplicate=group_len
      .filter(w=>w._2>1)
      .map(w=>w._1)
      .map(w=> (w._1,w._2.mkString(",")))
      .map(w=> w._1.concat("\t").concat(w._2))
      .map(w => cal_count(w,d_count))
    group1_single.saveAsTextFile("/home/milap/IdeaProjects/hadoopsparklearning/src/main/resources/articleListSingle1")
    group2_duplicate.saveAsTextFile("/home/milap/IdeaProjects/hadoopsparklearning/src/main/resources/articleListDuplicate1")
    println(s"Single article count:${s_count.value}") // saving into file action calls the accumulator function to count
    println(s"Duplicate article count:${d_count.value}")
  }
  def parse(line: String): Array[(String, String)] ={
    val idx = line.indexOf("\t")   // finds first occurrence of tab character
    var article_id=line.substring(0,idx)
    var text = line.substring(idx+1)      // breaks the string from tab+1 index till end
    var reg="^\\W+|\\W+$".r        // declaring regex
    var text1=reg.replaceAllIn(text,"")   // removing any spaces at the line end
    reg="\\W*\\s+\\W*".r
    val words =reg.split(text1)    // splitting line to words
   words.distinct.map(s=> (s,article_id))  // only distinct words are needed
  }
  def cal_count(str: String, accumulator: LongAccumulator)={
    accumulator.add(1)
    str
  }
}
