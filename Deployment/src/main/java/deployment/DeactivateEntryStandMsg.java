package deployment;

// Spring requires a POJ class for each message.
public class DeactivateEntryStandMsg {
	private string location;
	public DeactivateEntryStandMsg() {
	}
	public DeactivateEntryStandMsg( String location ) {
		this.location = location;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation( String location ) {
		this.location = location;
	}
}