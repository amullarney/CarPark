package deployment;

// Spring requires a POJ class for each message.
public class ActivateEntryStandMsg {
	private String messageName;
	private String location;
	private String barrier;
	private String ticket;
	public ActivateEntryStandMsg() {
	}
	public ActivateEntryStandMsg( String messageName, String location, String barrier, String ticket ) {
		this.messageName = messageName;
		this.location = location;
		this.barrier = barrier;
		this.ticket = ticket;
	}
	public String getMessageName() {
		return messageName;
	}
	public String getLocation() {
		return location;
	}
	public String getBarrier() {
		return barrier;
	}
	public String getTicket() {
		return ticket;
	}
	public void setMessageName( String messageName ) {
		this.messageName = messageName;
	}
	public void setLocation( String location ) {
		this.location = location;
	}
	public void setBarrier( String barrier ) {
		this.barrier = barrier;
	}
	public void setTicket( String ticket ) {
		this.ticket = ticket;
	}
}