package com.example.sep4_and.model;

public class GreenHouseDetailed {
    private int id;
    private String name;
    private String location;
    private float Temperature;
    private float Humidity;
    private float Light;
    private float CO2;
    private float CO2min;
    private float CO2max;
    private float TempMin;
    private float TempMax;
    private float HumidityMin;
    private float HumidityMax;
    private float LightMin;
    private float LightMax;

    public GreenHouseDetailed(int id, String name, String location, float temperature, float humidity, float light, float CO2, float CO2min, float CO2max, float tempMin, float tempMax, float humidityMin, float humidityMax, float lightMin, float lightMax) {
        this.id = id;
        this.name = name;
        this.location = location;
        Temperature = temperature;
        Humidity = humidity;
        Light = light;
        this.CO2 = CO2;
        this.CO2min = CO2min;
        this.CO2max = CO2max;
        TempMin = tempMin;
        TempMax = tempMax;
        HumidityMin = humidityMin;
        HumidityMax = humidityMax;
        LightMin = lightMin;
        LightMax = lightMax;
    }

    public float getLightMax() {
        return LightMax;
    }

    public void setLightMax(float lightMax) {
        LightMax = lightMax;
    }

    public float getLightMin() {
        return LightMin;
    }

    public void setLightMin(float lightMin) {
        LightMin = lightMin;
    }

    public float getHumidityMax() {
        return HumidityMax;
    }

    public void setHumidityMax(float humidityMax) {
        HumidityMax = humidityMax;
    }

    public float getHumidityMin() {
        return HumidityMin;
    }

    public void setHumidityMin(float humidityMin) {
        HumidityMin = humidityMin;
    }

    public float getTempMax() {
        return TempMax;
    }

    public void setTempMax(float tempMax) {
        TempMax = tempMax;
    }

    public float getTempMin() {
        return TempMin;
    }

    public void setTempMin(float tempMin) {
        TempMin = tempMin;
    }

    public float getCO2max() {
        return CO2max;
    }

    public void setCO2max(float CO2max) {
        this.CO2max = CO2max;
    }

    public float getCO2min() {
        return CO2min;
    }

    public void setCO2min(float CO2min) {
        this.CO2min = CO2min;
    }

    public float getCO2() {
        return CO2;
    }

    public void setCO2(float CO2) {
        this.CO2 = CO2;
    }

    public float getLight() {
        return Light;
    }

    public void setLight(float light) {
        Light = light;
    }

    public float getHumidity() {
        return Humidity;
    }

    public void setHumidity(float humidity) {
        Humidity = humidity;
    }

    public float getTemperature() {
        return Temperature;
    }

    public void setTemperature(float temperature) {
        Temperature = temperature;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
