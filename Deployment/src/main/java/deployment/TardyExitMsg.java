package deployment;

// Spring requires a POJ class for each message.
public class TardyExitMsg {
	private String messageName;
	private String location;
	private String ticketNumber;
	private String additionalCharge;
	private String overstay;
	public TardyExitMsg() {
	}
	public TardyExitMsg( String messageName, String location, String ticketNumber, String additionalCharge, String overstay ) {
		this.messageName = messageName;
		this.location = location;
		this.ticketNumber = ticketNumber;
		this.additionalCharge = additionalCharge;
		this.overstay = overstay;
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
	public String getAdditionalCharge() {
		return additionalCharge;
	}
	public String getOverstay() {
		return overstay;
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
	public void setAdditionalCharge( String additionalCharge ) {
		this.additionalCharge = additionalCharge;
	}
	public void setOverstay( String overstay ) {
		this.overstay = overstay;
	}
}