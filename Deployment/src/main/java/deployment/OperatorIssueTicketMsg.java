package deployment;

// Spring requires a POJ class for each message.
public class OperatorIssueTicketMsg {
	private String location;
	public OperatorIssueTicketMsg() {
	}
	public OperatorIssueTicketMsg( String location ) {
		this.location = location;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation( String location ) {
		this.location = location;
	}
}