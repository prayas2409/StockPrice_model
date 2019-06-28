package com.stockprice

import org.apache.spark.sql.SparkSession


object stockmain {
//  def fetchFilesFromLocalDirectory(path : String) : List[File] = {

  def main(args: Array[String]): Unit = {

    val s = SparkSession.builder().appName("stockmarkets").master("local").getOrCreate()
    val sc = s.sparkContext
    val accessKeyId: String = System.getenv("fs.s3a.access.key")
    val secretAccessKey: String = System.getenv("fs.s3a.secret.key")
    sc.hadoopConfiguration.set("fs.s3a.access.key", accessKeyId)
    sc.hadoopConfiguration.set("fs.s3a.secret.key", secretAccessKey)
    sc.hadoopConfiguration.set("fs.s3a.endpoint", "s3.ap-south-1.amazonaws.com")
    sc.hadoopConfiguration.set("fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")
    val sp = new stockPrice()
    sp.access_s3("s3a://week7-stockprice/HistoricalQuotes.csv",s)
//    "s3a://week7-stockprice/HistoricalQuotes.csv"

  }
}
