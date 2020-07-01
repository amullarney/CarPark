package deployment;


// Spring requires a POJ class for each message.
public class VehicleWaitingMsg {
	private String location;
	public VehicleWaitingMsg() {
		System.out.printf( "VehicleWaitingMsg()\n" );
	}
	public VehicleWaitingMsg( String location ) {
    	System.out.printf( "VehicleWaitingMsg( location ): %s\n", location );
		this.location = location;
	}
	public String getLocation() {
		System.out.printf( "getLocation(): %s\n", location );
		return location;
	}
	public void setLocation( String location ) {
		System.out.printf( "setLocation( location ): %s\n", location );
		this.location = location;
	}
}