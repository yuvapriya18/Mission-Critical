package org.example;


import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import java.io.IOException;
import org.apache.spark.ml.clustering.KMeans;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

public class TrainAndSaveModel {
    public static void main(String[] args) throws IOException {
        // Initialize Spark session
        SparkSession spark = SparkSession.builder()
                .appName("PreTrainKMeansModel")
                .master("local[*]")
                .getOrCreate();

        // Define schema for static JSON data
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

        // Load static data from JSON file
        Dataset<Row> staticData = spark.read()
                .schema(schema)
                .json("/home/vishnu/IdeaProjects/Mission_critic/spacecraft-data.json");

        // Define feature columns for KMeans
        VectorAssembler assembler = new VectorAssembler()
                .setInputCols(new String[]{"altitude", "velocity",
                        "temperature", "pressure", "batteryVoltage"})
                .setOutputCol("features");

        // Transform the static dataset to features
        Dataset<Row> featureData = assembler.transform(staticData);

        // Define the KMeans model
        KMeans kmeans = new KMeans()
                .setK(2)  // Number of clusters
                .setFeaturesCol("features")
                .setPredictionCol("prediction");

        // Create a pipeline with stages (assembler -> kmeans)
        Pipeline pipeline = new Pipeline()
                .setStages(new
                        org.apache.spark.ml.PipelineStage[]{assembler, kmeans});

        // Train the model on the static dataset
        PipelineModel model = pipeline.fit(staticData);

        // Save the trained model to a path for later use in streaming
        model.write().overwrite().save("/home/vishnu/IdeaProjects/Mission_critic/kmeans-model");

        System.out.println("Model training complete and saved.");
    }

}