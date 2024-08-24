package org.example;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.OutputMode;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.ml.clustering.KMeans;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.sql.types.ArrayType;


import java.util.concurrent.TimeoutException;

public class StructuredStreamingKafkaExample {
    public static void main(String[] args) throws TimeoutException, StreamingQueryException {
        // Initialize Spark session
        SparkSession spark = SparkSession.builder()
                .appName("StructuredStreamingKafkaExample")
                .master("local[*]")
                .getOrCreate();

        // Define schema for JSON data

        StructType schema = new StructType(new StructField[]{
                // Basic Info
                DataTypes.createStructField("spacecraftId", DataTypes.StringType, true),
                DataTypes.createStructField("position",
                        new StructType(new StructField[]{
                                DataTypes.createStructField("latitude", DataTypes.DoubleType, true),
                                DataTypes.createStructField("longitude",
                                        DataTypes.DoubleType, true),
                                DataTypes.createStructField("altitude", DataTypes.DoubleType, true)
                        }), true),
                DataTypes.createStructField("velocity", DataTypes.DoubleType, true),
                DataTypes.createStructField("altitude", DataTypes.DoubleType, true),

                // Telemetry Data
                DataTypes.createStructField("timestamp", DataTypes.LongType, true),
                DataTypes.createStructField("temperature", DataTypes.DoubleType, true),
                DataTypes.createStructField("pressure", DataTypes.DoubleType, true),
                DataTypes.createStructField("batteryVoltage", DataTypes.DoubleType, true),
                DataTypes.createStructField("attitude",
                        DataTypes.createArrayType(DataTypes.DoubleType), true),

                // Command Data
                DataTypes.createStructField("commandID", DataTypes.StringType, true),
                DataTypes.createStructField("subsystem", DataTypes.StringType, true),
                DataTypes.createStructField("executionTime", DataTypes.StringType, true),
                DataTypes.createStructField("commandStatus", DataTypes.StringType, true),

                // Position and Navigation Data
                DataTypes.createStructField("positionCoordinates",
                        DataTypes.createArrayType(DataTypes.DoubleType), true),
                DataTypes.createStructField("velocityVectors",
                        DataTypes.createArrayType(DataTypes.DoubleType), true),
                DataTypes.createStructField("orbitalParameters",
                        DataTypes.StringType, true),

                // System Performance Data
                DataTypes.createStructField("cpuLoad", DataTypes.DoubleType, true),
                DataTypes.createStructField("memoryUsage", DataTypes.DoubleType, true),
                DataTypes.createStructField("dataTransmissionRate",
                        DataTypes.DoubleType, true),

                // Redundancy Data
                DataTypes.createStructField("isRedundantSystemActive",
                        DataTypes.BooleanType, true),
                DataTypes.createStructField("errorLog", DataTypes.StringType, true),

                // Power Management
                DataTypes.createStructField("batteryCharge", DataTypes.DoubleType, true),
                DataTypes.createStructField("solarPanelEfficiency",
                        DataTypes.DoubleType, true),
                DataTypes.createStructField("powerConsumption", DataTypes.DoubleType, true),

                // Fuel Status
                DataTypes.createStructField("fuelLevel", DataTypes.DoubleType, true),
                DataTypes.createStructField("fuelConsumptionRate",
                        DataTypes.DoubleType, true),

                // Sensor Data
                DataTypes.createStructField("radarData",
                        DataTypes.createArrayType(DataTypes.DoubleType), true),
                DataTypes.createStructField("lidarData",
                        DataTypes.createArrayType(DataTypes.DoubleType), true),
                DataTypes.createStructField("laserAltimeterData",
                        DataTypes.createArrayType(DataTypes.DoubleType), true),
                DataTypes.createStructField("infraredSensorData",
                        DataTypes.createArrayType(DataTypes.DoubleType), true),
                DataTypes.createStructField("microwaveSensorData",
                        DataTypes.createArrayType(DataTypes.DoubleType), true),
                DataTypes.createStructField("sarData",
                        DataTypes.createArrayType(DataTypes.DoubleType), true),
                DataTypes.createStructField("radioOccultationData",
                        DataTypes.createArrayType(DataTypes.DoubleType), true),
                DataTypes.createStructField("accumulationRadarData",
                        DataTypes.createArrayType(DataTypes.DoubleType), true),
                DataTypes.createStructField("imagerData",
                        DataTypes.createArrayType(DataTypes.DoubleType), true),
                DataTypes.createStructField("precipitationRadiometerData",
                        DataTypes.createArrayType(DataTypes.DoubleType), true),
                DataTypes.createStructField("waterVaporRadiometerData",
                        DataTypes.createArrayType(DataTypes.DoubleType), true),
                DataTypes.createStructField("seaSurfaceHeightData",
                        DataTypes.createArrayType(DataTypes.DoubleType), true),
                DataTypes.createStructField("soilMoistureData",
                        DataTypes.createArrayType(DataTypes.DoubleType), true),
                DataTypes.createStructField("temperatureProfileData",
                        DataTypes.createArrayType(DataTypes.DoubleType), true),
                DataTypes.createStructField("elevationData",
                        DataTypes.createArrayType(DataTypes.DoubleType), true),
                DataTypes.createStructField("landSeaSurfaceTemperatureData",
                        DataTypes.createArrayType(DataTypes.DoubleType), true),
                DataTypes.createStructField("atmosphericProfileData",
                        DataTypes.createArrayType(DataTypes.DoubleType), true),
                DataTypes.createStructField("cloudData",
                        DataTypes.createArrayType(DataTypes.DoubleType), true)
        });

        // Create streaming DataFrame from Kafka
        Dataset<Row> df = spark.readStream()
                .format("kafka")
                .option("kafka.bootstrap.servers", "localhost:9092")
                .option("subscribe", "bigstream")
                .load()
                .selectExpr("CAST(value AS STRING)");

        // Parse JSON data
        Dataset<Row> parsedDF =
                df.select(functions.from_json(functions.col("value"),
                        schema).as("data")).select("data.*");

    // Register as a temporary view
        parsedDF.createOrReplaceTempView("spacecraft_data");

    //1st Query - Command status of the craft(fail or success)

        spark.sql("SELECT commandStatus FROM spacecraft_data ")
                .writeStream()
                .format("console")
                .outputMode(OutputMode.Append())
                .start();


        //Query 2: Detect Anomalies in Power Consumption (e.g., Power
        //                Consumption Above a Certain Threshold)
        //
        //        This query  detect spacecrafts with power
        //        consumption higher.

        spark.sql("SELECT spacecraftId,  timestamp, powerConsumption, solarPanelEfficiency, batteryCharge FROM spacecraft_data WHERE powerConsumption > 500;  ")
                .writeStream()
                .format("console")
                .outputMode(OutputMode.Append())
                .start();

        //Query 3: Track Error Logs in Real-Time
        //
        //        This query fetches all error logs for spacecraft systems where
        //        redundancy is not active.
        spark.sql("SELECT timestamp,spacecraftId,errorLog FROM spacecraft_data WHERE isRedundantSystemActive = false AND errorLog IS NOT NULL;")
                .writeStream()
                .format("console")
                .outputMode(OutputMode.Append())
                .start();

     //Query 4: Monitor Telementary DAta by Subsystem and Status
        //    This query will show a count of telemetry data grouped by `subsystem`
        //        and `commandStatus`, helping to track the status of different
        //        subsystems.

        spark.sql("SELECT subsystem,commandStatus,COUNT(*) AS status_count FROM spacecraft_data GROUP BY subsystem,commandStatus;")
                .writeStream()
                .format("console")
                .outputMode(OutputMode.Complete())
                .start();
        spark.streams().awaitAnyTermination();





    }
}
