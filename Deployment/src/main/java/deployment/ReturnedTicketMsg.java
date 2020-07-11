package deployment;

// Spring requires a POJ class for each message.
public class ReturnedTicketMsg {
	private String content;
	public ReturnedTicketMsg() {
	}
	public ReturnedTicketMsg( String content ) {
		this.content = content;
	}
	public String getContent() {
		return content;
	}
}