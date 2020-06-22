package de.Juldre.Bansystem.Utils.Objects;

public class BanTemplateObject {
	private TimeObject time;
	private String reason;
	private int id;
	public TimeObject getTime() {
		return time;
	}
	public void setTime(TimeObject time) {
		this.time = time;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
