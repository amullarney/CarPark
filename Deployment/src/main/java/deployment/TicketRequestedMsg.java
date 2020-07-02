package deployment;


// Spring requires a POJ class for each message.
public class TicketRequestedMsg {
	private String location;
	public TicketRequestedMsg() {
	}
	public TicketRequestedMsg( String location ) {
		this.location = location;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation( String location ) {
		this.location = location;
	}
}