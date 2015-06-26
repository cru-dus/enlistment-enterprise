package com.orangeandbronze.enlistment.domain;

import static org.apache.commons.lang3.Validate.*;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
	public enum Day {MTH, TF, WS};
	public enum Period {H0830, H1000, H1130, H1300, H1430, H1600}
	
	private final Day day;
	private final Period period;
	
	public Schedule(Day day, Period period) {
		notNull(day);
		notNull(period);
		this.day = day;
		this.period = period;
	}
	
	
	public static List<String> getAllDays(){
		List<String> allDays = new ArrayList<>();
		for(Day day : Day.values()){
			allDays.add(day.toString());
		}
		return allDays;
	}
	
	public static List<String> getAllPeriods(){
		List<String> allPeriods = new ArrayList<>();
		for(Period period : Period.values()){
			allPeriods.add(period.toString());
		}
		return allPeriods;
	}
	
	public static Schedule valueOf(String scheduleString) {
		notBlank(scheduleString);
		String[] tokens = scheduleString.split(" ");
		return new Schedule(Day.valueOf(tokens[0]), Period.valueOf(tokens[1]));
	}

	Day getDay() {
		return day;
	}

	Period getPeriod() {
		return period;
	}
	
	@Override
	public String toString() {
		return day.toString() + " " + period.toString(); 	// MTH H0830
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((day == null) ? 0 : day.hashCode());
		result = prime * result + ((period == null) ? 0 : period.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Schedule other = (Schedule) obj;
		if (day != other.day)
			return false;
		if (period != other.period)
			return false;
		return true;
	}
	
	
	
}
