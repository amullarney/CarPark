package deployment;


// Spring requires a POJ class for each message.
public class VehicleEnteredMsg {
	private String location;
	public VehicleEnteredMsg() {
	}
	public VehicleEnteredMsg( String location ) {
		this.location = location;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation( String location ) {
		this.location = location;
	}
}