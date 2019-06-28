package com.stockprice

import java.io.File
import org.apache.commons.exec.CommandLine
import org.apache.commons.exec.DefaultExecutor
import org.apache.commons.io.FileUtils

import org.apache.spark.sql.SparkSession

class stockPrice {

  def getListOfFiles(dir: String): List[String] = {
    val file = new File(dir)
    file.listFiles.filter(_.isFile)
      .filter(_.getName.endsWith(".csv"))
      .map(_.getPath).toList
  }

  def access_s3(path: String, s: SparkSession): Unit = {
    val dataframe = s.read.options(Map("inferSchema"->"true","header" -> "true")).csv(path)
    dataframe.show()
    println("Shown the data")
    val from_home = "/home/admin1/IdeaProjects/StockMarket/src/main/Python/StockPrice/Data/"
    dataframe.repartition(1).write.option("header","true").format("csv").save(from_home)
    println("Written the csv data into file")
    val filenames = getListOfFiles(from_home)
    println(filenames(0))
    val out_path = "/home/admin1/IdeaProjects/StockMarket/src/main/Python/StockPrice/model/model.pkl"
    val pyfile = "/home/admin1/IdeaProjects/StockMarket/src/main/Python/StockPrice/model_builer/stock_Price_Trainer.py"

    s.sparkContext.addFile(pyfile)
    val paths = s.sparkContext.makeRDD(List(filenames(0)+" "+out_path))
    print(paths.collect().toString)
    val returned = paths.pipe(pyfile)
    val got = returned.collect()
    got.foreach(println(_))

    val cmd = new CommandLine("/home/admin1/IdeaProjects/StockMarket/src/main/shellscript.sh")
    cmd.addArgument(got(1))
    cmd.addArgument("s3://week7-stockprice/")

    val exec = new DefaultExecutor()
    exec.setWorkingDirectory(FileUtils.getUserDirectory())
    exec.execute(cmd)
    println("added to s3")

  }
}
