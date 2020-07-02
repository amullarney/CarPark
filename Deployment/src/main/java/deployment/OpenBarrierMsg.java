package deployment;


// Spring requires a POJ class for each message.
public class OpenBarrierMsg {
	private String content;
	public OpenBarrierMsg() {
	}
	public OpenBarrierMsg( String content ) {
		this.content = content;
	}
	public String getContent() {
		return content;
	}
}