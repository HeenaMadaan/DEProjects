package com.myprojects

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types._

object LearningSparkSQL {
  def main(args: Array[String]): Unit = {
    val sc= SparkSession. builder()
      .master("local")
      .appName("learnSparkSQL")
      .getOrCreate()

    import sc.implicits._  // import after creating spark session
    // enables to use $ instead of col(), convert RDD to dataframes and datasets

    val df= sc.read
      .option("header",true).csv("/home/milap/Downloads/FL_insurance_sample.csv")

    df.printSchema()
    df.show()

    // creating dataset - convert sequence to a dataset
    val dataset= Seq(1,2,3).toDS()
    dataset.show()

    //creating dataset-converting sequence of case classes to dataset
    val personDS= Seq(Person("Max",23),Person("Adam",24), Person("Ben",14)).toDS()
    personDS.show()

    // creating dataset from RDD
    var rdd= sc.sparkContext.parallelize(Seq((1,"Spark"),(2,"Databricks")))
    val rddDS = rdd.toDS()
    rddDS.show()

    // creating dataframe using case class sequence
    val df1= Seq(Person("Andy",15), Person("Ben", 20), Person("Alex",60)).toDF()
    df1.show()

    // creating dataframe using column names
    val df2= sc.sparkContext.parallelize(Seq("ABC","DEF")).toDF("Name")
    df2.show()

    // using createDataFrame from spark session directly and providing column names
    val df3= sc.createDataFrame(rdd).toDF("id","name")
    df3.printSchema()
    df3.show()
    df3.select($"name", $"id"+10).show()

    // defining schema for data frame
    val schema= StructType(Array(StructField("id",IntegerType,true),
      StructField("name",StringType,true)))
    val rdd1: RDD[Row] = rdd.map(r => Row(r._1,r._2))
    val df4= sc.createDataFrame(rdd1,schema)
    df4.show()
    df4.printSchema()

   // creating temporary view to run SQl query programatticaly
    df1.createTempView("People")
    sc.sql("SELECT * FROM people").show()

    // trying to access it in new session to check session scope
    //sc.newSession().sql("Select * from people where age>40").show()
    // generates error view or table not found

    df1.createGlobalTempView("people")
    sc.sql("select * from global_temp.people where age>40").show()

    sc.newSession().sql("select name from global_temp.people where age>40").show()
  }
  case class Person(name : String, age : Int)  //   case class, by use of which you define the schema of the DataFrame, should be defined outside of the method needing it.
}
