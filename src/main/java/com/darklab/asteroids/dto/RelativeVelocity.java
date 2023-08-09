package com.darklab.asteroids.dto;

public class RelativeVelocity {
	private Double kilometers_per_second;
	private Double kilometers_per_hour;
	private Double miles_per_hour;

	public Double getKilometers_per_second() {
		return kilometers_per_second;
	}

	public void setKilometers_per_second(double kilometers_per_second) {
		this.kilometers_per_second = kilometers_per_second;
	}

	public Double getKilometers_per_hour() {
		return kilometers_per_hour;
	}

	public void setKilometers_per_hour(double kilometers_per_hour) {
		this.kilometers_per_hour = kilometers_per_hour;
	}

	public Double getMiles_per_hour() {
		return miles_per_hour;
	}

	public void setMiles_per_hour(double miles_per_hour) {
		this.miles_per_hour = miles_per_hour;
	}

	@Override
	public String toString() {
		return "RelativeVelocity{" + "kilometers_per_second=" + kilometers_per_second + ", kilometers_per_hour="
				+ kilometers_per_hour + ", miles_per_hour=" + miles_per_hour + '}';
	}
}
