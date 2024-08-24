package org.example;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.Gson;

public class SparkSqlExample {
    public static void main(String[] args) throws IOException {

        File file = new File("data.json");
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);
        final Gson gson = new Gson();

        SparkConf conf = new SparkConf().setMaster("local").setAppName("Spark-Sql-Example");
        SparkSession spark = SparkSession.builder().config(conf).getOrCreate();
        Dataset<Row> df = spark.read().json("spacecraft-data.json");
        df.printSchema();
        df.createOrReplaceTempView("space");
        Dataset<Row> res = spark.sql("select spacecraftId from space where batteryCharge < 30");
        Dataset<Row> res1 = spark.sql("select spacecraftId from space where fuelLevel < 20");
        //System.out.println(res);
        res.write().json("low-battery-info");
        res1.write().json("low-fuel-info");
        bw.write(gson.toJson(res) + "\n");
    }
}
