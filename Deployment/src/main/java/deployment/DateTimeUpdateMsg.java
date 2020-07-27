package deployment;

// Spring requires a POJ class for each message.
public class DateTimeUpdateMsg {
	private String messageName;
	private String dateTime;
	public DateTimeUpdateMsg() {
	}
	public DateTimeUpdateMsg( String messageName, String dateTime ) {
		this.messageName = messageName;
		this.dateTime = dateTime;
	}
	public String getMessageName() {
		return messageName;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setMessageName( String messageName ) {
		this.messageName = messageName;
	}
	public void setDateTime( String dateTime ) {
		this.dateTime = dateTime;
	}
}