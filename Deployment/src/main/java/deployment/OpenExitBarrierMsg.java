package deployment;

// Spring requires a POJ class for each message.
public class OpenExitBarrierMsg {
	private String location;
	public OpenExitBarrierMsg() {
	}
	public OpenExitBarrierMsg( String location ) {
		this.location = location;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation( String location ) {
		this.location = location;
	}
}