package deployment;

// Spring requires a POJ class for each message.
public class DelayedEntryMsg {
	private String messageName;
	private String location;
	public DelayedEntryMsg() {
	}
	public DelayedEntryMsg( String messageName, String location ) {
		this.messageName = messageName;
		this.location = location;
	}
	public String getMessageName() {
		return messageName;
	}
	public String getLocation() {
		return location;
	}
	public void setMessageName( String messageName ) {
		this.messageName = messageName;
	}
	public void setLocation( String location ) {
		this.location = location;
	}
}