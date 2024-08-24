package org.example;

import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.ml.clustering.KMeans;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.OutputMode;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.sql.functions;

import java.util.concurrent.TimeoutException;

public class StreamingAnomalyMLModel {
    public static void main(String[] args) throws StreamingQueryException, TimeoutException {
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

    // Define feature columns for anomaly detection
    VectorAssembler assembler = new VectorAssembler()
            .setInputCols(new String[]{"altitude", "velocity",
                    "temperature", "pressure", "batteryVoltage"})
            .setOutputCol("assembledFeatures");

    // Apply VectorAssembler to the streaming data
    Dataset<Row> featureData = assembler.transform(parsedDF);

    // Load a pre-trained KMeans model
    PipelineModel model = PipelineModel.load("/home/vishnu/IdeaProjects/Mission_critic/kmeans-model");

    // Apply the pre-trained model to the streaming data for predictions
    Dataset<Row> predictions = model.transform(featureData);

    // Filter anomalies (if prediction != 0, it's considered an anomaly)
    Dataset<Row> anomalies = predictions.filter("prediction != 0");

    // Output anomalies to the console
        anomalies.writeStream()
                .format("console")
                .outputMode(OutputMode.Append())
                .start()
                .awaitTermination();
}
}
