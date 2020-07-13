package deployment;


// Spring requires a POJ class for each message.
public class VehicleExitedMsg {
	private String location;
	public VehicleExitedMsg() {
	}
	public VehicleExitedMsg( String location ) {
		this.location = location;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation( String location ) {
		this.location = location;
	}
}