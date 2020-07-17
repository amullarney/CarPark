package deployment;

// Spring requires a POJ class for each message.
public class FeeCollectedMsg {
	private String ticketNumber;
	public FeeCollectedMsg() {
	}
	public FeeCollectedMsg( String ticketNumber ) {
		this.ticketNumber = ticketNumber;
	}
	public String getTicketNumber() {
		return ticketNumber;
	}
	public void setTicketNumber( String ticketNumber ) {
		this.ticketNumber = ticketNumber;
	}
}