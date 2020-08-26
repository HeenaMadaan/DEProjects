package com.myprojects

import org.apache.spark.sql.SparkSession

object DFReader {
  def main(args: Array[String]): Unit = {
    val spark= SparkSession
      .builder
      .appName("ParquetReader")
      .master("local")
      .getOrCreate()

    val readDf= spark.read.parquet("/home/milap/IdeaProjects/hadoopsparklearning/src/main/resources/Date_part=20200315/Hour_part=22")
    readDf.createTempView("22hour")
    readDf.sqlContext.sql("select url,Minute_part from 22hour where Minute_part=56")
      .show()
    //readDf.show()
  }
}
