package deployment;


// Spring requires a POJ class for each message.
public class TicketRequestDisabledMsg {
	private String content;
	public TicketRequestDisabledMsg() {
	}
	public TicketRequestDisabledMsg( String content ) {
		this.content = content;
	}
	public String getContent() {
		return content;
	}
}