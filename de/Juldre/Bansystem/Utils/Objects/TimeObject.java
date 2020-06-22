package de.Juldre.Bansystem.Utils.Objects;

public class TimeObject {
	private int milliseconds, seconds, minutes, hours, days, months, years;

	public int getMilliseconds() {
		return milliseconds;
	}

	public void setMilliseconds(int milliseconds) {
		this.milliseconds = milliseconds;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public int getMonths() {
		return months;
	}

	public void setMonths(int months) {
		this.months = months;
	}

	public int getYears() {
		return years;
	}

	public void setYears(int years) {
		this.years = years;
	}
	public long toMillis() {
		return (long)getMilliseconds()+((long)getSeconds()*1000)+((long)getMinutes()*1000*60)+((long)getHours()*1000*60*60)+((long)getDays()*1000*60*60*24)+((long)getMonths()*1000*60*60*24*30)+((long)getYears()*1000*60*60*24*30*12);
	}
}
