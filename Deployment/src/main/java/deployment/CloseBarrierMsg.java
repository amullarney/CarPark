package deployment;


// Spring requires a POJ class for each message.
public class CloseBarrierMsg {
	private String content;
	public CloseBarrierMsg() {
	}
	public CloseBarrierMsg( String content ) {
		this.content = content;
	}
	public String getContent() {
		return content;
	}
}