package deployment;


// Spring requires a POJ class for each message.
public class TicketCollectedMsg {
	private String location;
	public TicketCollectedMsg() {
	}
	public TicketCollectedMsg( String location ) {
		this.location = location;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation( String location ) {
		this.location = location;
	}
}