package deployment;

// Spring requires a POJ class for each message.
public class ActivateExitStandMsg {
	private String messageName;
	private String location;
	private String barrier;
	private String ticket;
	private String exitDeadline;
	public ActivateExitStandMsg() {
	}
	public ActivateExitStandMsg( String messageName, String location, String barrier, String ticket, String exitDeadline ) {
		this.messageName = messageName;
		this.location = location;
		this.barrier = barrier;
		this.ticket = ticket;
		this.exitDeadline = exitDeadline;
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
	public String getExitDeadline() {
		return exitDeadline;
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
	public void setExitDeadline( String exitDeadline ) {
		this.exitDeadline = exitDeadline;
	}
}