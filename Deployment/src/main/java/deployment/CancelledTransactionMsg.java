package deployment;

// Spring requires a POJ class for each message.
public class CancelledTransactionMsg {
	private String location;
	public CancelledTransactionMsg() {
	}
	public CancelledTransactionMsg( String location ) {
		this.location = location;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation( String location ) {
		this.location = location;
	}
}