package org.example;

import com.google.gson.Gson;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

public class GenerateSpacecraftData {
    private static final Gson gson = new Gson();
    private static final Random random = new Random();

    public static void main(String[] args) throws InterruptedException, IOException {
        File file = new File("spacecraft-data.json");
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);

        while (true) {
            int recordsCount = random.nextInt(15);
            for (int i = 0; i <= recordsCount; i++) {
                SpacecraftData spacecraftData = new SpacecraftData();

                // Generate random data
                spacecraftData.setSpacecraftId(UUID.randomUUID().toString());
                spacecraftData.setPosition(generateRandomCoordinate());
                spacecraftData.setVelocity(random.nextDouble() * 10000); // Random velocity
                spacecraftData.setAltitude(random.nextDouble() * 10000); // Random altitude
                spacecraftData.setTimestamp(System.currentTimeMillis());
                spacecraftData.setTemperature(random.nextDouble() * 100); // Random temperature
                spacecraftData.setPressure(random.nextDouble() * 1000); // Random pressure
                spacecraftData.setBatteryVoltage(random.nextDouble() * 12); // Random voltage
                spacecraftData.setAttitude(generateRandomAttitude());

                // Setting random values for other fields similarly
                spacecraftData.setCommandID(UUID.randomUUID().toString());
                spacecraftData.setSubsystem("Subsystem-" + random.nextInt(10));
                spacecraftData.setExecutionTime(System.currentTimeMillis() + "");
                spacecraftData.setCommandStatus(random.nextBoolean() ? "Successful" : "Failed");
                spacecraftData.setPositionCoordinates(generateRandomArray(3));
                spacecraftData.setVelocityVectors(generateRandomArray(3));
                spacecraftData.setOrbitalParameters("Apogee:" + random.nextDouble() * 10000 + ", Perigee:" + random.nextDouble() * 10000);

                spacecraftData.setCpuLoad(random.nextDouble() * 100); // CPU load percentage
                spacecraftData.setMemoryUsage(random.nextDouble() * 100); // Memory usage percentage
                spacecraftData.setDataTransmissionRate(random.nextDouble() * 1000); // Data rate

                spacecraftData.setRedundantSystemActive(random.nextBoolean());
                spacecraftData.setErrorLog("ErrorLog-" + random.nextInt(1000));

                spacecraftData.setBatteryCharge(random.nextDouble() * 100); // Battery charge percentage
                spacecraftData.setSolarPanelEfficiency(random.nextDouble() * 100); // Solar panel efficiency percentage
                spacecraftData.setPowerConsumption(random.nextDouble() * 1000); // Power consumption in watts

                spacecraftData.setFuelLevel(random.nextDouble() * 100); // Fuel level percentage
                spacecraftData.setFuelConsumptionRate(random.nextDouble() * 10); // Fuel consumption rate

                spacecraftData.setRadarData(generateRandomArray(10));
                spacecraftData.setLidarData(generateRandomArray(10));
                spacecraftData.setLaserAltimeterData(generateRandomArray(10));
                spacecraftData.setInfraredSensorData(generateRandomArray(10));
                spacecraftData.setMicrowaveSensorData(generateRandomArray(10));
                spacecraftData.setSarData(generateRandomArray(10));
                spacecraftData.setRadioOccultationData(generateRandomArray(10));
                spacecraftData.setAccumulationRadarData(generateRandomArray(10));
                spacecraftData.setImagerData(generateRandomArray(10));
                spacecraftData.setPrecipitationRadiometerData(generateRandomArray(10));
                spacecraftData.setWaterVaporRadiometerData(generateRandomArray(10));
                spacecraftData.setSeaSurfaceHeightData(generateRandomArray(10));
                spacecraftData.setSoilMoistureData(generateRandomArray(10));
                spacecraftData.setTemperatureProfileData(generateRandomArray(10));
                spacecraftData.setElevationData(generateRandomArray(10));
                spacecraftData.setLandSeaSurfaceTemperatureData(generateRandomArray(10));
                spacecraftData.setAtmosphericProfileData(generateRandomArray(10));
                spacecraftData.setCloudData(generateRandomArray(10));

                // Write JSON representation to file
                bw.write(gson.toJson(spacecraftData) + "\n");
            }

            bw.flush();
            Thread.sleep(1000);
            System.out.println("Written " + recordsCount + " records to the file.");
        }
    }

    private static SpacecraftData.Coordinate generateRandomCoordinate() {
        SpacecraftData.Coordinate coord = new SpacecraftData.Coordinate();
        coord.setLat(random.nextDouble() * 180 - 90); // Latitude between -90 and 90
        coord.setLon(random.nextDouble() * 360 - 180); // Longitude between -180 and 180
        return coord;
    }

    private static double[] generateRandomArray(int size) {
        double[] array = new double[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextDouble() * 100; // Random values between 0 and 100
        }
        return array;
    }

    private static double[] generateRandomAttitude() {
        return new double[]{random.nextDouble() * 360, random.nextDouble() * 360, random.nextDouble() * 360}; // Random Euler angles
    }
}