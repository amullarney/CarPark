package deployment;


// Spring requires a POJ class for each message.
public class TicketRequestEnabledMsg {
	private String content;
	public TicketRequestEnabledMsg() {
		System.out.printf( "TicketRequestEnabledMsg()\n" );
	}
	public TicketRequestEnabledMsg( String content ) {
    	System.out.printf( "TicketRequestEnabledMsg( content ): %s\n", content );
		this.content = content;
	}
	public String getContent() {
		System.out.printf( "TicketRequestEnbabledMsg.getContent(): %s\n", content );
		return content;
	}
}