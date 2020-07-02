package deployment;


// Spring requires a POJ class for each message.
public class TicketRequestEnabledMsg {
	private String content;
	public TicketRequestEnabledMsg() {
	}
	public TicketRequestEnabledMsg( String content ) {
		this.content = content;
	}
	public String getContent() {
		return content;
	}
}