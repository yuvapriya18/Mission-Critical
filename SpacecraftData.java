package org.example;


import com.google.gson.Gson;

public class SpacecraftData {

    // Basic Information
    private String spacecraftId;
    private Coordinate position;
    private double velocity;
    private double altitude;

    // Telemetry Data
    private long timestamp; // UTC Time
    private double temperature;
    private double pressure;
    private double batteryVoltage;
    private double[] attitude; // Orientation in quaternions or Euler angles

    // Command Data
    private String commandID;
    private String subsystem;
    private String executionTime; // UTC Time
    private String commandStatus; // Successful, Pending, Failed

    // Position and Navigation Data
    private double[] positionCoordinates; // X, Y, Z
    private double[] velocityVectors; // Vx, Vy, Vz
    private String orbitalParameters; // Apogee, Perigee, Inclination

    // System Performance Data
    private double cpuLoad;
    private double memoryUsage;
    private double dataTransmissionRate;

    // Redundancy Data
    private boolean isRedundantSystemActive;
    private String errorLog;

    // Power Management
    private double batteryCharge;
    private double solarPanelEfficiency;
    private double powerConsumption;

    // Fuel Status
    private double fuelLevel; // Percentage of remaining fuel
    private double fuelConsumptionRate; // Fuel consumption rate in units per time

    // Sensor Data
    private double[] radarData; // Placeholder for radar sensor data
    private double[] lidarData; // Placeholder for lidar sensor data
    private double[] laserAltimeterData; // Placeholder for laser altimeter data
    private double[] infraredSensorData; // Placeholder for active infrared sensor data
    private double[] microwaveSensorData; // Placeholder for active microwave sensor data
    private double[] sarData; // Placeholder for synthetic aperture radar data
    private double[] radioOccultationData; // Placeholder for radio occultation data
    private double[] accumulationRadarData; // Placeholder for accumulation radar data
    private double[] imagerData; // Placeholder for advanced imager data
    private double[] precipitationRadiometerData; // Placeholder for precipitation radiometer data
    private double[] waterVaporRadiometerData; // Placeholder for water vapor radiometer data
    private double[] seaSurfaceHeightData; // Placeholder for sea-surface height radar altimeter data
    private double[] soilMoistureData; // Placeholder for soil moisture data
    private double[] temperatureProfileData; // Placeholder for temperature profile data
    private double[] elevationData; // Placeholder for elevation measurement data
    private double[] landSeaSurfaceTemperatureData; // Placeholder for land and sea surface temperature data
    private double[] atmosphericProfileData; // Placeholder for atmospheric profile data
    private double[] cloudData; // Placeholder for cloud and aerosol data

    // Getter and Setter methods for all fields...

    public String getSpacecraftId() {
        return spacecraftId;
    }

    public void setSpacecraftId(String spacecraftId) {
        this.spacecraftId = spacecraftId;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getBatteryVoltage() {
        return batteryVoltage;
    }

    public void setBatteryVoltage(double batteryVoltage) {
        this.batteryVoltage = batteryVoltage;
    }

    public double[] getAttitude() {
        return attitude;
    }

    public void setAttitude(double[] attitude) {
        this.attitude = attitude;
    }

    public String getCommandID() {
        return commandID;
    }

    public void setCommandID(String commandID) {
        this.commandID = commandID;
    }

    public String getSubsystem() {
        return subsystem;
    }

    public void setSubsystem(String subsystem) {
        this.subsystem = subsystem;
    }

    public String getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(String executionTime) {
        this.executionTime = executionTime;
    }

    public String getCommandStatus() {
        return commandStatus;
    }

    public void setCommandStatus(String commandStatus) {
        this.commandStatus = commandStatus;
    }

    public double[] getPositionCoordinates() {
        return positionCoordinates;
    }

    public void setPositionCoordinates(double[] positionCoordinates) {
        this.positionCoordinates = positionCoordinates;
    }

    public double[] getVelocityVectors() {
        return velocityVectors;
    }

    public void setVelocityVectors(double[] velocityVectors) {
        this.velocityVectors = velocityVectors;
    }

    public String getOrbitalParameters() {
        return orbitalParameters;
    }

    public void setOrbitalParameters(String orbitalParameters) {
        this.orbitalParameters = orbitalParameters;
    }

    public double getCpuLoad() {
        return cpuLoad;
    }

    public void setCpuLoad(double cpuLoad) {
        this.cpuLoad = cpuLoad;
    }

    public double getMemoryUsage() {
        return memoryUsage;
    }

    public void setMemoryUsage(double memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

    public double getDataTransmissionRate() {
        return dataTransmissionRate;
    }

    public void setDataTransmissionRate(double dataTransmissionRate) {
        this.dataTransmissionRate = dataTransmissionRate;
    }

    public boolean isRedundantSystemActive() {
        return isRedundantSystemActive;
    }

    public void setRedundantSystemActive(boolean redundantSystemActive) {
        isRedundantSystemActive = redundantSystemActive;
    }

    public String getErrorLog() {
        return errorLog;
    }

    public void setErrorLog(String errorLog) {
        this.errorLog = errorLog;
    }

    public double getBatteryCharge() {
        return batteryCharge;
    }

    public void setBatteryCharge(double batteryCharge) {
        this.batteryCharge = batteryCharge;
    }

    public double getSolarPanelEfficiency() {
        return solarPanelEfficiency;
    }

    public void setSolarPanelEfficiency(double solarPanelEfficiency) {
        this.solarPanelEfficiency = solarPanelEfficiency;
    }

    public double getPowerConsumption() {
        return powerConsumption;
    }

    public void setPowerConsumption(double powerConsumption) {
        this.powerConsumption = powerConsumption;
    }

    public double getFuelLevel() {
        return fuelLevel;
    }

    public void setFuelLevel(double fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    public double getFuelConsumptionRate() {
        return fuelConsumptionRate;
    }

    public void setFuelConsumptionRate(double fuelConsumptionRate) {
        this.fuelConsumptionRate = fuelConsumptionRate;
    }

    public double[] getRadarData() {
        return radarData;
    }

    public void setRadarData(double[] radarData) {
        this.radarData = radarData;
    }

    public double[] getLidarData() {
        return lidarData;
    }

    public void setLidarData(double[] lidarData) {
        this.lidarData = lidarData;
    }

    public double[] getLaserAltimeterData() {
        return laserAltimeterData;
    }

    public void setLaserAltimeterData(double[] laserAltimeterData) {
        this.laserAltimeterData = laserAltimeterData;
    }

    public double[] getInfraredSensorData() {
        return infraredSensorData;
    }

    public void setInfraredSensorData(double[] infraredSensorData) {
        this.infraredSensorData = infraredSensorData;
    }

    public double[] getMicrowaveSensorData() {
        return microwaveSensorData;
    }

    public void setMicrowaveSensorData(double[] microwaveSensorData) {
        this.microwaveSensorData = microwaveSensorData;
    }

    public double[] getSarData() {
        return sarData;
    }

    public void setSarData(double[] sarData) {
        this.sarData = sarData;
    }

    public double[] getRadioOccultationData() {
        return radioOccultationData;
    }

    public void setRadioOccultationData(double[] radioOccultationData) {
        this.radioOccultationData = radioOccultationData;
    }

    public double[] getAccumulationRadarData() {
        return accumulationRadarData;
    }

    public void setAccumulationRadarData(double[] accumulationRadarData) {
        this.accumulationRadarData = accumulationRadarData;
    }

    public double[] getImagerData() {
        return imagerData;
    }

    public void setImagerData(double[] imagerData) {
        this.imagerData = imagerData;
    }

    public double[] getPrecipitationRadiometerData() {
        return precipitationRadiometerData;
    }

    public void setPrecipitationRadiometerData(double[] precipitationRadiometerData) {
        this.precipitationRadiometerData = precipitationRadiometerData;
    }

    public double[] getWaterVaporRadiometerData() {
        return waterVaporRadiometerData;
    }

    public void setWaterVaporRadiometerData(double[] waterVaporRadiometerData) {
        this.waterVaporRadiometerData = waterVaporRadiometerData;
    }

    public double[] getSeaSurfaceHeightData() {
        return seaSurfaceHeightData;
    }

    public void setSeaSurfaceHeightData(double[] seaSurfaceHeightData) {
        this.seaSurfaceHeightData = seaSurfaceHeightData;
    }

    public double[] getSoilMoistureData() {
        return soilMoistureData;
    }

    public void setSoilMoistureData(double[] soilMoistureData) {
        this.soilMoistureData = soilMoistureData;
    }

    public double[] getTemperatureProfileData() {
        return temperatureProfileData;
    }

    public void setTemperatureProfileData(double[] temperatureProfileData) {
        this.temperatureProfileData = temperatureProfileData;
    }

    public double[] getElevationData() {
        return elevationData;
    }

    public void setElevationData(double[] elevationData) {
        this.elevationData = elevationData;
    }

    public double[] getLandSeaSurfaceTemperatureData() {
        return landSeaSurfaceTemperatureData;
    }

    public void setLandSeaSurfaceTemperatureData(double[] landSeaSurfaceTemperatureData) {
        this.landSeaSurfaceTemperatureData = landSeaSurfaceTemperatureData;
    }

    public double[] getAtmosphericProfileData() {
        return atmosphericProfileData;
    }

    public void setAtmosphericProfileData(double[] atmosphericProfileData) {
        this.atmosphericProfileData = atmosphericProfileData;
    }

    public double[] getCloudData() {
        return cloudData;
    }

    public void setCloudData(double[] cloudData) {
        this.cloudData = cloudData;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static class Coordinate {
        private double lon;
        private double lat;

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        @Override
        public String toString() {
            return String.format("%s,%s", lat, lon);
        }
    }
}
