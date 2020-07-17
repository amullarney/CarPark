package deployment;

// Spring requires a POJ class for each message.
public class DelayedEntryMsg {
	private string location;
	public DelayedEntryMsg() {
	}
	public DelayedEntryMsg( String location ) {
		this.location = location;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation( String location ) {
		this.location = location;
	}
}