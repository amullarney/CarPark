package deployment;


// Spring requires a POJ class for each message.
public class IssueTicketMsg {
	private String content;
	public IssueTicketMsg() {
	}
	public IssueTicketMsg( String content ) {
		this.content = content;
	}
	public String getContent() {
		return content;
	}
}