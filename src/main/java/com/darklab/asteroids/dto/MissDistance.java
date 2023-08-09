package com.darklab.asteroids.dto;

public class MissDistance {
	private Double astronomical;
	private Double lunar;
	private Double kilometers;
	private Double miles;

	public Double getAstronomical() {
		return astronomical;
	}

	public void setAstronomical(double astronomical) {
		this.astronomical = astronomical;
	}

	public Double getLunar() {
		return lunar;
	}

	public void setLunar(double lunar) {
		this.lunar = lunar;
	}

	public Double getKilometers() {
		return kilometers;
	}

	public void setKilometers(double kilometers) {
		this.kilometers = kilometers;
	}

	public Double getMiles() {
		return miles;
	}

	public void setMiles(double miles) {
		this.miles = miles;
	}

	@Override
	public String toString() {
		return "MissDistance{" + "astronomical=" + astronomical + ", lunar=" + lunar + ", kilometers=" + kilometers
				+ ", miles=" + miles + '}';
	}
}
