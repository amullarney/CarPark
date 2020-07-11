package deployment;

//Spring requires a POJ class for each message.
public class InsertedTicketMsg {
	private String location;
	private String ticketNumber;
	public InsertedTicketMsg() {
	}
	public InsertedTicketMsg( String location, String ticketNumber ) {
		this.location = location;
		this.ticketNumber = ticketNumber;
	}
	public String getLocation() {
		return location;
	}
	public String getTicketNumber() {
		return ticketNumber;
	}
	public void setLocation( String location ) {
		this.location = location;
	}
	public void setTicketNumber( String ticketNumber ) {
		this.ticketNumber = ticketNumber;
	}
}