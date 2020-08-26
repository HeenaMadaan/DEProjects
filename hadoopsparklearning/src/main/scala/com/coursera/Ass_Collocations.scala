package com.coursera

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.util.LongAccumulator
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer

object Ass_Collocations {
  def main(args: Array[String]): Unit = {
    val sc=new SparkContext(new SparkConf().setAppName("Pairs").setMaster("local[2]"))
    val stopFile=sc.textFile("file:///home/milap/IdeaProjects/hadoopsparklearning/src/main/resources/stopwords.txt")
      .map(w=>w.toLowerCase)
    val stopList=sc.broadcast(stopFile.collect().toList)

    var word_total=sc.longAccumulator("Word_accumulator")
    var pair_total=sc.longAccumulator("Pair_accumulator")
    val wiki = sc.textFile("file:///home/milap/IdeaProjects/hadoopsparklearning/src/main/resources/articles-part",16)
    // val wiki=sc.parallelize(List("20\t)I am doing good~", "10\tDo+not \"worry\" about ________- it", "15\tYou are good", "21\tIt is gonna be good","24\t(anarcho-pacifism),    George Woodcock. Anarchism: A History of Libertarian Ideas and Movements (1962)"))
      .flatMap(line => parse(line,stopList,word_total,pair_total)).persist()  //persisting words pairs
    println("sleeping")

    Thread.sleep(200)
    val singles=wiki.map(w=>(w._1,1)).reduceByKey((x,y) => x+y)   //single words count
    println(s"wiki total:${wiki.count()}")
    val pairsCount = wiki.filter(w=>w._2!="null#")
      .map(w=> ((w._1,w._2),1))
    .reduceByKey((x,y)=>x+y).persist()  // pairs word count
    println(s"Pairs1 size:${pairsCount.count()}")
    singles.take((20)).foreach(println)
    pairsCount.take((20)).foreach(println)
    println(s"Total words:${word_total.value}")
    println(s"Total pairs:${pair_total.value}")
    val wordB= sc.broadcast(word_total.value)
    val pairB = sc.broadcast(pair_total.value)
    val singleProb= singles.map(w=> dividew(w,wordB))   // finding single words probability by count(w)/totalw
    val pairsProb = pairsCount.map(w=> divide(w,pairB))  // finding paired words probability by count(w1w2)/totalw1w2
    val freqPairs= pairsCount.filter(w=> w._2>=500)    // filtering paired words with occurrence of >=500
    val freqPairProb = freqPairs.join(pairsProb).map{case ((a,b),(c,d))=>(a,(b,d))}.persist() // a=w1, b=w2,d=P(ab)
    val freqPairW1Prob= freqPairProb.join(singleProb).map{case (a,((b,d),e))=> (b,(a,d,e))}// finding P(a), e=P(a)
    val freqPairW1W2Prob = freqPairW1Prob.join(singleProb).map{case (b,((a,d,e),f)) => ((a,b),d,e,f)}  // finding P(b), e=P(b)
    //calculating PMI
    val PMI= freqPairW1W2Prob.map(w=> (w._1,w._2,math.log(w._2/(w._3*w._4)))).persist()
    val NPMI= PMI.map(w=> (w._3/(-1*math.log(w._2)),w._1._1+"_"+w._1._2))
    val result= NPMI.top(39)
    println(s"Freq pairs : ${freqPairs.count()}")
    println("pairs prob:")
    singles.saveAsTextFile("/home/milap/IdeaProjects/hadoopsparklearning/src/main/resources/CollocationsOP/singleWordCount")
    //group2_duplicate.saveAsTextFile("/home/milap/IdeaProjects/hadoopsparklearning/src/main/resources/articleListDuplicate")

  }
  def parse(line: String, stopList:Broadcast[List[String]], wacc : LongAccumulator,pacc : LongAccumulator): Array[(String,String)] ={
    val idx = line.indexOf("\t")   // finds first occurrence of tab character
    var article_id=line.substring(0,idx)
    var text = line.substring(idx+1)      // breaks the string from tab+1 index till end
    var reg="^\\W+|\\W+$".r        // declaring regex
    var text1=reg.replaceAllIn(text,"")   // removing any spaces at the line end
    reg="\\W*\\s+\\W*".r
    val words =reg.split(text1).map(w=>w.toLowerCase)    // splitting line to words
    val filtered=words.filter(w => !stopList.value.contains(w))
 //   println(s"Filtered length:${filtered.length}")
    wacc.add(filtered.length)
    var wordsPair = new ListBuffer[(String,String)]()
    for (i <- 0 until filtered.length-2){
        wordsPair +=((words(i),words(i+1)))
    }
    pacc.add(wordsPair.length)
    wordsPair+=((words(words.length-1),"null#"))
    wordsPair.toArray
  }
  def divide(w: ((String,String), Int), total: Broadcast[java.lang.Long]): ((String,String), Float) = {(w._1,w._2/total.value.toFloat)}
  def dividew(w: (String, Int), total: Broadcast[java.lang.Long]): (String, Float) = {(w._1,w._2/total.value.toFloat)}

  def cal_count(str: String, accumulator: LongAccumulator)={
    accumulator.add(1)
    str
  }
}
