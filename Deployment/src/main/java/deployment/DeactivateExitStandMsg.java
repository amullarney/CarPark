package deployment;

// Spring requires a POJ class for each message.
public class DeactivateExitStandMsg {
	private string location;
	public DeactivateExitStandMsg() {
	}
	public DeactivateExitStandMsg( String location ) {
		this.location = location;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation( String location ) {
		this.location = location;
	}
}