package deployment;

// Spring requires a POJ class for each message.
public class SetTimeMsg {
	private String year;
	private String month;
	private String day;
	private String hour;
	private String minute;
	public SetTimeMsg() {
	}
	public SetTimeMsg( String year, String month, String day, String hour, String minute ) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
	}
	public String getYear() {
		return year;
	}	
	public String getMonth() {
		return month;
	}
	public String getDay() {
		return day;
	}
	public String getHour() {
		return hour;
	}
	public String getMinute() {
		return minute;
	}
	public void setYear( String year ) {
		this.year = year;
	}
	public void setMonth( String month ) {
		this.month = month;
	}
	public void setDay( String day ) {
		this.day = day;
	}
	public void setHour( String hour ) {
		this.hour = hour;
	}
	public void setMinute( String minute ) {
		this.minute = minute;
	}
}