package deployment;

// Spring requires a POJ class for each message.
public class OpenEntryBarrierMsg {
	private String location;
	public OpenEntryBarrierMsg() {
	}
	public OpenEntryBarrierMsg( String location ) {
		this.location = location;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation( String location ) {
		this.location = location;
	}
}