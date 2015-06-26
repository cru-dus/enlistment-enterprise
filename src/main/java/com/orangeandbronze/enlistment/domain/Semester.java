package com.orangeandbronze.enlistment.domain;

public enum Semester {
	NONE, Y2013_1ST, Y2013_2ND, Y2014_1ST, Y2014_2ND, Y2015_1ST;

	public static Semester current() {
		// returns last element of the enum
		return values()[values().length - 1];
	}
}
