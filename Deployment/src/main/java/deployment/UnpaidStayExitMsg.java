package deployment;

// Spring requires a POJ class for each message.
public class UnpaidStayExitMsg {
	private String messageName;
	private String location;
	private String ticketNumber;
	private String charge;
	private String duration;
	public UnpaidStayExitMsg() {
	}
	public UnpaidStayExitMsg( String messageName, String location, String ticketNumber, String charge, String duration ) {
		this.messageName = messageName;
		this.location = location;
		this.ticketNumber = ticketNumber;
		this.charge = charge;
		this.duration = duration;
	}
	public String getMessageName() {
		return messageName;
	}
	public String getLocation() {
		return location;
	}
	public String getTicketNumber() {
		return ticketNumber;
	}
	public String getCharge() {
		return charge;
	}
	public String getDuration() {
		return duration;
	}
	public void setMessageName( String messageName ) {
		this.messageName = messageName;
	}
	public void setLocation( String location ) {
		this.location = location;
	}
	public void setTicketNumber( String ticketNumber ) {
		this.ticketNumber = ticketNumber;
	}
	public void setCharge( String charge ) {
		this.charge = charge;
	}
	public void setDuration( String duration ) {
		this.duration = duration;
	}
}