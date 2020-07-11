package deployment;

// Spring requires a POJ class for each message.
public class WaivedChangeMsg {
	private String location;
	public WaivedChangeMsg() {
	}
	public WaivedChangeMsg( String location ) {
		this.location = location;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation( String location ) {
		this.location = location;
	}
}