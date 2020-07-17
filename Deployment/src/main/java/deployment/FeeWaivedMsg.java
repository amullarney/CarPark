package deployment;

// Spring requires a POJ class for each message.
public class FeeWaivedMsg {
	private String ticketNumber;
	public FeeWaivedMsg() {
	}
	public FeeWaivedMsg( String ticketNumber ) {
		this.ticketNumber = ticketNumber;
	}
	public String getTicketNumber() {
		return ticketNumber;
	}
	public void setTicketNumber( String ticketNumber ) {
		this.ticketNumber = ticketNumber;
	}
}