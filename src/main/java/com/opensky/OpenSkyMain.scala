package com.opensky

import org.apache.hadoop.fs._
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object OpenSkyMain {

  def main(args: Array[String]): Unit = {

    println("scala maven project")


    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkOpenSky").set("spark.driver.host", "localhost")

    conf.set("spark.testing.memory", "2147480000") // increased app memory

    val spark = SparkSession.builder()
      .config(conf)
      .getOrCreate()

    println("SparkOpenSky Spark session started....")

    // read data from sales data sample csv to DataFrame

    val csvData = spark.read
      .option("header", value = true)
      .option("inferSchema", value = true)
      .csv("data/sales_data_sample.csv")

    csvData.printSchema()
    //csvData.explain()

//    csvData.groupBy("YEAR_ID","PRODUCTLINE","STATUS")
//          .sum("SALES")
//          .where(csvData("STATUS") === "Shipped")
//          .orderBy(desc("YEAR_ID"))
//          .show()

    // Find the average sales amount for each year by product code from DataFrame

    val filteredJob = csvData.groupBy("YEAR_ID","PRODUCTLINE","STATUS")
      .agg(round(avg("SALES"), 2).as("AVERAGE_SALES_AMT"))
      .where(csvData("STATUS") === "Shipped")
      .orderBy(desc("YEAR_ID"))

    println(filteredJob.count())

    filteredJob.show(false)

    // write filtered data as CSV file

    filteredJob.repartition(1)
      .write
      .format("csv")
      .mode("overwrite")
      .option("header", value = true)
      .option("path", "output/")
      .save()


    val hadoopConfig = FileSystem.get(spark.sparkContext.hadoopConfiguration)

    val pathFiles = new Path("output/part*")

    val fileNames = hadoopConfig.globStatus(pathFiles)(0).getPath().getName()
    println(fileNames)
    hadoopConfig.rename(new Path("output/" + fileNames), new Path("output/output.csv"))

   // hadoopConfig.delete(new Path("mydata.csv-temp"), true)
   // csvData.show()
    spark.stop()


  }

}
