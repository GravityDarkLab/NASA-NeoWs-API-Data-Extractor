package com.darklab.asteroids.dto;

public class CloseApproachData {
	private String close_approach_date_full;
	private RelativeVelocity relative_velocity;
	private MissDistance miss_distance;
	private String orbiting_body;

	public void setClose_approach_date_full(String close_approach_date_full) {
		this.close_approach_date_full = close_approach_date_full;
	}

	public void setRelative_velocity(RelativeVelocity relative_velocity) {
		this.relative_velocity = relative_velocity;
	}

	public void setMiss_distance(MissDistance miss_distance) {
		this.miss_distance = miss_distance;
	}

	public void setOrbiting_body(String orbiting_body) {
		this.orbiting_body = orbiting_body;
	}

	public String getClose_approach_date_full() {
		return close_approach_date_full;
	}

	public RelativeVelocity getRelative_velocity() {
		return relative_velocity;
	}

	public MissDistance getMiss_distance() {
		return miss_distance;
	}

	public String getOrbiting_body() {
		return orbiting_body;
	}

	@Override
	public String toString() {
		return "CloseApproachData{" + "close_approach_date_full='" + close_approach_date_full + '\''
				+ ", relative_velocity=" + relative_velocity + ", miss_distance=" + miss_distance + ", orbiting_body='"
				+ orbiting_body + '\'' + '}';
	}
}
