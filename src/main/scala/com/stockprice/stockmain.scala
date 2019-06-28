package com.stockprice

import org.apache.hadoop.fs.LocalFileSystem
import org.apache.hadoop.hdfs.DistributedFileSystem
import org.apache.spark.sql.SparkSession


object stockmain {
//  def fetchFilesFromLocalDirectory(path : String) : List[File] = {

  def main(args: Array[String]): Unit = {

    val s = SparkSession.builder().appName("stockmarkets").master("local[*]").getOrCreate()
    val sc = s.sparkContext
    val accessKeyId: String = System.getenv("fs.s3a.access.key")
    val secretAccessKey: String = System.getenv("fs.s3a.secret.key")
    sc.hadoopConfiguration.set("fs.s3a.access.key", accessKeyId)
    sc.hadoopConfiguration.set("fs.s3a.secret.key", secretAccessKey)
    sc.hadoopConfiguration.set("fs.s3a.endpoint", "s3.ap-south-1.amazonaws.com")
    sc.hadoopConfiguration.set("fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")
    sc.hadoopConfiguration.set("fs.hdfs.impl", classOf[DistributedFileSystem].getName)
    sc.hadoopConfiguration.set("fs.file.impl", classOf[LocalFileSystem].getName)

    val sp = new stockPrice()
    sp.access_s3(args(0),s)
//    "s3a://week7-stockprice/HistoricalQuotes.csv"

  }
}
