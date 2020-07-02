package deployment;


// Spring requires a POJ class for each message.
public class VehicleWaitingMsg {
	private String location;
	public VehicleWaitingMsg() {
	}
	public VehicleWaitingMsg( String location ) {
		this.location = location;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation( String location ) {
		this.location = location;
	}
}