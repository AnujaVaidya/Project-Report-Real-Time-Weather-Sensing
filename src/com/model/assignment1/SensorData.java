package com.model.assignment1;

public class SensorData {
	private Integer sensorId;
	private Integer humidity;
	private Integer temp;
	private Integer wind;
	private Integer precipitation;
	private String day;
	private String timeOfDay;
	private Double latitude;
	private Double longitude;
	public Integer getSensorId() {
		return sensorId;
	}
	public void setSensorId(Integer sensorId) {
		this.sensorId = sensorId;
	}
	public Integer getHumidity() {
		return humidity;
	}
	public void setHumidity(Integer humidity) {
		this.humidity = humidity;
	}
	public Integer getTemp() {
		return temp;
	}
	public void setTemp(Integer temp) {
		this.temp = temp;
	}
	public Integer getWind() {
		return wind;
	}
	public void setWind(Integer wind) {
		this.wind = wind;
	}
	public Integer getPrecipitation() {
		return precipitation;
	}
	public void setPrecipitation(Integer precipitation) {
		this.precipitation = precipitation;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getTimeOfDay() {
		return timeOfDay;
	}
	public void setTimeOfDay(String timeOfDay) {
		this.timeOfDay = timeOfDay;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
}
